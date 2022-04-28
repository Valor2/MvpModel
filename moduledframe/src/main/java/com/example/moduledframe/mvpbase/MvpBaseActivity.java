package com.example.moduledframe.mvpbase;

import android.os.Bundle;

import com.example.moduledframe.mvpbase.mvputils.TUtil;
import com.example.moduledframe.mvpbase.presenter.BasePresenter;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;


/**
 * Created by：wsl
 * Created Time：2020/5/13
 */
public abstract class MvpBaseActivity<P extends BasePresenter> extends RxAppCompatActivity implements BaseView{

    private static final String TAG = "MvpBaseActivity";

    private static final int LOADER_ID = 101;
    //p 层实例
    private BasePresenter mPresenter;


    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (mPresenter == null) {
            mPresenter = getPresenterInstance();
        }

        super.onCreate(savedInstanceState);

        if (mPresenter != null) {
            mPresenter.setVM(MvpBaseActivity.this);
            mPresenter.setContext(this);
        }

        if (mPresenter != null) {
            mPresenter.onStart();
        }

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    public void setPresenter(BasePresenter basePresenter){
        this.mPresenter = basePresenter;
    }

    //获取 Presenter 实例
    private P getPresenterInstance() {
        return TUtil.getT(MvpBaseActivity.this, 0); //泛型首个参数
    }

    @SuppressWarnings("unchecked")
    public P getPresenter() {
        if (mPresenter == null) return null;
        return (P) mPresenter;
    }

}
