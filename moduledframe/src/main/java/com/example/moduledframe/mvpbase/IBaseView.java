package com.example.moduledframe.mvpbase;

import android.content.Context;
import android.content.DialogInterface;

/**
 * View层接口基类
 *
 * @author Hunter
 */
public interface IBaseView {

    /**
     * 获取上下文对象
     * @return
     */
    Context getContext();

    /**
     * 显示进度条
     * @param flag    是否可取消
     * @param message 要显示的信息
     */
    void showProgress(boolean flag, String message);

    /**
     * 定时关闭，即最多等待时间就关闭进度条
     * @param message
     * @param sec     秒数
     */
    void showProgress(String message, int sec);

    /**
     * 显示可取消的进度条
     * @param message 要显示的信息
     */
    void showProgress(String message);

    /**
     * 显示可取消的进度条
     * @param strRes
     */
    void showProgress(int strRes);

    /**
     * 隐藏进度条
     */
    void hideProgress();

    /**
     * 设置取消进度条监听
     * @param onCancelListener
     */
    void setProgressCancelListener(DialogInterface.OnCancelListener onCancelListener);

    /**
     * 根据字符串弹出toast
     * @param msg 提示内容
     */
    void showToast(String msg);

    /**
     * 根据字符串资源弹出toast
     * @param res
     */
    void showToast(int res);


}
