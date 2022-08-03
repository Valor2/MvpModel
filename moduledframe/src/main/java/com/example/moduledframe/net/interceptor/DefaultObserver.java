package com.example.moduledframe.net.interceptor;

import android.util.Log;


import com.example.moduledframe.net.NetConstant;
import com.example.moduledframe.net.ResponseResult;
import com.example.moduledframe.net.interceptor.exception.ApiException;
import com.example.moduledframe.net.interceptor.exception.NoDataExceptionException;
import com.example.moduledframe.net.interceptor.exception.ServerResponseException;
import com.example.moduledframe.utils.EventEntity;
import com.example.moduledframe.utils.spfkey.SPFKey;
import com.example.moduledframe.utils.spfkey.SPfUtil;
import com.google.gson.JsonParseException;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


/**
 * Created by csy on 2017/4/18.
 */

public abstract class DefaultObserver<T> implements Observer<ResponseResult<T>> {
    private Disposable disposable;


    public DefaultObserver() {
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }


    @Override
    public void onNext(ResponseResult<T> response) {
        onSuccess(response);
    }

    @Override
    public void onError(Throwable e) {

        if (e instanceof HttpException) {     //   HTTP错误
            Log.e(NetConstant.logTag, "网络错误1" + e.getMessage());
//            ToastUtil.show(BaseApplication.getContext(), "[HTTP-" + ((HttpException) e).code() + "]" + e.getMessage());
            onException(NetConstant.BAD_NETWORK,"[HTTP-" + ((HttpException) e).code() + "]" + e.getMessage());
        } else if (e instanceof IOException) {
            //   连接错误
//            ToastUtil.show(BaseApplication.getContext(), "请求超时，请检查网络重试");
            onException(NetConstant.CONNECT_ERROR, "请求超时，请检查网络重试");
//            EventBus.getDefault().postSticky(new NetWorkStatu(1));

        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
//            ToastUtil.show(BaseApplication.getContext(), "数据解析出现了问题");
            onException(NetConstant.PARSE_ERROR, "数据解析出现了问题");
            Log.e(NetConstant.logTag, Objects.requireNonNull(e.getMessage()));
        } else if (e instanceof ServerResponseException) {
            ServerResponseException responseException = (ServerResponseException) e;
            int code = responseException.mErrorCode;
            onException(code, e.getMessage());
        } else if (e instanceof ApiException) {
            //服务器错误码
            ApiException apiException = (ApiException) e;
            int code = apiException.getErrorCode();

            //如果服务器返回用户已经退出, 我们要先判断当前是否是登录状态,如果是登录,就发广播通知界面更新,否则不进行多次刷新.
            if ((code == NetConstant.NO_EXPIRED || code == NetConstant.FORCED_TO_LOGOFF_ERROR
                    || code == NetConstant.USER_LOGIN_OUT_OK_ERROR) && SPfUtil.getInstance().getBoolean(SPFKey.IsSingIN)) {//账号过期或账号未登录
                EventBus.getDefault().post(new EventEntity(code,e.getMessage(),""));
                SPfUtil.getInstance().setBoolean(SPFKey.IsSingIN, false);
//                SPfUtil.getInstance().remove(SPFKey.LoginBean);
            }
            onException(code, e.getMessage());
            onException(code, e.getMessage(), ((ApiException) e).getDetailMsg());
        } else if (e instanceof NoDataExceptionException) {
            onSuccess(null);
        } else {
            Log.e(NetConstant.logTag, Objects.requireNonNull(e.getMessage()));
//            ToastUtil.show(BaseApplication.getContext(), "[未知错误]" + Objects.requireNonNull(e.getMessage()));
            onException(NetConstant.Exception, "未知异常");
        }

        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onComplete() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    /**
     * 请求成功
     *
     * @param result 服务器返回的数据
     */
    abstract public void onSuccess(ResponseResult<T> result);

    abstract public void onException(int code, String eMsg);

    /**
     * @param code      错误码
     * @param eMsg      错误数据
     * @param detailMsg 完完全全不做任何处理的后台返回数据
     */
    protected void onException(int code, String eMsg, String detailMsg) {

    }

}
