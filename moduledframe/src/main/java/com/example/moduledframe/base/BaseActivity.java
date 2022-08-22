package com.example.moduledframe.base;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.view.InflateException;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.moduledframe.R;
import com.example.moduledframe.dialog.LoadingDialogFm;
import com.example.moduledframe.mvpbase.IBaseView;
import com.example.moduledframe.mvpbase.MvpBaseActivity;
import com.example.moduledframe.mvpbase.presenter.BasePresenter;
import com.example.moduledframe.utils.ActivityCollector;
import com.example.moduledframe.utils.AntiShakeUtil;
import com.example.moduledframe.utils.EventEntity;

import com.example.moduledframe.utils.StatusCompat;
import com.example.moduledframe.utils.Timer_Task;
import com.example.moduledframe.utils.spfkey.SPFKey;
import com.example.moduledframe.utils.spfkey.SPfUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity <P extends BasePresenter> extends MvpBaseActivity<P> implements IBaseView {
    //获取TAG的activity名称
    protected final String TAG = this.getClass().getSimpleName();

    //是否允许旋转屏幕
    private boolean isAllowScreenRoate = true;
    //封装Toast对象
    private  Toast toast;
    public Context context;
    protected Unbinder mBinder;

    private View layoutBack;
    public int mColorId;//顶部颜色ID

    private static LoadingDialogFm loading;
    private Timer_Task loadingTimer;
    private MyHandler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        //activity管理
        ActivityCollector.addActivity(this);
//        ARouter.getInstance().inject(this);// ARouter 注入
        try {
            int layoutResID =   initView(savedInstanceState);
            if (layoutResID != 0) {
                setContentView(layoutResID);
                layoutBack();
                //是否绑定依赖注入
                mBinder = ButterKnife.bind(this);
                getSupportActionBar().hide();
            }
        } catch (Exception e) {
            if (e instanceof InflateException) {
                throw e;
            }
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        if(mColorId == 0){
            StatusCompat.setStatusBarColor(this,  R.color.white);
        }else {
            StatusCompat.setStatusBarColor(this,   mColorId);
        }

        //设置屏幕是否可旋转
        if (!isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // 背景颜色
//            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
            // 黑色文字
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // 白色文字
            // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }

        //设置数据
        initData();
    }

    /**
     * 初始化控件
     */
    public abstract int initView(@Nullable Bundle savedInstanceState);

    /**
     * 设置数据
     */
    protected abstract void initData();

    public void setProgressCancelListener(DialogInterface.OnCancelListener onCancelListener) {
    }
    public synchronized void showProgress(final boolean flag, final String message) {
        if (loading != null) {
            loading.dismiss();
            loading = null;
        }

        if(AntiShakeUtil.check()){
            return;
        }

        loading = new LoadingDialogFm(message);
        try{
            if(ActivityManager.isUserAMonkey()){
                loading.setCancelable(true);
            }else{
                loading.setCancelable(true);//点击外部可取消  2020/8/25 by csj
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        loading.show(getSupportFragmentManager(), "");
    }

    public void hideProgress() {
        try {
            if(loadingTimer != null){
                loadingTimer.stopTimer();
            }
            if (loading != null) {
                if (!loading.canClose()) {
                    getHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                if(loading != null){
                                    loading.dismiss();
                                    loading = null;
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }, 300);
                }else{
                    loading.dismiss();
                    loading = null;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public MyHandler getHandler() {
        if (mHandler == null) {
            mHandler = new MyHandler(this);
        }
        return mHandler;
    }

    public static class MyHandler extends android.os.Handler {
        private WeakReference<BaseActivity> mWeakReference;
        public MyHandler(BaseActivity act) {
            mWeakReference = new WeakReference<>(act);
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            BaseActivity act = mWeakReference.get();
            if (act != null) {
                act.processMessage(msg);
            }
            if (msg.what == 400) {
                try {
                    if (loading != null) {
                        if (loading.getTagSec() == 2) {
                            loading.dismiss();
                            loading = null;
                            if (act != null) {
                                act.showToast("网络不可用,请稍后再试!");
                            }
                        } else {
                            if (act != null) {
                                act.showProgress("网络信号不太好,请耐心等待!", 30);
                            }
                            loading.setTagSec(2);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void processMessage(Message message) {
    }

    public synchronized void showProgress(String message, int sec) {
        try{
            showProgress(true,message);
            if(sec > 0){
                if(loadingTimer == null){
                    loadingTimer = new Timer_Task(getHandler(),400,sec * 1000L,null,1);
                }
                loadingTimer.startTimer();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendMessage(int what, Object obj) {
        if (mHandler != null) {
            Message msg = mHandler.obtainMessage();
            msg.what = what;
            msg.obj = obj;
            mHandler.sendMessage(msg);
        }
    }

    public void sendHandlerMessage(int what) {
        Message msg = Message.obtain();
        msg.what = what;
        if (mHandler != null) {
            mHandler.sendMessage(msg);
        } else {
//            LogUtil.i(TAG, "baseHandler为空");
        }
    }

    public Boolean isSingIN() {
        return SPfUtil.getInstance().getBoolean(SPFKey.IsSingIN);
    }

    /**
     * 是否允许屏幕旋转
     *
     * @param allowScreenRoate true or false
     */
    public void setAllowScreenRoate(boolean allowScreenRoate) {
        isAllowScreenRoate = allowScreenRoate;
    }

    /**
     * 保证同一按钮在1秒内只会响应一次点击事件
     */
    public abstract class OnSingleClickListener implements View.OnClickListener {
        //两次点击按钮之间的间隔，目前为1000ms
        private static final int MIN_CLICK_DELAY_TIME = 1000;
        private long lastClickTime;

        public abstract void onSingleClick(View view);

        @Override
        public void onClick(View view) {
            long curClickTime = System.currentTimeMillis();
            if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                lastClickTime = curClickTime;
                onSingleClick(view);
            }
        }
    }

    /**
     * 同一按钮在短时间内可重复响应点击事件
     */
    public abstract class OnMultiClickListener implements View.OnClickListener {
        public abstract void onMultiClick(View view);

        @Override
        public void onClick(View v) {
            onMultiClick(v);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgress(String message) {
        showProgress(message,15);
    }

    @Override
    public void showProgress(int strRes) {
        showProgress(getString(strRes));
    }

    @Override
    public void showToast(int res) {
        showToast(getString(res));
    }

    /**
     * 显示提示  toast
     *
     * @param msg 提示信息
     */
    @Override
    public void showToast(String msg) {
        try {
            if (null == toast) {
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventEntity eventEntity) {

    }

    public void layoutBack() {
        try{
            Resources res = getResources();
            final String packageName = getPackageName();
            layoutBack = findViewById(res.getIdentifier("layoutBack", "id", packageName));
            if (layoutBack != null) {
                layoutBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finishThisPage();
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    protected void finishThisPage() {
        this.finish();
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBinder != null) {
            mBinder.unbind();
        }
        //activity管理
        ActivityCollector.removeActivity(this);
    }



}