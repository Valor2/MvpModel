package com.example.textmodule.mvp;


import com.example.moduledframe.mvpbase.BaseView;

/**
 */
public interface MvpContract {

    interface View extends BaseView {
        void showSuccess(int rat, String message, Object data);//成功
        void onFailt(int code, String message); //失败

    }


}
