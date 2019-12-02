package com.example.fengtai.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.entity.LoginResult;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.util.Constants;
import com.example.fengtai.util.SharedPreferencesUtils;
import com.example.fengtai.util.Util;

import org.jetbrains.annotations.NotNull;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText accountText;
    private EditText passwordText;
    private CheckBox isRememberPwd;
    private TextView textView_sign;
    private Button button_login;
    private CheckBox aotu_login;
    private ToggleButton toggleButton;

    private String account;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
        setListenners();


        //记住密码
        SharedPreferencesUtils sup = new SharedPreferencesUtils(this);
        boolean isRemember = (boolean) sup.getParam("isRememberPwd", false);
        Boolean isAutoLogin = (Boolean) sup.getParam("isAutoLogin", false);

        //SharedPerferences 获取用户名账户密码，存在则填充
        String account = (String) sup.getParam("account", "");
        String pwd = (String) sup.getParam("pwd", "");
        if (!account.equals("") && !pwd.equals("")) {
            if (isRemember) {
                accountText.setText(account);
                passwordText.setText(pwd);
                isRememberPwd.setChecked(true);
            }
            if (isAutoLogin)
                Login();
        }

    }

            void initComponents() {
        accountText = findViewById(R.id.user_name_login);
        passwordText = findViewById(R.id.user_pass_login);
        isRememberPwd = findViewById(R.id.remmber_pass);
        textView_sign = findViewById(R.id.bttn_sign_login);
        button_login = findViewById(R.id.bttn_loign_in);
        progressBar = findViewById(R.id.progressbar);
        aotu_login = findViewById(R.id.aotu_login);
        toggleButton = findViewById(R.id.togglebtton);

    }

    void setListenners() {
        /**
         * 隐藏显示密码
         */
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransformationMethod method = passwordText.getTransformationMethod();
                if (method == HideReturnsTransformationMethod.getInstance()) {
                    passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                // 保证切换后光标位于文本末尾
                Spannable spanText = passwordText.getText();
                if (spanText != null) {
                    Selection.setSelection(spanText, spanText.length());
                }
            }
        });
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        textView_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
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

    /**
     * API 方式登录
     */
    private void Login() {

        //前段参数校验，防SQL注入
        account = Util.StringHandle(accountText.getText().toString());
        password = Util.StringHandle(passwordText.getText().toString());
        System.out.println("account content:" + account);
        //检查用户名密码是否为空
        final String resMsg = checkDataValid(account, password);
        if (!resMsg.equals("")) {
            showResponse(resMsg);
            return;
        }
        progressBar.setVisibility(View.VISIBLE);//显示进度条
        OptionHandle(account, password);//处理记住密码
      //        网络请求
        MyApplication.API
                .loginUser(RequestBody.create(MediaType.parse(""), account)
                        , RequestBody.create(MediaType.parse(""), password))
                .enqueue(new Callback<Result<LoginResult>>() {
                    @Override
                    public void onResponse(@NotNull retrofit2.Call<Result<LoginResult>> call, @NotNull retrofit2.Response<Result<LoginResult>> response) {
                        showResponse(response.body().getMessage());
                        if ((response.body().getStatus() + "").equals(Constants.SUCCESSCODE_LOGIN)) {
                            //可以设置查找数据库中是否存在用户
                            showResponse(response.body().getMessage());

                            MyApplication.userId = response.body().getData().getUser_id();

                            startActivity(new Intent(LoginActivity.this, BottomNavigationActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Result<LoginResult>> call, Throwable t) {
                        t.printStackTrace();
                        showResponse(t.getMessage());
                    }
                });
    }

    //更新UI
    private void showResponse(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //判断密码账户为空
    private String checkDataValid(String account, String pwd) {
        if (TextUtils.isEmpty(account) | TextUtils.isEmpty(pwd))
            return getResources().getString(R.string.null_hint);
        return "";
    }

    //保存用户名密码
    void OptionHandle(String account, String pwd) {
        SharedPreferences.Editor editor = getSharedPreferences("UserData", MODE_PRIVATE).edit();
        SharedPreferencesUtils spu = new SharedPreferencesUtils(this);
        if (isRememberPwd.isChecked()) {
            editor.putBoolean("isRememberPwd", true);
            // 保存账号密码
            spu.setParam("account", account);
            spu.setParam("pwd", pwd);
        } else {
            editor.putBoolean("isRememberPwd", false);
        }
        if (aotu_login.isChecked()) {
            editor.putBoolean("isAutoLogin", true);
        } else {
            editor.putBoolean("isAutoLogin", false);
        }
        editor.apply();
    }
}