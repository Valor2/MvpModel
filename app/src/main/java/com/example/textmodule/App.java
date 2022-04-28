package com.example.textmodule;

import android.app.Application;

import com.example.moduledframe.BaseManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BaseManager.getInstance().init(this);
    }
}
