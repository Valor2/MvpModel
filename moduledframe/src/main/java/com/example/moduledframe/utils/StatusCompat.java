 package com.example.moduledframe.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.moduledframe.utils.spfkey.SPFKey;
import com.example.moduledframe.utils.spfkey.SPfUtil;


 /**
  * Created by DaiBin on 2018\7\31
  */
 public class StatusCompat {


     public static void setStatusBarColor(Activity activity, int translucentPrimaryColor) {
         boolean isStatusIconStable=true;
         try{
             if(SPfUtil.getInstance().getBoolean(SPFKey.WhiteAndheaven)){
                 isStatusIconStable=true;
             }
         }catch (Exception e){
             e.printStackTrace();
         }
         setStatusBarColor(activity, isStatusIconStable, translucentPrimaryColor);
     }


     /**
      * 沉浸式适配6.0以上
      *
      * @param activity
      * @param isStatusIconStable      状态栏图标是否浅色
      * @param translucentPrimaryColor 状态栏背景色
      */
     public static void setStatusBarColor(Activity activity, boolean isStatusIconStable, int translucentPrimaryColor) {
         Window window = activity.getWindow();
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             // 透明状态栏
             activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
             // 透明导航栏
             activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

             window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
             window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
             window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

             window.setStatusBarColor(translucentPrimaryColor);
 //            window.setStatusBarColor(0x00FFFFFF);
             if (!isStatusIconStable) {
 //                //设置状态栏文字颜色及图标为深色
                 window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
             } else {
                 //设置状态栏文字颜色及图标为浅色
                 window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
             }
         }else {
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                 window.getDecorView()
                         .setSystemUiVisibility(
                                 View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
             } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                 window.setStatusBarColor(Color.BLACK);
             }

         }
     }


     public static void setStatusBarColors(Activity activity, int colorId) {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             Window window = activity.getWindow();
             window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
             window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
             window.setStatusBarColor(activity.getResources().getColor(colorId));
             window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
         } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
             //使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
 //            transparencyBar(activity);
 //            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
 //            tintManager.setStatusBarTintEnabled(true);
 //            tintManager.setStatusBarTintResource(colorId);
         }
     }


 }
