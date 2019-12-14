package com.example.fengtai.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.fengtai.MyApplication;
import com.example.fengtai.R;
import com.example.fengtai.activity.BottomNavigationActivity;
import com.example.fengtai.entity.LoginResult;
import com.example.fengtai.entity.UserInfoResult;
import com.example.fengtai.entity.base.Result;
import com.example.fengtai.util.memoryUtils.ExternalStorageUtils;
import com.example.fengtai.util.viewUtil.SubmitControl;
import com.example.fengtai.util.Util;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import fengtaiapp.db.DaoMaster;
import fengtaiapp.db.DaoSession;
import fengtaiapp.db.ProductDao;
import fengtaiapp.db.UserInfo;
import fengtaiapp.db.UserInfoDao;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.fengtai.MyApplication.API;
import static com.example.fengtai.fragment.Fragment_home.readStream;

public class LoginActivity extends AppCompatActivity {


    private static final int REQUEST_CODE_GO_TO_REGIST = 1;
    private static final int REQUEST_CODE_GO_TO_REMAKPASS = 2;


    //数据库操作
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private SQLiteDatabase db;
    private UserInfoDao userInfoDao;
    private ProductDao productDao;
    private String path;

    private UserInfoResult userInfoResults ;
    private LoginResult loginResult;
    private ProgressBar progressBar;
    private EditText accountText;
    private EditText passwordText;
    private TextView remakePass;
    private TextView textView_sign;
    private Button button_login;
    private ImageView passErro;
    private TextView showErro;
    private ToggleButton toggleButton;

