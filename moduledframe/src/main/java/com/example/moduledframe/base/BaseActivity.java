package com.example.moduledframe.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.view.InflateException;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;


import com.example.moduledframe.R;
import com.example.moduledframe.mvpbase.MvpBaseActivity;
import com.example.moduledframe.mvpbase.presenter.BasePresenter;
import com.example.moduledframe.utils.ActivityCollector;
import com.example.moduledframe.utils.EventEntity;
import com.example.moduledframe.utils.StatusCompat;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity <P extends BasePresenter> extends MvpBaseActivity<P> {
    //获取TAG的activity名称
    protected final String TAG = this.getClass().getSimpleName();

    //是否允许旋转屏幕
    private boolean isAllowScreenRoate = true;
    //封装Toast对象
    private static Toast toast;

    public Context context;

    protected Unbinder mBinder;
    protected boolean isKt=false;

    private View layoutBack;
    public int mColorId;//顶部颜色ID

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
        //初始化控件

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

    /**
     * 显示提示  toast
     *
     * @param msg 提示信息
     */
    @SuppressLint("ShowToast")
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