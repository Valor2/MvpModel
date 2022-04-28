package com.example.moduleh5;

import android.app.Application;
import android.content.Context;

public class BaseH5Manager {

    private static BaseH5Manager BaseH5Manager;
    private Application application;

    public static BaseH5Manager getInstance() {
        if (BaseH5Manager == null) {
            BaseH5Manager = new BaseH5Manager();
        }
        return BaseH5Manager;
    }

    public void init(Application application) {
        this.application = application;
    }

    public Context getContext() {
        return application.getApplicationContext();
    }

    public Application getApplication() {
        return application;
    }

}
