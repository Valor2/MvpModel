package com.example.moduleuser

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.moduledframe.base.BaseActivity
import com.example.moduledframe.mvpbase.presenter.BaseNullKtPresenter
import kotlinx.android.synthetic.main.activity_user.*

@Route(path ="/moduleUser/UserActivity")

class UserActivity : BaseActivity<BaseNullKtPresenter>() {

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_user
    }

    override fun initData() {
        ARouter.getInstance().inject(this) // ARouter 注入

        button1.setOnClickListener {
            ARouter.getInstance().build("/h5/H5WebActivity").navigation()
        }
    }
}