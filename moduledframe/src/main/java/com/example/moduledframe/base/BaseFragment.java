
package com.example.moduledframe.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;


import com.example.moduledframe.mvpbase.IBaseView;
import com.example.moduledframe.mvpbase.MvpBaseFragment;
import com.example.moduledframe.mvpbase.presenter.BasePresenter;
import com.example.moduledframe.net.NetWorkStatu;
import com.example.moduledframe.utils.EventEntity;
import com.example.moduledframe.utils.ToastUtil;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 基础Fragment
 *
 * @author kenny
 * @date 2020/5/14.
 */
public abstract class BaseFragment<P extends BasePresenter> extends MvpBaseFragment<P> implements IBaseView {

    public final String TAG = BaseFragment.this.getClass().getSimpleName();
    public ViewGroup mView;
    public boolean mShowTitleBar = true;
    protected Context mContext;

    protected Unbinder mBinder;
    protected boolean isKt=false;
    protected Gson gson = new Gson();
    protected View contentView;


    public BaseFragment() {
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        int resId = getContentViewResId();

        //获取fragment需要显示的view
        contentView = getContentView();
        if (contentView == null) {
            //获取资源Id为-1不初始化
            if (resId != -1) {
                //初始化容器,获取容器实体View
                contentView = LayoutInflater.from(getActivity()).inflate(getContentViewResId(), null);
            }
        }

        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (contentView != null) {
            initToolBar(contentView);
            if(!isKt){
                //是否绑定依赖注入
                mBinder = ButterKnife.bind(this, contentView);
            }

            //回调initViews
            initView(contentView, savedInstanceState);

            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }


        }
    }

    /**
     * 获取Activity
     * @return
     */



    @Override
    public void showProgress(boolean flag, String message) {
        if (getStatus()) {
            getBaseActivity().showProgress(flag, message);
        }
    }

    @Override
    public void showProgress(String message) {
        showProgress(true, message);
    }

    public void showProgress(String msg, int sec) {
        getBaseActivity().showProgress(msg, sec);
    }

    @Override
    public void showProgress(int strRes) {
        showProgress(getString(strRes));
    }

    @Override
    public void hideProgress() {
        try {
            getBaseActivity().hideProgress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setProgressCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        getBaseActivity().setProgressCancelListener(onCancelListener);
    }

    @Override
    public void showToast(int res) {
        ToastUtil.show(res+"");
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.show(msg);
    }

    public boolean isAttachedToActivity() {
        return !isRemoving() && contentView != null;
    }

    public void dealNetWorkStatu(NetWorkStatu event) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Event(NetWorkStatu event) {//网络状态监听
        dealNetWorkStatu(event);
    }

    public abstract int getContentViewResId();

    public abstract View initView(@NotNull View contentView, @Nullable Bundle savedInstanceState);

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventEntity eventEntity) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }


    public Boolean isSingIN() {
        return getBaseActivity().isSingIN();
    }

    private void initToolBar(View view) {
    }

    public View getContentView() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mBinder != null) {
            mBinder.unbind();
        }
        contentView = null;
        try {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //是否显示TitleBar
    private boolean isShowTitleBar() {
        return mShowTitleBar;
    }


    public void processMessage(Message message) {
    }

    /**
     * 验证Activit 和 fragment 是否活动中
     * @return true 活动中   false 已退出
     */
    public boolean validateFragmentIsActivitys() {
        if (getActivity() == null || getActivity().isFinishing() || !isAdded()) {
            return false;
        }
        return true;
    }

    public Context getApplicationContext() {
        if (getActivity() != null) {
            return getActivity().getApplicationContext();
        }
        return getContext();
    }


    public boolean onBackPressed() {
        return false;
    }

    public AlertDialog mAlertDialog;

    public AlertDialog showConfirmDialog(String message,
                                         DialogInterface.OnClickListener confirmListener,
                                         boolean cancelBtn) {
        dismissConfirmDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("系统提示");
        builder.setMessage(message);
        builder.setPositiveButton("确认", confirmListener);
        if (cancelBtn) {
            builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());
        }
        mAlertDialog = builder.create();
        mAlertDialog.show();
        return mAlertDialog;
    }

    public void dismissConfirmDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }

    /**
     * 获取当前Fragment状态
     * @return true为正常 false为未加载或正在删除
     */
    private boolean getStatus() {
        return (isAdded() && !isRemoving());
    }

    /**
     * 获取Activity
     * @return
     */
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    public void startActivity(Class cls) {
        startActivity(new Intent(getContext(), cls));
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        dismissConfirmDialog();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
    }

    protected void isKtGrammar(){
        isKt=true;
    }

}