    private String account;
    private String password;
    private String _account;
    private String _password;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
        setListenners();
        openDB();
        SharedPreferences sharedPreferences = getSharedPreferences("myUserMsg",MODE_PRIVATE);
        _account = sharedPreferences.getString("account","");
        _password =sharedPreferences.getString("password","");
        accountText.setText(_account);
        passwordText.setText(_password);
        checkUserInfo();
    }

    //回调函数，用来处理自动填充用户名密码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_GO_TO_REGIST:
                if (resultCode == Activity.RESULT_OK) {
                    //则获取data中的账号和密码  动态设置到EditText中
                    String name = data.getStringExtra("name");
                    String pwd = data.getStringExtra("pwd");
                    accountText.setText(name);
                    passwordText.setText(pwd);
                }
                break;

            case REQUEST_CODE_GO_TO_REMAKPASS:

                if (resultCode == Activity.RESULT_OK) {
                    //则获取data中的账号和密码  动态设置到EditText中
                    String name = data.getStringExtra("rname");
                    String pwd = data.getStringExtra("rpwd");

                    accountText.setText(name);
                    passwordText.setText(pwd);
                }
                break;
            default:
                break;
        }
    }
    void initComponents() {
        accountText = findViewById(R.id.user_name_login);
        passwordText = findViewById(R.id.user_pass_login);
        remakePass = findViewById(R.id.remmber_pass);
        textView_sign = findViewById(R.id.bttn_sign_login);
        button_login = findViewById(R.id.bttn_loign_in);
        progressBar = findViewById(R.id.progressbar);
        toggleButton = findViewById(R.id.togglebtton);
        passErro = findViewById(R.id.passerro);
        showErro = findViewById(R.id.showerro);
    }

    /**
     * 隐藏显示密码
     */
    void setListenners() {
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
        SubmitControl submitControl = new SubmitControl();
        submitControl.initConroller();
        submitControl.addView(accountText, passwordText);
        submitControl.setSubmitButton(button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isNetworkAvailable(LoginActivity.this)) {
                    //todo 判断网络链接，实行本地与API操作。
                   NetWorkStatus();
                } else {
                    Login();
                }
            }
        });
        textView_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivityForResult(intent, REQUEST_CODE_GO_TO_REGIST);
            }
        });
        remakePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RemakePassActivity.class);
                startActivityForResult(intent, REQUEST_CODE_GO_TO_REMAKPASS);
            }
        });

    }

    /**
     * 网络状态：有网模式，无网络模式
     */
    public void NetWorkStatus() {
        final AlertDialog.Builder networkstatus = new AlertDialog.Builder(LoginActivity.this);
        networkstatus.setTitle("提示：");
        networkstatus.setMessage("您处于无网络服务状态，是否以离线状态登录？");
        networkstatus.setIcon(R.drawable.ic_network_erro);
        networkstatus.setCancelable(true);

        //设置按钮
        networkstatus.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //查询数据库
                checkUserInfo();
            }
        });
        //取消按钮
        networkstatus.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        networkstatus.show();
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
     * API 方式登录
     */
    private void Login() {
        //前段参数校验，防SQL注入
        account = Util.StringHandle(accountText.getText().toString());
        password = Util.StringHandle(passwordText.getText().toString());
        //检查用户名密码是否为空
        final String resMsg = checkDataValid(account, password);
        if (!resMsg.equals("")) {
            showResponse(resMsg);
            return;
        }
        progressBar.setVisibility(View.VISIBLE);//显示进度条
        // OptionHandle(account, password);//处理记住密码
        //        网络请求
        API
                .loginUser(RequestBody.create(MediaType.parse(""), account)
                        , RequestBody.create(MediaType.parse(""), password))
                .enqueue(new Callback<Result<LoginResult>>() {
                    @Override
                    public void onResponse(@NotNull retrofit2.Call<Result<LoginResult>> call, @NotNull retrofit2.Response<Result<LoginResult>> response) {
                        if (response.body().getStatus()==200) {
                            showResponse(response.body().getMessage());
                            loginResult = response.body().getData();
                            MyApplication.userId = loginResult.getUser_id();
                            API
                                    .getUserInfo(MyApplication.userId).enqueue(new Callback<Result<UserInfoResult>>() {
                                @Override
                                public void onResponse(Call<Result<UserInfoResult>> call, Response<Result<UserInfoResult>> response) {
                                    userInfoResults = response.body().getData();
                                    final String url = userInfoResults.getAvatar();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                final Context context = getApplicationContext();
                                                FutureTarget<File> target = Glide.with(context)
                                                        .asFile()
                                                        .load(url)
                                                        .submit();
                                                final File file = target.get();
                                                //下载的图片保存到本地
                                                ExternalStorageUtils.writeExternalStoragePrivate("productPicIcon",LoginActivity.this,"Avatar.png",readStream(String.valueOf(file)));
                                                //获取存储文件路径
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                    path = Util.getFilePath(LoginActivity.this,"productPicIcon/Avatar.png");
                                    //设置自动记住用户名密码密码功能
                                    SharedPreferences sharedPreferences = getSharedPreferences("myUserMsg",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("account",userInfoResults.getUsername());
                                    editor.putString("password",password);
                                    editor.commit();
                                    addUserInfo();
                                    //todo 创建用户个人信息表
                                    startActivity(new Intent(LoginActivity.this, BottomNavigationActivity.class));
                                }

                                @Override
                                public void onFailure(Call<Result<UserInfoResult>> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });

                        } else {
                            showResponse(response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Result<LoginResult>> call, Throwable t) {
                        t.printStackTrace();
                        showResponse("用户名密码错误");
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

    //判断数据库中用户名是否存在
    private void openDB(){
        db = new DaoMaster.DevOpenHelper(LoginActivity.this,"fengtai.db",null).getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        userInfoDao = daoSession.getUserInfoDao();
    }

    //把用户信息保存到数据库
    private void addUserInfo(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userInfoResults.getUsername());
        userInfo.setUser_id(userInfoResults.getUser_id());
        userInfo.setAge(userInfoResults.getAge());
        userInfo.setPhone(userInfoResults.getPhone());
        userInfo.setAddress(userInfoResults.getAddress());
        userInfo.setAvatar(path);
        userInfo.setId_card(userInfoResults.getId_card());
        userInfo.setId(Long.valueOf(userInfoResults.getUser_id()));
        userInfo.setCreated_at(userInfoResults.getCreated_at());
        userInfo.setSex(Boolean.valueOf(userInfoResults.getSex()));
        userInfo.setShengdata(userInfoResults.getShengdata());
        userInfo.setShidata(userInfoResults.getShidata());
        userInfo.setXiandata(userInfoResults.getXiandata());
        userInfoDao.insertOrReplace(userInfo);

    }

    private void checkUserInfo(){
        List<UserInfo> list =userInfoDao.queryBuilder().list();
        for (UserInfo userInfo:list){
            Log.d("UserInfo"," " + userInfo);

        }
    }

}
