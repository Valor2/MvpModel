package com.example.textmodule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.moduledframe.BaseManager;
import com.example.moduledframe.base.BaseActivity;
import com.example.moduledframe.mvpbase.presenter.BaseNullKtPresenter;
import com.example.moduledframe.utils.LogUtil;
import com.example.moduledframe.utils.ToastUtil;
import com.example.moduleh5.h5.H5WebActivity;
import com.example.textmodule.mvp.MvpActivity;


public class MainActivity extends BaseActivity<BaseNullKtPresenter> {


    @Override
    public int initView(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        mColorId = R.color.colors_gules;
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
//        ARouter.getInstance().inject(this); // ARouter 注入

        findViewById(R.id.mvp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.show("跳转mvp");
                MvpActivity.Companion.startActivity(getBaseContext(),"1");
            }
        });

        findViewById(R.id.html5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                H5WebActivity.Companion.startActivity(getBaseContext(), "https://www.baidu.com/", "wode标题");
            }
        });
        findViewById(R.id.User).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/moduleUser/UserActivity").navigation(getBaseContext(), new NavCallback() {

                    @Override
                    public void onFound(Postcard postcard) {
                        LogUtil.i("zhao", "onArrival: 找到了 ");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                         LogUtil.i("zhao", "onArrival: 找不到了 ");
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        LogUtil.i("zhao", "onArrival: 跳转完了 ");
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        LogUtil.i("zhao", "onArrival: 被拦截了 ");
                    }
//                ARouter.getInstance().build("/com/Activity1").navigation( this , 100 );
                });

            }
        });
    }


}