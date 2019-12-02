package com.example.fengtai.fragment.breeddoc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.entity.breed.BreedBaseMsg;
import com.example.fengtai.entity.breed.BreedFindResult;
import com.example.fengtai.fragment.base.BaseFragment;
import com.example.fengtai.util.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_baseMsg extends BaseFragment {
    private List<BreedFindResult> breedFindResults;
    private String breedID = "";

    private String sheng;
    private String shi;
    private String xian;
    private String dizhi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breedbase_msg, container, false);
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onStart() {
        super.onStart();
        breedID = getArguments().getString("breedId");
        System.out.print("breedID" +breedID);
        View view = getView();
        final TextView BreedbaseName = view.findViewById(R.id.breedbase_name);
        final TextView BasebreedNum = view.findViewById(R.id.basebreed_num);
        final TextView BaseMaleNum = view.findViewById(R.id.basemale_num);
        final TextView BaseFameleNum = view.findViewById(R.id.basefamele_num);
        final TextView BaseBreedAge = view.findViewById(R.id.basebreed_age);
        final TextView BaseBreedTime = view.findViewById(R.id.basebreed_time);
        final TextView BaseBreedAdress = view.findViewById(R.id.basebreed_adress);
        final TextView BaseBreedClass = view.findViewById(R.id.basebreed_class);
        final TextView GongYingShang = view.findViewById(R.id.gongyingshang);
        final TextView ZongZhiChu = view.findViewById(R.id.zongzhichu);
        final ImageView avatar = view.findViewById(R.id.avatar);
        //todo 获取对应的牲畜id
        MyApplication.API.getBreedFind(breedID, MyApplication.userId).enqueue(new Callback<Result<BreedBaseMsg>>() {
            @Override
            public void onResponse(Call<Result<BreedBaseMsg>> call, Response<Result<BreedBaseMsg>> response) {
                if (response.code() == 200) {
                    BreedBaseMsg data = response.body().getData();
                    Glide.with(getContext()).load(data.getImg()).into(avatar);
                    BreedbaseName.setText(data.getTitle());
                    BasebreedNum.setText(data.getNumber());
                    BaseMaleNum.setText(data.getGong());
                    BaseFameleNum.setText(data.getMu());
                    BaseBreedAge.setText(data.getAge());
                    BaseBreedTime.setText(data.getBecome_time());
                    BaseBreedClass.setText(data.getVariety());
                    GongYingShang.setText(data.getSupplier());
                    ZongZhiChu.setText(data.getBecome_price());
                    sheng = data.getSheng();
                    shi = data.getShi();
                    xian = data.getXian();
                    dizhi = data.getDizhi();
                    BaseBreedAdress.setText(sheng + shi + xian + dizhi);
                }
            }

            @Override
            public void onFailure(Call<Result<BreedBaseMsg>> call, Throwable t) {
                Util.makeToast(getContext(), t.getMessage());
            }
        });

    }
}
