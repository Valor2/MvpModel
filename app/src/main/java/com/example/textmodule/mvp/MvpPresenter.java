package com.example.textmodule.mvp;


import com.example.moduledframe.mvpbase.presenter.BasePresenter;
import com.example.moduledframe.net.ResponseResult;
import com.example.moduledframe.net.interceptor.DefaultObserver;
import com.example.textmodule.net.CourseRetrofit;

import java.util.List;

public class MvpPresenter extends BasePresenter<MvpContract.View> {
    @Override
    public void onStart() {
    }

    //
    public void getCourse(String strpath,String contentEt){
        CourseRetrofit.getCourse(new DefaultObserver<List<Object>>() {
            @Override
            public void onSuccess(ResponseResult<List<Object>> result) {

            }

            @Override
            public void onException(int code, String eMsg) {

            }
        },"");
    }


}
