package com.example.fengtai.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.entity.LoginResult;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.util.viewUtil.SubmitControl;
import com.example.fengtai.util.Util;

import org.jetbrains.annotations.NotNull;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class SignUpActivity extends AppCompatActivity {


    private EditText editText_name;
    private EditText editText_pass;
    private EditText editText_sure_pss;
    private EditText editText_phone;
    private CheckBox mCheckBox;
    private Button button_sign;
    private TextView seeTreaty;
    private ImageButton back;
    private ImageView userIcon;
    private ImageView passIcon;
    private ImageView surepassIcon;
    private ImageView phoneIcon;
    private TextView showErro;
    private TextView showErro1;
    private TextView showErro2;
    private TextView showErro3;
    private ProgressBar progressBar;
    private ToggleButton toggleButton, toggleButton2;
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
        mCheckBox = findViewById(R.id.checkbox);
        back = findViewById(R.id.back);
        seeTreaty = findViewById(R.id.seetreaty);
        progressBar = findViewById(R.id.signprogressbar);
        toggleButton = findViewById(R.id.togglebtton);
        toggleButton2 = findViewById(R.id.togglebtton2);
        userIcon = findViewById(R.id.usericon);
        passIcon = findViewById(R.id.passicon);
        surepassIcon = findViewById(R.id.surepassicon);
        phoneIcon = findViewById(R.id.phoneicon);
        showErro = findViewById(R.id.signshowerro);
        showErro1 = findViewById(R.id.signshowerro1);
        showErro2 = findViewById(R.id.signshowerro2);
        showErro3 = findViewById(R.id.sginshowerro3);
    }



    void setListeners() {
        //返回监听器
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SubmitControl submitControl = new SubmitControl();
        submitControl.initConroller();
        submitControl.addView(editText_name,editText_pass,editText_phone,editText_sure_pss,mCheckBox);
        submitControl.setSubmitButton(button_sign);
        button_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        seeTreaty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder seetreaty = new AlertDialog.Builder(SignUpActivity.this);
                seetreaty.setTitle("免责条款");
                //todo 添加API
                seetreaty.setMessage("免责条款");
                seetreaty.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
                seetreaty.show();
            }
        });

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransformationMethod method = editText_pass.getTransformationMethod();
                if (method == HideReturnsTransformationMethod.getInstance()) {
                    editText_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    editText_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                // 保证切换后光标位于文本末尾
                Spannable spanText = editText_pass.getText();
                if (spanText != null) {
                    Selection.setSelection(spanText, spanText.length());
                }
            }
        });
        toggleButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransformationMethod method = editText_sure_pss.getTransformationMethod();
                if (method == HideReturnsTransformationMethod.getInstance()) {
                    editText_sure_pss.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    editText_sure_pss.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                // 保证切换后光标位于文本末尾
                Spannable spanText = editText_sure_pss.getText();
                if (spanText != null) {
                    Selection.setSelection(spanText, spanText.length());
                }
            }
        });
        checkEditText();

    }

    /**
     * 隐藏输入法
     *
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
     * API 方式注册
     */
    private void register() {

        progressBar.setVisibility(View.VISIBLE);
        //前端参数校验，防SQL注入
       String account = Util.StringHandle(editText_name.getText().toString());
        String pwd = Util.StringHandle(editText_pass.getText().toString());
        String sure_pwd = Util.StringHandle(editText_sure_pss.getText().toString());
        String phone = Util.StringHandle(editText_phone.getText().toString());

        //判断用户输入信息是否合理
       String resMsg = checkDataValid(account, pwd, sure_pwd, phone);
        if (!resMsg.equals("")) {
            showResponse(resMsg);
        }
        MyApplication.API
                .enrollUser(RequestBody.create(MediaType.parse(""), account)
                        , RequestBody.create(MediaType.parse(""), pwd),
                        RequestBody.create(MediaType.parse(""), phone))
                .enqueue(new retrofit2.Callback<Result<LoginResult>>() {
                    @Override
                    public void onResponse(@NotNull retrofit2.Call<Result<LoginResult>> call, @NotNull retrofit2.Response<Result<LoginResult>> response) {
                        // 显示注册结果
                        if (response.body().getCode() == 200) {
                            showResponse(response.body().getMessage());
                            // 注册成功
                            String name= editText_name.getText().toString().trim();
                            String pwd= editText_pass.getText().toString().trim();
                            //调用setResult方法  
                            //把两个参数设置到intent中
                            Intent data =new Intent();
                            data.putExtra("name", name);
                            data.putExtra("pwd", pwd);
                            //setResult(RESULT_OK, data);
                            setResult(Activity.RESULT_OK, data);
                            finish();
                        }else {
                            showResponse(response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Result<LoginResult>> call, Throwable t) {
                        t.printStackTrace();
                        showResponse("请链接网络");
                    }
                });
    }

    private void showResponse(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 判断EditeText是否为空
     *
     * @param account
     * @param pwd
     * @param pwd_confirm
     * @param phone
     * @return
     */
    private String checkDataValid(String account, String pwd, String pwd_confirm, String phone) {
        if (TextUtils.isEmpty(account) | TextUtils.isEmpty(pwd) | TextUtils.isEmpty(pwd_confirm))
            return getResources().getString(R.string.null_hint);
        if (!pwd.equals(pwd_confirm))
            return getResources().getString(R.string.pass_sure);
        if (phone.length() != 11)
            return getResources().getString(R.string.phonenum);
        return "";
    }

    /**
     * 监听输入框事件
     */

    private void checkEditText() {

        /**
         * 监听焦点
         */
        //用户名
        editText_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && TextUtils.isEmpty(editText_name.getText().toString().trim())){
                    showErro.setText("用户名不能为空！");
                    editText_name.setBackground(getResources().getDrawable(R.drawable.edittexterro));
                    userIcon.setBackground(getResources().getDrawable(R.drawable.ic_user_erro));
                   // editText_name.setHintTextColor(getResources().getColor(R.color.read));
                }else {
                    editText_name.setBackground(getResources().getDrawable(R.drawable.edittext));
                   // editText_name.setHintTextColor(getResources().getColor(R.color.colorTextGrey));
                    userIcon.setBackground(getResources().getDrawable(R.drawable.ic_user));
                    showErro.setText("");
                }
            }
        });
        //用户密码
        editText_pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String pass = editText_pass.getText().toString().trim();
                if (!hasFocus && TextUtils.isEmpty(editText_pass.getText().toString().trim())){
                    showErro1.setText("密码不能为空！");
                    editText_pass.setBackground(getResources().getDrawable(R.drawable.edittexterro));
                    passIcon.setBackground(getResources().getDrawable(R.drawable.ic_password_erro));
                    //editText_pass.setHintTextColor(getResources().getColor(R.color.read));
                }else {
                    editText_pass.setBackground(getResources().getDrawable(R.drawable.edittext));
                   // editText_pass.setHintTextColor(getResources().getColor(R.color.colorTextGrey));
                    passIcon.setBackground(getResources().getDrawable(R.drawable.ic_password));
                    showErro1.setText("");
                }
            }
        });
        editText_pass.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pass = editText_pass.getText().toString().trim();
                if (pass.length()<7 & pass.length()!=0){
                    showErro1.setText("密码太短！");
                    editText_pass.setBackground(getResources().getDrawable(R.drawable.edittexterro));
                    passIcon.setBackground(getResources().getDrawable(R.drawable.ic_password_erro));
                }else {
                    editText_pass.setBackground(getResources().getDrawable(R.drawable.edittext));
                    // editText_pass.setHintTextColor(getResources().getColor(R.color.colorTextGrey));
                    passIcon.setBackground(getResources().getDrawable(R.drawable.ic_password));
                    showErro1.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //确认用户密码
        editText_sure_pss.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String pass = editText_pass.getText().toString().trim();
                String surepass = editText_sure_pss.getText().toString().trim();
                if (!hasFocus & TextUtils.isEmpty(surepass)){
                    showErro2.setText("密码不能为空！");
                    editText_sure_pss.setBackground(getResources().getDrawable(R.drawable.edittexterro));
                    surepassIcon.setBackground(getResources().getDrawable(R.drawable.ic_password_erro));
                    //editText_sure_pss.setHintTextColor(getResources().getColor(R.color.read));
                }else if(!surepass.equals(pass)&surepass.length()!=0) {
                    showErro2.setText("您输入的密码不一致！");
                    editText_sure_pss.setBackground(getResources().getDrawable(R.drawable.edittexterro));
                    surepassIcon.setBackground(getResources().getDrawable(R.drawable.ic_password_erro));
                   // editText_sure_pss.setHintTextColor(getResources().getColor(R.color.read));
                }else {
                    editText_sure_pss.setBackground(getResources().getDrawable(R.drawable.edittext));
                    //editText_sure_pss.setHintTextColor(getResources().getColor(R.color.colorTextGrey));
                    surepassIcon.setBackground(getResources().getDrawable(R.drawable.ic_password));
                    showErro2.setText("");
                }
            }
        });

        //手机号码
        editText_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phone = editText_phone.getText().toString().trim();
                if (!Util.isMobileNO(phone)&phone.length()!=0) {
                    showErro3.setText("手机号码格式不正确！");
                    editText_phone.setBackground(getResources().getDrawable(R.drawable.edittexterro));
                    phoneIcon.setBackground(getResources().getDrawable(R.drawable.ic_phone_erro));
                }else {
                    editText_phone.setBackground(getResources().getDrawable(R.drawable.edittext));
                    phoneIcon.setBackground(getResources().getDrawable(R.drawable.ic_phone));
                    showErro3.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        editText_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                   String phone = editText_phone.getText().toString().trim();
                 if (!hasFocus&TextUtils.isEmpty(phone)){
                     showErro3.setText("手机号不能为空！");
                     editText_phone.setBackground(getResources().getDrawable(R.drawable.edittexterro));
                     phoneIcon.setBackground(getResources().getDrawable(R.drawable.ic_phone_erro));
                 }else if (!Util.isMobileNO(phone) & phone.length()!=0){
                     showErro3.setText("手机号码格式不正确！");
                     editText_phone.setBackground(getResources().getDrawable(R.drawable.edittexterro));
                     phoneIcon.setBackground(getResources().getDrawable(R.drawable.ic_phone_erro));
                 }else {
                     editText_phone.setBackground(getResources().getDrawable(R.drawable.edittext));
                     phoneIcon.setBackground(getResources().getDrawable(R.drawable.ic_phone));
                     showErro3.setText("");
                 }
            }
        });

    }

}


