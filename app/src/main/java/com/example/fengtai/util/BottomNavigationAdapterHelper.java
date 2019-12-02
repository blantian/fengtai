package com.example.fengtai.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;

import java.lang.reflect.Field;

public class BottomNavigationAdapterHelper {

    @RequiresApi(api= Build.VERSION_CODES.KITKAT)
    @SuppressLint( "RestrictedApi")
    public static  void disableShiftMode(BottomNavigationView bottomNavigationView)
    {
        BottomNavigationMenuView bottomNavigationMenuView=(BottomNavigationMenuView) bottomNavigationView.getChildAt(0);

        try {
            //反射
            Field shifMode=bottomNavigationMenuView.getClass().getDeclaredField("mShifMode");
            shifMode.setAccessible(true);
            shifMode.setBoolean(bottomNavigationMenuView,false);
            shifMode.setAccessible(false);

            for(int i=0;i<bottomNavigationMenuView.getChildCount();i++)
            {
                BottomNavigationItemView itemView=(BottomNavigationItemView) bottomNavigationView.getChildAt(i);

                itemView.setChecked(itemView.getItemData().isChecked());

            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

}