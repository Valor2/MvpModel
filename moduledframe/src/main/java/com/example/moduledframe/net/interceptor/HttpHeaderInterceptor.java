package com.example.moduledframe.net.interceptor;




import com.example.moduledframe.utils.Utils;
import com.example.moduledframe.utils.spfkey.SPFKey;
import com.example.moduledframe.utils.spfkey.SPfUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by csy on 2018/3/21.
 */

public class HttpHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //  配置请求头
        String token = SPfUtil.getInstance().getString(SPFKey.TOKEN);
//        String mIMEI = Utils.getIMEI();
//        Log.d("token and imei",token +"  "+mIMEI);
//        String origin_id="0";
//        if(BaseApplication.isLite()){
//            origin_id="1";
//        }
        Request request = chain.request().newBuilder()
                .addHeader("Authorization", token)
//                .addHeader("version",Utils.getBaseAppVersionName())//获取版本号 1.0.7
//                .addHeader("version-name",  Utils.getAppVersionCode(BaseApplication.getContext()))//获取版本号  5
                .addHeader("deviceId", SPfUtil.getInstance().getString(SPFKey.DeviceId))
                .addHeader("version","1.1")
                .addHeader("userPlatform","android")
//                .addHeader("Device-Model", ROMTools.getName())
                .addHeader("Content-Type", "application/json;charset=utf-8")
                .addHeader("Accept","application/json;charset=utf-8")
                .addHeader("Accept-Encoding", "identity")
//                .addHeader("Drives", Utils.getUserAgent())
//                .addHeader("Origin-Id",origin_id)
//                .addHeader("Channel-Name", Utils.getMetaData(BaseApplication.getContext(),"UMENG_CHANNEL"))
                .build();

        return chain.proceed(request);
    }
}
