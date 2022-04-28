package com.example.moduleh5.h5

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.webkit.WebSettings
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.moduledframe.base.BaseActivity
import com.example.moduledframe.mvpbase.presenter.BaseNullKtPresenter
import com.example.moduleh5.R
import kotlinx.android.synthetic.main.activity_h5_web1.*

@Route(path = "/h5/H5WebActivity")
class H5WebActivity : BaseActivity<BaseNullKtPresenter>() {


    var url = "https://www.baidu.com/"
    companion object {
        val urlKey = "url"
        val titleKey = "title"
        var isShowBarKey = "isShowBarKey"
        var isOfflineKey="isOffline"
        fun startActivity(context: Context, url: String, title: String) {
            val intent = Intent(context, H5WebActivity::class.java)
            intent.putExtra(urlKey, url)
            intent.putExtra(titleKey, title)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity(intent)
        }

        fun startActivity(context: Context, url: String, title: String, isShowBar: Boolean) {
            val intent = Intent(context, H5WebActivity::class.java)
            intent.putExtra(urlKey, url)
            intent.putExtra(titleKey, title)
            intent.putExtra(isShowBarKey, isShowBar)
            context.startActivity(intent)
        }

        fun startActivity(context: Context, url: String, title: String, isShowBar: Boolean, isOffline:Boolean) {
            val intent = Intent(context, H5WebActivity::class.java)
            intent.putExtra(urlKey, url)
            intent.putExtra(titleKey, title)
            intent.putExtra(isShowBarKey, isShowBar)
            intent.putExtra(isOfflineKey, isOffline)
            context.startActivity(intent)
        }


    }


    override fun initView(savedInstanceState: Bundle?): Int {
       return R.layout.activity_h5_web1
    }

    override fun initData() {
        findViewById<TextView>(R.id.textTitle).text = intent.getStringExtra(titleKey).toString()
//        textTitle.setText("系统设置")
        initWebView()
        button_me.setOnClickListener {
            ARouter.getInstance().build("/user/UserActivity").navigation()
        }
    }


    fun initWebView(){
        url = intent.getStringExtra(urlKey).toString()
//        Log.d("h5", "onCreate: 传送地址$url")
//        if (!url.startsWith("http")) {
//            ToastUtil.show(this, "访问链接错误!" + url)
//        }
//

//        url="http://127.0.0.1:16888/mt-imall/"
//        url="http://127.0.0.1:8080"
//        url="file:///android_asset/index1.html"
//        url="https://www.maoti.com/"
        title = intent.getStringExtra(titleKey).toString()
//        isShowBar = intent.getBooleanExtra(H5WebActivity.isShowBarKey, false)

//        if (!isShowBar) {
//            titleBar.visibility = View.GONE
//            fengexian_view.visibility = View.GONE
//        }
        h5webView.clearCache(true)
//        h5JavascriptInterface = H5JavascriptInterface(h5webView, this, this)
//        h5webView.addJavascriptInterface(h5JavascriptInterface!!, "H5JavascriptInterface")
//        h5webView.addJavascriptInterface(H5GrandPrixInterface(this), "Android")
//        h5webView.addJavascriptInterface(H5InjectedObjectInterface(h5webView, this), "injectedObject")

        h5webView.settings.javaScriptEnabled = true
        h5webView.settings.javaScriptCanOpenWindowsAutomatically = true
        h5webView.settings.allowFileAccess = true
        h5webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS;
        h5webView.settings.setSupportZoom(true)
        h5webView.settings.builtInZoomControls = false
        h5webView.settings.useWideViewPort = true
        h5webView.settings.setSupportMultipleWindows(true)
        h5webView.settings.setAppCacheEnabled(true)
        h5webView.settings.domStorageEnabled = true
        h5webView.settings.setGeolocationEnabled(true)
        h5webView.settings.setAppCacheMaxSize(Long.MAX_VALUE);
        h5webView.settings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        h5webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE;
        h5webView.isHorizontalScrollBarEnabled = false //水平不显示
        h5webView.isVerticalScrollBarEnabled = false //垂直不显示

//        var ua=h5webView.settings.userAgentString +" maoti/"+ Utils.getBaseAppVersionName()
//        h5webView.settings.userAgentString=ua

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        h5webView.loadUrl(url)
    }

}