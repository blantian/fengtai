package com.example.fengtai.fragment;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.adapter.HomeListAdapter;
import com.example.fengtai.entity.HomeResult;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.util.DownlodPic;
import com.example.fengtai.util.PermissionUtil;
import com.example.fengtai.util.Util;
import com.example.fengtai.util.memoryUtils.ExternalStorageUtils;
import com.example.fengtai.util.memoryUtils.FileUtil;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_home extends Fragment {


    private View view;
    private TextView liUserName;
    private TextView Username;
    private String _liUserName;
    private String _Username;
    private ArrayList<String> picPath;
    private ArrayList<HomeResult> homeResults = new ArrayList<>();
    FileUtil fileUtil = new FileUtil();


    public Fragment_home() {
    }


    public static Fragment_home newInstance() {
        return new Fragment_home();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);
        //检查权限，申请权限
        if (PermissionUtil.CheckPermissions(getActivity(), getContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})) {
            PermissionUtil.RequestPermission(getActivity(), getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        //获取用户名
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myUserMsg", MODE_PRIVATE);
        _liUserName = sharedPreferences.getString("account", "");
        _Username = sharedPreferences.getString("account", "");
        liUserName = getView().findViewById(R.id.liusername);
        Username = getView().findViewById(R.id.username);
        liUserName.setText(_liUserName);
        Username.setText(_Username);

        final RecyclerView recyclerView = getView().findViewById(R.id.home_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        //todo 无网络状态
        if (!Util.isNetworkAvailable(getContext())) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setAdapter(new HomeListAdapter(homeResults, getContext()));
                }
            });
        } else {
            MyApplication.API.getHome(RequestBody.create(MediaType.parse(""), MyApplication.userId)).enqueue(new Callback<Result<ArrayList<HomeResult>>>() {
                @Override
                public void onResponse(@NotNull final Call<Result<ArrayList<HomeResult>>> call, @NotNull Response<Result<ArrayList<HomeResult>>> response) {
                    if (response.body().getStatus() == 200) {
                        homeResults = response.body().getData();
                        //todo 下载图片：此处后期可优化为异步线程下载
                        for (int i = 0; i < homeResults.size(); i++) {
                            final String name = String.valueOf(i);
                            final String url = homeResults.get(i).getProduct_pic();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        final Context context = getActivity().getApplicationContext();
                                        FutureTarget<File> target = Glide.with(context)
                                                .asFile()
                                                .load(url)
                                                .submit();
                                        final File file = target.get();
                                        //下载的图片保存到本地
                                        ExternalStorageUtils.writeExternalStoragePrivate("productPicIcon",getActivity(),"picicon"+name+".png",readStream(String.valueOf(file)));
                                        //获取存储文件路径
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                            //ExternalStorageUtils.readExternalStoragePrivate("productPicIcon","picicon"+name,getActivity());
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                recyclerView.setAdapter(new HomeListAdapter(homeResults, getContext()));
                            }
                        });
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Result<ArrayList<HomeResult>>> call, @NotNull Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
    /**
     * 照片转byte二进制
     * @param imagepath 需要转byte的照片路径
     * @return 已经转成的byte
     * @throws Exception
     */
    public static byte[] readStream(String imagepath) throws Exception {
        FileInputStream fs = new FileInputStream(imagepath);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while (-1 != (len = fs.read(buffer))) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        fs.close();
        return outStream.toByteArray();
    }
}