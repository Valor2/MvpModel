//package com.example.moduleh5.h5.script
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.content.Intent
//import android.graphics.Color
//import android.util.Log
//import android.webkit.JavascriptInterface
//import android.webkit.WebView
//import com.example.moduledframe.utils.ToastUtil
//import com.example.moduleh5.h5.imp.FastHttpCallBack
//import com.example.moduleh5.h5.imp.ImageCallBack
//import com.google.gson.Gson
//
//import org.greenrobot.eventbus.EventBus
//import org.greenrobot.eventbus.Subscribe
//import org.greenrobot.eventbus.ThreadMode
//import org.json.JSONObject
//
//class H5JavascriptInterface(private val webView: WebView, private val context: Context, private val viewCallback: H5Contract.View) {
//    val TAG = "h5_net"
//    private val h5PostNet = H5PostNetUtil()
//    private val h5Image = H5ImageUtil(webView.context)
//    private val h5GetNet = H5GetNetUtil()
//    private var loginCallBack = ""
//
//    init {
//        if (!EventBus.getDefault().isRegistered(this)) {
//            EventBus.getDefault().register(this)
//        }
//    }
//
//
//    /**
//     * h5关闭Activity
//     */
//    @JavascriptInterface
//    fun goback() {
//        viewCallback.finishPage()
//    }
//
//    /**
//     * h5关闭Activity
//     */
//    @JavascriptInterface
//    fun openWeChat() {
//        (webView.context as androidx.appcompat.app.AppCompatActivity).runOnUiThread {
//            var lan =  webView.context.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
//            var intent = Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_LAUNCHER);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setComponent(lan?.getComponent());
//            webView.context.startActivity(intent);
//        }
//    }
//
//
//    /**
//     * 吐司
//     */
//    @JavascriptInterface
//    fun toastMsg(data: String) {
//        ToastUtil.showShort(data)
//    }
//
//    /**
//     * 吐司,1秒
//     */
//    @JavascriptInterface
//    fun toastMsgLong(data: String) {
//        ToastUtil.showLong(data)
//    }
//
//
//    /**
//     * 小程序可以通过该方法获取一切网络文件,包括静态图片\动态图片\视频等文件.通常用于哪些基本不更新的文件.如：文章列表图片，头像图片
//     * 当第二次调用的时候,原生直接返回缓存文件
//     * imgUrl 图片地址(全路径)
//     * callBackMethod 回调方法名
//     */
//    @JavascriptInterface
//    fun getFile(data: String) {
//        Log.d(TAG, "getImage: " + data)
//        //https://static.maoti.com/app/admin/2021/01/19/259908345D22228140593.jpg
//        h5Image.downFile("https://static.maoti.com/app/admin/2021/01/19/259908345D22228140593.jpg", "getImage", true, object : ImageCallBack {
//            override fun imagePath(method: String, path: String) {
//                (webView.context as androidx.appcompat.app.AppCompatActivity).runOnUiThread {
//                    webView.evaluateJavascript("javascript:$method('$path')", null)
//                }
//            }
//        })
//
//    }
//
//
//
//    /**
//     * api接口网络请求
//     * {
//    "url":"xxxx.com"  //请求路径 (全路径)
//    "method":"post"   //请求方式(post,get,等等),如果是get,h5自己拼接参数
//    "callBackMethod":"login" //h5回调方法名
//
//    //请求参数,以jsonobject对象形式
//    "params":{
//    "xxx":"xxx"
//    "ddd":"ddd"
//    }
//    }
//     */
//    @JavascriptInterface
//    fun apiCall(data: String) {
//        Log.d(TAG, "apiCall: " + data)
//        try {
//            val jsonObject = JSONObject(data)
//            val url = jsonObject.getString("url")
//            val callBackMethod = jsonObject.getString("callBackMethod")
//            var params: String = "params"
//            if (jsonObject.has(params)) {
//                params = jsonObject.getJSONObject("params").toString()
//            }
//            if (jsonObject.getString("method") == "post") {
//                h5PostNet.commonPost(h5PostNet.getRequestForPostJson(url, params, callBackMethod), callBackMethod, object :
//                    FastHttpCallBack {
//                    @SuppressLint("NewApi")
//                    override fun callBackMethod(method: String, data: String) {
//                        (webView.context as androidx.appcompat.app.AppCompatActivity).runOnUiThread {
//                            webView.evaluateJavascript("javascript:$method('$data')", null)
//                        }
//                    }
//                })
//            } else if (jsonObject.getString("method") == "get") {
//                h5GetNet.commonGet(h5GetNet.getRequestForGet(url, callBackMethod), callBackMethod, object : FastHttpCallBack {
//                    @SuppressLint("NewApi")
//                    override fun callBackMethod(method: String, data: String) {
//                        (webView.context as androidx.appcompat.app.AppCompatActivity).runOnUiThread {
//                            webView.evaluateJavascript("javascript:$method('$data')", null)
//                        }
//                    }
//
//                })
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    /**
//     * 初始化配置的方法
//     */
//    @JavascriptInterface
//    fun loadNativeSetting(callBackData: String) {
//        val jsonObject = JSONObject()
//        jsonObject.put("Device-Model", ROMTools.getName())
//        jsonObject.put("Version", Utils.getBaseAppVersionName())
//        jsonObject.put("Platform", "android")
//        jsonObject.put("Platform-Version", android.os.Build.VERSION.SDK_INT)
//        jsonObject.put("DeviceName", ROMTools.getName())
//        jsonObject.put("Origin-Id", 0)
//        jsonObject.put("IsLogined", SPfUtil.getInstance().getBoolean(SPFKey.IsSingIN))
//        jsonObject.put("Channel-Name", Utils.getMetaData(BaseManager.getInstance().getContext(), "UMENG_CHANNEL"))
//        jsonObject.put("Token", SPfUtil.getInstance().getString(SPFKey.TOKEN))
//        jsonObject.put("userId", SPfUtil.getInstance().getInt(SPFKey.USER_ID))
//
//        try{
//            jsonObject.put("Statusbar-Height", QMUIDisplayHelper.pxToDp(UltimateBarX.getStatusBarHeight().toFloat()))
//        }catch (e: Exception){
//            e.printStackTrace()
//            jsonObject.put("Statusbar-Height", 30)
//        }
//
//        if (SPfUtil.getInstance().getBoolean(SPFKey.WhiteAndheaven, false)) {
//            jsonObject.put("ThemeStyle", 1)
//        } else {
//            jsonObject.put("ThemeStyle", 0)
//        }
//
//        var data = jsonObject.toString()
//
//        var callBackJSONObject=JSONObject(callBackData)
//        var callBackMethod=callBackJSONObject.getString("callBackMethod")
//
//        (webView.context as androidx.appcompat.app.AppCompatActivity).runOnUiThread {
//            webView.evaluateJavascript("javascript:"+callBackMethod+"('$data')", null)
//        }
//
//    }
//
//
//    /**
//     *
//     *跳转android的界面
//     * appId是包名
//     * className跳转目录全称
//     * params传递的参数
//     */
//
//    @JavascriptInterface
//    fun openAndroidPage(data: String) {
//       IntentTools.startIntent(data)
//    }
//
//    /**
//     * 登录接口. 如果已经登录成功, 直接返回数据
//     */
//    @JavascriptInterface
//    fun doLogin(data: String) {
//        Log.d("H5", "doLogin: H5调用了登录")
//        val h5LoginBean = Gson().fromJson<H5LoginBean>(data, H5LoginBean::class.java)
//        loginCallBack = h5LoginBean.callBackMethod
//        viewCallback.reLoadStyle(h5LoginBean.reload)
//        if(!BaseManager.getInstance().isSingIN()){
//            EventBus.getDefault().post(h5LoginBean)
//        }else{
//            (webView.context as androidx.appcompat.app.AppCompatActivity).runOnUiThread {
//                val jsonObject = JSONObject()
//                jsonObject.put("login",true)
//                jsonObject.put("token", SPfUtil.getInstance().getString(SPFKey.TOKEN))
//                jsonObject.put("userId", SPfUtil.getInstance().getInt(SPFKey.USER_ID))
//                webView.evaluateJavascript("javascript:" + loginCallBack + "(" + jsonObject.toString() + ")", null)
//            }
//        }
//    }
//
//
//    @JavascriptInterface
//    fun openGameDetailPage(id:Int,tabkey:String) {
//        var jsonObject=JSONObject()
//        jsonObject.put("appId","com.sowell.maoti")
//        jsonObject.put("className","com.sowell.maoti.ui.ballgame.BallGameDetailsActivity")
//        var jsonObjectP=JSONObject()
//        jsonObject.put("params",jsonObjectP)
//
//        jsonObjectP.put("KEY_GAME_ID",id)
//        jsonObjectP.put("KEY_TABPOSITION",tabkey)
//
//        var intent=jsonObject.toString()
//        IntentTools.startIntent(intent)
//        Log.d("H5", "跳转比赛详情")
//    }
//
//
//    @JavascriptInterface
//    fun share(data: String) {
//        Log.d("H5", "doLogin: H5调用了分享" + data)
//        val h5ShareBean = Gson().fromJson<H5ShareBean>(data, H5ShareBean::class.java)
//        EventBus.getDefault().post(h5ShareBean)
//    }
//
//    @JavascriptInterface
//    fun resetBGColor(data: String) {
//        Log.d("H5", "resetBGColo设置颜色" + data)
//        try {
//            webView.setBackgroundColor(Color.parseColor(data))
//        } catch (e: Exception) {
//            webView.setBackgroundColor(Color.parseColor("#FFFFFF"))
//        }
//
//    }
//
//    @JavascriptInterface
//    fun showNavBar(isShow: Boolean) {
//        if (isShow) {
//            viewCallback.showBar()
//        } else {
//            viewCallback.hideBar()
//        }
//    }
//
//    @JavascriptInterface
//    fun refreshState(state: Boolean) {
//        viewCallback.refreshState(state)
//    }
//
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onEventBusCom(event: EventEntity) {
//        if (event.getCode() == EventBusKey.login || event.getCode() == EventBusKey.logout) {
//            (webView.context as androidx.appcompat.app.AppCompatActivity).runOnUiThread {
//                val jsonObject = JSONObject()
//                jsonObject.put("login",event.getCode() == EventBusKey.login)
//                jsonObject.put("token", SPfUtil.getInstance().getString(SPFKey.TOKEN))
//                jsonObject.put("userId", SPfUtil.getInstance().getInt(SPFKey.USER_ID))
//                webView.evaluateJavascript("javascript:" + loginCallBack + "(" + jsonObject.toString() + ")", null)
//            }
//        }
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onEventBusCom(event: H5LoginCallBackBean) {
//        (webView.context as androidx.appcompat.app.AppCompatActivity).runOnUiThread {
//            if(!BaseManager.getInstance().isSingIN()){
//                val jsonObject = JSONObject()
//                jsonObject.put("login",false)
//                jsonObject.put("token", SPfUtil.getInstance().getString(SPFKey.TOKEN))
//                jsonObject.put("userId", SPfUtil.getInstance().getInt(SPFKey.USER_ID))
//                webView.evaluateJavascript("javascript:" + loginCallBack + "(" + jsonObject.toString() + ")", null)
//            }
//        }
//    }
//
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onEvent(event: H5ShareCallBackBean) {
//        (webView.context as androidx.appcompat.app.AppCompatActivity).runOnUiThread {
//            webView.evaluateJavascript("javascript:" + event.callBackMethod + "(" + event.isOk + ")", null)
//        }
//    }
//
//
//
//    fun onDestroy() {
//        h5Image.onDestroy()
//        h5PostNet.onDestroy()
//        (webView.context as androidx.appcompat.app.AppCompatActivity).runOnUiThread {
////            webView.evaluateJavascript("javascript:onDestroy()", null)
//        }
//        try {
//            if (EventBus.getDefault().isRegistered(this)) {
//                EventBus.getDefault().unregister(this)
//            }
//        } catch (e: java.lang.Exception) {
//            e.printStackTrace()
//        }
//    }
//}