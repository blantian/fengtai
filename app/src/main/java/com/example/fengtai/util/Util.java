package com.example.fengtai.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;

import static android.os.Environment.MEDIA_MOUNTED;


public class Util {

        /**
         * @description 字符串处理，防止SQL注入
         * @param input
         * @return
         */
        public static String StringHandle(String input){
            String output;
            // 将包含有 单引号(')，分号(;) 和 注释符号(--)的语句替换掉
            output = input.trim().replaceAll(".*([';]+|(--)+).*", " ");
            return output;
        }

        /**
         * Toast封装
         * @param context
         * @param msg
         */
        public static void makeToast(Context context, String msg){
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
        }

        /**
         * 判断sd卡是否存在
         * @return
         */
        public static boolean isSdcardExisting() {
            final String state = Environment.getExternalStorageState();
            if (state.equals(MEDIA_MOUNTED)) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * 判断当前有没有联网状态
         * @param context
         * @return
         */
        public static boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                return false;
            } else {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED
                                || info[i].getState() == NetworkInfo.State.CONNECTING) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param
         * @return 待检测的字符串
         */
        public static boolean isMobileNO(String mobileNums) {
            // "[1]"代表下一位为数字可以是几，
            // "[0-9]"代表可以为0-9中的一个，
            // "[5,7,9]"表示可以是5,7,9中的任意一位,
            // [^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，
            // 有9位
            String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
            if (TextUtils.isEmpty(mobileNums))
                return false;
            else
                return mobileNums.matches(telRegex);
        }



    /**
     * 获取存储路径
     * @return
     */
    public static String getFilePath(Context context, String dir) {
        String directoryPath = "";
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//判断外部存储是否可用
            directoryPath = context.getExternalFilesDir(dir).getAbsolutePath();
        } else {//没外部存储就使用内部存储
            directoryPath = context.getFilesDir() + File.separator + dir;
        }
        return directoryPath;

    }

}


