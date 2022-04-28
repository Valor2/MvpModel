package com.example.moduledframe.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moduledframe.BaseManager;
import com.example.moduledframe.R;


/**
 * 公共的Toast
 * @author kenny
 * @date 2016/3/18.
 */
public class ToastUtil {

    private static Toast sToast = null;
    private static String lastToastText;
    private static int theSameCount = 0;//提示语连续相同的次数
    private final static int SAME_MAX = 3;//连续三次相同不提示,超过三次就清0,重新提示
    private final static long MAX_DELAY = 1500L;//相同提示的时效,即超过这个时间, 相同提示也可以提示
    private static long lastTimeMillis = 0L;//上一次相同提示的时刻

    private static long lastTimeMillisNotSame = 0;

    public static void show(Context context, String msg){
        shwoToast(context, msg, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String msg, int duration) {
        if(TextUtils.isEmpty(msg) || context == null){
            return;
        }

        if(duration < Toast.LENGTH_SHORT){
            duration = Toast.LENGTH_SHORT;
        }

        shwoToast(context, msg, duration);
    }

    public static void show(String msg, int duration) {
        shwoToast(BaseManager.getInstance().getContext(), msg, duration);
    }

    /**
     * short show Toast
     * @param msg
     * @return
     */
    public static void show(String msg) {

        show(msg, Toast.LENGTH_SHORT);
    }

    /**
     * show long Toast
     * @param msg
     * @return
     */
    public static void showLong(String msg) {
        shwoToast(BaseManager.getInstance().getContext(),msg, Toast.LENGTH_LONG);
    }

    public static void showLong(int msg) {
        shwoToast(BaseManager.getInstance().getContext(), BaseManager.getInstance().getContext().getString(msg), Toast.LENGTH_LONG);
    }

    /**
     * short show Toast
     * @param msg
     * @return
     */
    public static void showShort(String msg) {

        show(msg, Toast.LENGTH_SHORT);
    }

    public static void showShort(int msg) {
        shwoToast(BaseManager.getInstance().getContext(), BaseManager.getInstance().getContext().getResources().getString(msg), Toast.LENGTH_SHORT);
    }

    public static void shwoToast(Context context,String msg,int duration) {
//        long startTime = System.currentTimeMillis(); //起始时间
        if(!isTheSame(msg)){
//            if(sToast == null){
                sToast = Toast.makeText(context, msg, duration);//new Toast(context);
                View view = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
                sToast.setView(view);
                sToast.setGravity(Gravity.CENTER, 0, 0);
//            }
            TextView tv =  sToast.getView().findViewById(R.id.text);
            tv.setText(msg);
            sToast.setDuration(duration);
            sToast.show();

        }
//        long endTime = System.currentTimeMillis(); //结束时间
//        long runTime = endTime - startTime;
//        LogUtil.i("test", String.format("方法使用时间 %d ms", runTime));
    }

    private static synchronized boolean isTheSame(String text){
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastTimeMillis > MAX_DELAY){
            lastTimeMillis = currentTime;
            theSameCount = 0;
            return false;
        }
        if(lastToastText!=null && lastToastText.equals(text)){
            if(SAME_MAX == theSameCount){
                theSameCount = 0;
                return false;
            }else {
                theSameCount++;
            }
            return true;
        }else{
            theSameCount = 0;
        }
        lastToastText = text;
        return false;
    }

    /**
     * 相同的提示 ,5秒提示一次. 常用语网络频繁链接失败
     * @param msg
     */
    public static void shwoToastSame(String msg){
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTimeMillisNotSame > 5000) {
            lastTimeMillisNotSame = currentTime;
            show(BaseManager.getInstance().getContext(), msg);
        }
    }

}