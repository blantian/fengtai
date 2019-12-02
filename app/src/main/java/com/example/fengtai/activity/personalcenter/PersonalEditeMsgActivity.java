package com.example.fengtai.activity.personalcenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.adapter.SpinnerAdapter;
import com.example.fengtai.entity.ImgResult;
import com.example.fengtai.entity.UserInfoResult;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.entity.Item;
import com.example.fengtai.entity.Region;
import com.example.fengtai.util.Util;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.fengtai.MyApplication.API;

public class PersonalEditeMsgActivity extends AppCompatActivity{

    private Handler mHandler = new Handler();
    private ImageView personalBack;
    private ImageView headPic;
    private EditText etpUsername;
    private EditText etpPassword;
    private EditText etpEmail;
    private EditText etpPhone;
    private EditText etpIdcard;
    private Spinner etpSex;
    private Spinner addressProvince;
    private Spinner adressCity;
    private Spinner adressCounty;
    private EditText etpDetailadress;
    private EditText etpAge;
    private Button bttnSaveMsg;


    private String Img;
    private String editName;
    private String editPass;
    private String editEmail;
    private String editPhone;
    private String editIdcard;
    private String selectSex;
    private String selectProvince;
    private String selectCity;
    private String selectCounty;
    private String detailAdress;
    private String age;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_editemsg);
        initView();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(0);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            API.getUserInfo(MyApplication.userId).enqueue(new Callback<Result<UserInfoResult>>() {
                                @Override
                                public void onResponse(Call<Result<UserInfoResult>> call, Response<Result<UserInfoResult>> response) {
                                    Glide.with(PersonalEditeMsgActivity.this).load(response.body().getData().getAvatar()).into(headPic);
                                    etpUsername.setText(response.body().getData().getUsername());
                                    if (!response.body().getData().getEmail().equals("")){
                                        etpEmail.setText(response.body().getData().getEmail());
                                    }
                                    else {
                                        return;
                                    }
                                    etpPhone.setText(response.body().getData().getPhone());
                                    etpIdcard.setText(response.body().getData().getId_card());
                                    etpDetailadress.setText(response.body().getData().getAddress());
                                    etpAge.setText(response.body().getData().getAge());

                                }

                                @Override
                                public void onFailure(Call<Result<UserInfoResult>> call, Throwable t) {
                                    t.printStackTrace();
                                    Util.makeToast(PersonalEditeMsgActivity.this,"出错");

                                }
                            });
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        System.out.println(ImagePicker.RESULT_CODE_ITEMS);
        if (resultCode==ImagePicker.RESULT_CODE_ITEMS){
            if (data!=null){
                ArrayList<ImageItem> extra = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Img = extra.get(0).path;
                System.out.println(Img);
                Glide.with(PersonalEditeMsgActivity.this).load(Img).into(headPic);
            }
        }
    }

    private void initView() {
        personalBack = (ImageView) findViewById(R.id.personal_back);
        headPic = (ImageView) findViewById(R.id.headpic);
        etpUsername = (EditText) findViewById(R.id.etp_username);
        etpPassword = (EditText) findViewById(R.id.etp_password);
        etpEmail = (EditText) findViewById(R.id.etp_email);
        etpPhone = (EditText) findViewById(R.id.etp_phone);
        etpIdcard = (EditText) findViewById(R.id.etp_idcard);
        etpSex = (Spinner) findViewById(R.id.etp_sex);
        addressProvince = (Spinner) findViewById(R.id.adress_province);
        adressCity = (Spinner) findViewById(R.id.adress_city);
        adressCounty = (Spinner) findViewById(R.id.adress_county);
        etpDetailadress = (EditText) findViewById(R.id.etp_detailadress);
        etpAge = (EditText) findViewById(R.id.etp_age);
        bttnSaveMsg = (Button) findViewById(R.id.bttn_save_msg);

        headPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(PersonalEditeMsgActivity.this, ImageGridActivity.class),100);
            }
        });

        bttnSaveMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

    }

    private void save(){
        if (Img == null){
            Util.makeToast(this,"请提交头像");
            return;
        }
        File file = new File(Img);
        API.uploadImg(MultipartBody.Part
                      .createFormData("name",file.getName(), RequestBody.create(file, MediaType.parse("Image/*")))).enqueue(new Callback<Result<ImgResult>>() {
            @Override
            public void onResponse(Call<Result<ImgResult>> call, Response<Result<ImgResult>> response) {
                if (response.code()==200){
                    //数据提交
                    enrollData();
                }
            }

            @Override
            public void onFailure(Call<Result<ImgResult>> call, Throwable t) {

            }
        });
    }

    public void enrollData(){
        editName = Util.StringHandle(etpUsername.getText().toString());
        editPass = Util.StringHandle(etpPassword.getText().toString());
        editEmail = Util.StringHandle(etpEmail.getText().toString());
        editPhone = Util.StringHandle(etpPhone.getText().toString());
        editIdcard =Util.StringHandle(etpIdcard.getText().toString());
        detailAdress =Util.StringHandle(etpDetailadress.getText().toString());
        age =Util.StringHandle(etpAge.getText().toString());

        API
                .editUserInfo(RequestBody.create(MediaType.parse(""),MyApplication.userId),
                        RequestBody.create(MediaType.parse(""),editName),
                        RequestBody.create(MediaType.parse(""),editPass),
                        RequestBody.create(MediaType.parse(""),editEmail),
                        RequestBody.create(MediaType.parse(""),editPhone),
                        RequestBody.create(MediaType.parse(""),((Item)etpSex.getSelectedItem()).getValue()),
                        RequestBody.create(MediaType.parse(""),detailAdress),
                        RequestBody.create(MediaType.parse(""),editIdcard),
                        RequestBody.create(MediaType.parse(""),age),
                        RequestBody.create(MediaType.parse(""),((Item)addressProvince.getSelectedItem()).getValue()),
                        RequestBody.create(MediaType.parse(""),((Item) adressCity.getSelectedItem()).getValue()),
                        RequestBody.create(MediaType.parse(""),((Item) adressCounty.getSelectedItem()).getValue()),
                        RequestBody.create(MediaType.parse(""),Img)).enqueue(new Callback<Result<UserInfoResult>>() {
            @Override
            public void onResponse(Call<Result<UserInfoResult>> call, Response<Result<UserInfoResult>> response) {
                Util.makeToast(PersonalEditeMsgActivity.this,response.body().getMessage());
                startActivity(new Intent(PersonalEditeMsgActivity.this,PersonalActivity.class));
            }

            @Override
            public void onFailure(Call<Result<UserInfoResult>> call, Throwable t) {
                Util.makeToast(PersonalEditeMsgActivity.this,t.getMessage());
            }
        });
    }

    /**
     * 三级地址
     */
   private void initAddress(){

       API.getProvinces().enqueue(new Callback<Result<ArrayList<Region>>>() {
           @Override
           public void onResponse(Call<Result<ArrayList<Region>>> call, Response<Result<ArrayList<Region>>> response) {
               if (response.code()==200){
                   ArrayList<Item> list = new ArrayList<>();
                   for(Region region:response.body().getData()){
                       list.add(new Item(region.getTitle(),region.getId()));

                       addressProvince.setAdapter(new SpinnerAdapter(PersonalEditeMsgActivity.this,android.R.layout.simple_list_item_1,list));
                   }
               }
           }

           @Override
           public void onFailure(Call<Result<ArrayList<Region>>> call, Throwable t) {

           }
       });
   }

    private void submit() {
        // validate
        String username = etpUsername.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "username不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = etpPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String email = etpEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show();
            return;
        }

        String phone = etpPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "phone不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String idcard = etpIdcard.getText().toString().trim();
        if (TextUtils.isEmpty(idcard)) {
            Toast.makeText(this, "idcard不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String detailadress = etpDetailadress.getText().toString().trim();
        if (TextUtils.isEmpty(detailadress)) {
            Toast.makeText(this, "detailadress不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String age = etpAge.getText().toString().trim();
        if (TextUtils.isEmpty(age)) {
            Toast.makeText(this, "age不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
