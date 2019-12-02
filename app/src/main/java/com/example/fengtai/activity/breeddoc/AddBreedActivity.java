package com.example.fengtai.activity.breeddoc;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.adapter.SpinnerAdapter;
import com.example.fengtai.entity.ImgResult;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.entity.breed.BreedsResult;
import com.example.fengtai.entity.breed.Supplier;
import com.example.fengtai.entity.breed.Variety;
import com.example.fengtai.entity.Item;
import com.example.fengtai.entity.Region;
import com.example.fengtai.util.Util;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.fengtai.MyApplication.API;


public class AddBreedActivity extends AppCompatActivity {

    private ArrayList<String> list;
    private String[] breedName;

    private Spinner classiFy;
    private EditText et_Name;
    private EditText et_Number;
    private EditText et_Age;
    private EditText select_byDate;
    private EditText sum_priess;
    private EditText select_sellDate;
    private Spinner provinces;
    private Spinner citys;
    private Spinner countys;
    private EditText detailAddress;
    private Spinner breeds;
    private Spinner suppliers;
    private Button save_Msg;
    private ImageView pic;

    private String selectedClassiFy;
    private String addName;
    private String addNumber;
    private String addAge;
    private String selectedDte;
    private String selectedProvinces;
    private String priess;
    private String selectedCity;
    private String selectedCounty;
    private String datail;
    private String selectedBreed;
    private String selectedSuppliers;
    private String img;
    private int ref = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfarmdoc);
        initComponents();
        initData();

    }

    private void initComponents() {
        classiFy = findViewById(R.id.classify);
        et_Name = findViewById(R.id.et_name);
        et_Age = findViewById(R.id.et_age);
        et_Number = findViewById(R.id.et_number);
        select_byDate = findViewById(R.id.select_by_time);
        select_sellDate = findViewById(R.id.select_sell_time);
        sum_priess = findViewById(R.id.et_bypress);
        provinces = findViewById(R.id.adress_province);
        citys = findViewById(R.id.adress_city);
        countys = findViewById(R.id.adress_county);
        breeds = findViewById(R.id.breed);
        suppliers = findViewById(R.id.supplier);


        save_Msg = findViewById(R.id.save_msg);
        pic = findViewById(R.id.pic);


        select_byDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(select_byDate);
            }
        });
        select_sellDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(select_sellDate);
            }
        });

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(AddBreedActivity.this, ImageGridActivity.class), 100);
            }
        });
        save_Msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

    }

    private void initData() {

        API.getVars().enqueue(new Callback<Result<ArrayList<Variety>>>() {
            @Override
            public void onResponse(Call<Result<ArrayList<Variety>>> call, Response<Result<ArrayList<Variety>>> response) {
                if (response.code() == 200) {
                    ArrayList<Item> list = new ArrayList<>();
                    for (Variety o : response.body().getData())
                        list.add(new Item(o.getName(), o.getId()));

                    breeds.setAdapter(new SpinnerAdapter(AddBreedActivity.this, android.R.layout.simple_list_item_1, list));
                }
            }

            @Override
            public void onFailure(Call<Result<ArrayList<Variety>>> call, Throwable t) {

            }
        });

        API.getSuppliers().enqueue(new Callback<Result<ArrayList<Supplier>>>() {
            @Override
            public void onResponse(Call<Result<ArrayList<Supplier>>> call, Response<Result<ArrayList<Supplier>>> response) {
                if (response.code() == 200) {
                    ArrayList<Item> list = new ArrayList<>();
                    for (Supplier o : response.body().getData())
                        list.add(new Item(o.getName(), o.getId()));

                    suppliers.setAdapter(new SpinnerAdapter(AddBreedActivity.this, android.R.layout.simple_list_item_1, list));
                }
            }

            @Override
            public void onFailure(Call<Result<ArrayList<Supplier>>> call, Throwable t) {
                t.printStackTrace();

            }
        });
        initAddress();
    }


    private void save() {
        if (img == null) {
            Util.makeToast(this, "请提交头像");
            return;
        }
        File file = new File(img);
        API.uploadImg(
                MultipartBody.Part
                        .createFormData("name", file.getName(),
                                RequestBody.create(file, MediaType.parse("image/*"))
                        )).enqueue(new Callback<Result<ImgResult>>() {
            @Override
            public void onResponse(Call<Result<ImgResult>> call, Response<Result<ImgResult>> response) {
                if (response.code() == 200) {
                    Util.makeToast(AddBreedActivity.this, response.body().getMessage());
                    enroll();
                }
            }

            @Override
            public void onFailure(Call<Result<ImgResult>> call, Throwable t) {
                t.printStackTrace();

            }
        });

    }

    public void enroll() {
        addName = Util.StringHandle(et_Name.getText().toString());
        addNumber = Util.StringHandle(et_Number.getText().toString());
        addAge = Util.StringHandle(et_Age.getText().toString());

        API
                .enrollbreed(
                        MyApplication.userId,
                        classiFy.getSelectedItemPosition() + 1 + "",
                        et_Number.getText().toString(),
                        select_byDate.getText().toString(),
                        select_sellDate.getText().toString(),
                        et_Name.getText().toString(),
                        et_Age.getText().toString(),
                        sum_priess.getText().toString(),
                        img,
                        ((Item) provinces.getSelectedItem()).getValue(),
                        ((Item) citys.getSelectedItem()).getValue(),
                        ((Item) countys.getSelectedItem()).getValue(),
                        ((Item) suppliers.getSelectedItem()).getValue(),
                        ((Item) breeds.getSelectedItem()).getValue()
                )
                .enqueue(new Callback<Result<BreedsResult>>() {
                    @Override
                    public void onResponse(Call<Result<BreedsResult>> call, Response<Result<BreedsResult>> response) {


                        Util.makeToast(AddBreedActivity.this, response.body().getMessage());
                     //读取分类 id id 可能有问题
                        startActivity(new Intent(AddBreedActivity.this, BreedsDocActivity.class).putExtra("id", classiFy.getSelectedItemPosition() + 1 + ""));

                    }

                    @Override
                    public void onFailure(Call<Result<BreedsResult>> call, Throwable t) {
                        Util.makeToast(AddBreedActivity.this, t.getMessage());
                    }
                });

    }


    private void datePicker(final EditText et) {
        DatePickerDialog pickerDialog;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            pickerDialog = new DatePickerDialog(this);
            pickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    et.setText(new SimpleDateFormat("YYYY-MM-dd").format(calendar.getTime()));

                }
            });
            pickerDialog.show();
        } else Util.makeToast(this, "系统不支持");

    }


    /**
     * 三级地址
     */
    private void initAddress() {

        API.getProvinces().enqueue(new Callback<Result<ArrayList<Region>>>() {
            @Override
            public void onResponse(Call<Result<ArrayList<Region>>> call, Response<Result<ArrayList<Region>>> response) {
                if (response.code() == 200) {
                    ArrayList<Item> list = new ArrayList<>();
                    for (Region o : response.body().getData())
                        list.add(new Item(o.getTitle(), o.getId()));

                    provinces.setAdapter(new SpinnerAdapter(AddBreedActivity.this, R.layout.spinner_item, list));
                }
            }

            @Override
            public void onFailure(Call<Result<ArrayList<Region>>> call, Throwable t) {

            }
        });

        provinces.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                API.getCities(
                        ((Item) provinces.getAdapter().getItem(position)).getValue()).enqueue(new Callback<Result<ArrayList<Region>>>() {
                    @Override
                    public void onResponse(Call<Result<ArrayList<Region>>> call, Response<Result<ArrayList<Region>>> response) {
                        if (response.code() == 200) {
                            ArrayList<Item> list = new ArrayList<>();
                            for (Region o : response.body().getData())
                                list.add(new Item(o.getTitle(), o.getId()));

                            citys.setAdapter(new SpinnerAdapter(AddBreedActivity.this, android.R.layout.simple_list_item_1, list));
                            ((SpinnerAdapter) citys.getAdapter()).notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result<ArrayList<Region>>> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        citys.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                API.getCounty(((Item) citys.getAdapter().getItem(position)).getValue() + "fid=" + ((Item) provinces.getAdapter().getItem(position)).getValue()).enqueue(new Callback<Result<ArrayList<Region>>>() {
                    @Override
                    public void onResponse(Call<Result<ArrayList<Region>>> call, Response<Result<ArrayList<Region>>> response) {
                        if (response.code() == 200) {
                            ArrayList<Item> list = new ArrayList<>();
                            for (Region o : response.body().getData())
                                list.add(new Item(o.getTitle(), o.getId()));
                            countys.setAdapter(new SpinnerAdapter(AddBreedActivity.this, android.R.layout.simple_list_item_1, list));
                            ((SpinnerAdapter) countys.getAdapter()).notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result<ArrayList<Region>>> call, Throwable t) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(ImagePicker.RESULT_CODE_ITEMS);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                ArrayList<ImageItem> extra = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                img = extra.get(0).path;
                System.out.println(img);
                Glide.with(AddBreedActivity.this).load(img).into(pic);
            }
        }
    }
}