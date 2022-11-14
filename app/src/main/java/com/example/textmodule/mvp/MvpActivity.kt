package com.example.textmodule.mvp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.example.moduledframe.base.BaseActivity
import com.example.textmodule.R

class MvpActivity : BaseActivity<MvpPresenter>() {

    companion object {
        fun startActivity(ctx: Context, draftBean: String) {
            val to = Intent(ctx, MvpActivity::class.java) //PersonCenterActivity
            if(draftBean != null){
                to.putExtra("11111", draftBean)
            }
            to.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ctx.startActivity(to)
        }
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return  R.layout.activity_mvp;
    }

    override fun initData() {
        val textView = findViewById<TextView>(R.id.textTitle)
        textView.text = "自定义接口请求"

        presenter.getCourse("","")
        presenter.getCourse1("","")
    }
}