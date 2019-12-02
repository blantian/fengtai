package com.example.fengtai.fragment.breeddoc;

import android.app.ProgressDialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.fengtai.BuildConfig;
import com.example.fengtai.MainActivity;
import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.adapter.BreedsDocInfoAdapter;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.entity.breed.BreedDoc;
import com.example.fengtai.fragment.base.BaseFragment;
import com.example.fengtai.util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.fengtai.MyApplication.API;

public class Fragment_breedDocMsg extends BaseFragment {

    String[] currentSelected = new String[2];
    private ProgressBar progressbars;
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView = null;
    private BreedsDocInfoAdapter adapter = null;
    private ArrayList<BreedDoc> list = new ArrayList<>();
    private String breedID = "";
    private boolean _1 = false, _2 = false, _3 = false;
    //用于提供刷新效果
    private ArrayList<BreedDoc> arrayList = new ArrayList<>();
    private int currentPage = 0;

    public Fragment_breedDocMsg() {
    }

    private void buildDialog(){
         progressbars = new ProgressBar(getContext());
        //progressbars.set("加载中...");
        //progressbars.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentbreeddoc_msg, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        breedID = getArguments().getString("breedId");
        View view = getView();
        progressbars = view.findViewById(R.id.progressbar);
        recyclerView = view.findViewById(R.id.recycview);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        adapter = new BreedsDocInfoAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        updateData("", "");

        final EditText editText = view.findViewById(R.id.breed_search);
        TextView btnSearch = view.findViewById(R.id.btn_search);

        TextView btnSex = view.findViewById(R.id.btn_sex);
        TextView btnBreeding = view.findViewById(R.id.btn_breeding);
        TextView btnWight = view.findViewById(R.id.btn_wight);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData("1", editText.getText().toString());
            }
        });

        btnSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _1 = !_1;
                    updateData("2", _1 ? "1" : "2");

            }
        });
        btnBreeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _2 = !_2;
                updateData("4", _2 ? "1" : "2");
            }
        });

        btnWight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _3 = !_3;
                updateData("3", _3 ? "1" : "2");
            }
        });


        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMore();
                refreshLayout.finishLoadMore(10);
                //buildDialog();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                updateData(currentSelected[0], currentSelected[1]);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.bottom = 8;
            }
        });
    }

    private void loadMore() {
        list.clear();
        if ((currentPage + 5) <= arrayList.size()) {

            list.addAll(arrayList.subList(currentPage, currentPage += 5));

        } else {
            //已无更多数据加载
            refreshLayout.finishLoadMoreWithNoMoreData();
            list.addAll(arrayList.subList(currentPage, arrayList.size()));
        }
        //更新视图
        adapter.notifyDataSetChanged();


        if (list.size() == 0)
            refreshLayout.setNoMoreData(true);

    }

    /**
     * @param type  类型
     * @param param 类型参数
     */
    private void updateData(String type, String param) {
        currentSelected[0] = type;
        currentSelected[1] = param;
        API.getBreedData(MyApplication.userId, breedID, type, param).enqueue(new Callback<Result<LinkedTreeMap>>() {
            @Override
            public void onResponse(Call<Result<LinkedTreeMap>> call, Response<Result<LinkedTreeMap>> response) {


                Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();

                LinkedTreeMap<String, LinkedTreeMap> map = response.body().getData();
                //冗余干扰数据 typet
                map.remove("typet");

                arrayList.clear();
                for (Object o : map.keySet())
                    arrayList.add(gson.fromJson(gson.toJson(map.get(o)), BreedDoc.class));

                currentPage = 0;
                loadMore();
                refreshLayout.finishRefresh(5);
                Util.makeToast(getContext(), response.body().getMessage());
            }

            @Override
            public void onFailure(Call<Result<LinkedTreeMap>> call, Throwable t) {
                Util.makeToast(getContext(), t.getMessage());
            }
        });
    }

}
