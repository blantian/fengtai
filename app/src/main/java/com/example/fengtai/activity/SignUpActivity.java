package com.example.fengtai.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.entity.LoginResult;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.util.Constants;
import com.example.fengtai.util.Util;

import org.jetbrains.annotations.NotNull;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class SignUpActivity extends AppCompatActivity {

    Context context;
    private EditText editText_name;
    private EditText editText_pass;
    private EditText editText_sure_pss;
    private EditText editText_phone;
    private Button button_sign;
    private ImageButton back;
    private ProgressDialog progressDialog;
    private OkHttpClient client;
    private String user_name, user_pss, user_phone, user_sure_pass;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initComponents();
        setListeners();

    }

    void initComponents() {
        editText_name = findViewById(R.id.user_name_sign);
        editText_pass = findViewById(R.id.user_pass_sign);
        editText_sure_pss = findViewById(R.id.sure_pass_sign);
        editText_phone = findViewById(R.id.user_phone_sign);
        button_sign = findViewById(R.id.btt_sign_up);
        back = findViewById(R.id.back);
        progressDialog = new ProgressDialog(SignUpActivity.this);
    }

    void setListeners() {
        //返回监听器
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    /**
     * 隐藏输入法
     * @param ev
     * @return
     */
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 拦截ACTION_DOWN事件，判断是否需要隐藏输入法
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldHideInput(view, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }

        // 交由DecorView去做Touch事件的分发
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }

        // Activity内没有View对这个Touch事件做处理，那么有Activity来处理
        return onTouchEvent(ev);
    }
    private boolean isShouldHideInput(View view, MotionEvent ev) {
        //判断是否是EditText，如果不是，直接返回false
        if (view != null && (view instanceof EditText)) {
            int[] location = {0, 0};
            view.getLocationOnScreen(location);
            int left = location[0];
            int top = location[1];

            //判断Touch的点是否在EditText外
            if (ev.getX() < left || (ev.getX() > left + view.getWidth())
                    || ev.getY() < top || (ev.getY() > top + view.getHeight())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    //POST请求
    private void register() {

        //前端参数校验，防SQL注入
        String account = Util.StringHandle(editText_name.getText().toString());
        String pwd = Util.StringHandle(editText_pass.getText().toString());
        String sure_pwd = Util.StringHandle(editText_sure_pss.getText().toString());
        String phone = Util.StringHandle(editText_phone.getText().toString());

        //判断用户输入信息是否合理
        String resMsg = checkDataValid(account, pwd, sure_pwd, phone);
        if (!resMsg.equals("")) {
            showResponse(resMsg);
            return;
        }

        MyApplication.API
                .enrollUser(RequestBody.create(MediaType.parse(""), account)
                        , RequestBody.create(MediaType.parse(""), pwd),
                        RequestBody.create(MediaType.parse(""), phone))
                .enqueue(new retrofit2.Callback<Result<LoginResult>>() {
                    @Override
                    public void onResponse(@NotNull retrofit2.Call<Result<LoginResult>> call, @NotNull retrofit2.Response<Result<LoginResult>> response) {
                        // 显示注册结果
                        showResponse(response.body().getMessage());
                        if ((response.body().getStatus() + "").equals(Constants.SUCCESSCODE_REGISTER)) {
                            //可以设置查找数据库中是否存在用户f
                            // 注册成功
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Result<LoginResult>> call, Throwable t) {
                        t.printStackTrace();
                        showResponse("请链接网络");
                    }
                });


//        request.addRequestParam("uername",account);
//        request.addRequestParam("password",pwd);
//        //请求
//        HttpUtil.sendPost(Constants.URL_Register,request.getJsonStr(),new okhttp3.Callback(){
//            public void onResponse(Call call, Response response) throws IOException {
//                CommonResponse res = new CommonResponse(response.body().string());
//                String resCode = res.getResCode();
//                String resMsg = res.getResMsg();
//                // 显示注册结果
//                showResponse(resMsg);
//                // 注册成功
//                if (resCode.equals(Constants.SUCCESSCODE_REGISTER)) {
//                    finish();
//                }
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//                showResponse("Network ERROR");
//            }
//        });
    }

    private void showResponse(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String checkDataValid(String account, String pwd, String pwd_confirm, String phone) {
        if (TextUtils.isEmpty(account) | TextUtils.isEmpty(pwd) | TextUtils.isEmpty(pwd_confirm))
            return getResources().getString(R.string.null_hint);
        if (!pwd.equals(pwd_confirm))
            return getResources().getString(R.string.pass_sure);
        if (phone.length() != 11)
            return getResources().getString(R.string.phonenum);
        return "";
    }


}


