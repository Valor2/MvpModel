package com.example.moduledframe;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;



public class BaseManager {

    private static BaseManager baseMvpManager;
    private Application application;

    public static BaseManager getInstance() {
        if (baseMvpManager == null) {
            baseMvpManager = new BaseManager();
        }
        return baseMvpManager;
    }

    public void init(Application application) {
        this.application = application;

        ARouterInit();
    }

    public Context getContext() {
        return application.getApplicationContext();
    }

    public Application getApplication() {
        return application;
    }

    /**
     * 初始化ARouter 框架
     */
    private void ARouterInit(){
        ARouter.openLog();
        ARouter.openDebug();// 是否打开debug运行
        ARouter.init(application);
    }

    /**
     * 注销ARouter 框架，需要在Application 的onTerminate方法调用
     */
    public void ARouterDestroy(){
        ARouter.getInstance().destroy();
    }

}
