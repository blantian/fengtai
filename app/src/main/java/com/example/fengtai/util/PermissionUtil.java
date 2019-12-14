package com.example.fengtai.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/*
=====================
    动态申请权限
=====================
 */
public class PermissionUtil {

    /**
     * 申请单个权限
     * @param activity
     * @param context
     * @param PermissionRequest
     * @return
     */
    public static boolean CheckPermission(Activity activity, Context context, String PermissionRequest){

        int permission=-1;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //检查是否有权限
            permission = ContextCompat.checkSelfPermission(context, PermissionRequest);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                activity.requestPermissions(new String[]{PermissionRequest}, 1);
            }
        }
        else {
            permission= PackageManager.PERMISSION_GRANTED;
        }
        return permission== PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 申请多个权限
     * @param activity
     * @param context
     * @param PermissionRequests
     * @return
     */
    public static boolean CheckPermissions(Activity activity, Context context, String[] PermissionRequests){
        boolean PermissionRequest=true;
        int permission=-1;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for(int i=0;i<PermissionRequests.length;i++) {
                permission = ContextCompat.checkSelfPermission(context, PermissionRequests[i]);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    PermissionRequest=false;
                    break;
                }
            }
        }
        return PermissionRequest;
    }
    public static void RequestPermission(Activity activity, Context context, String... PermissionRequest){
        int permission=-1;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> listPermissions = new ArrayList<>();

            for (int i = 0; i < PermissionRequest.length; i++) {
                permission = ContextCompat.checkSelfPermission(context, PermissionRequest[i]);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    listPermissions.add(PermissionRequest[i]);
                }
            }
            String[] stringsPermissions= new String[listPermissions.size()];
            stringsPermissions=listPermissions.toArray(new String[listPermissions.size()]);
            if(stringsPermissions.length>0){
                activity.requestPermissions(stringsPermissions,1);
            }
        }
    }
}
