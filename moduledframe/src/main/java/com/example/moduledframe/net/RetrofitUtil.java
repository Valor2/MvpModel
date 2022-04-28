
package com.example.moduledframe.net;



import com.example.moduledframe.BaseManager;
import com.example.moduledframe.net.interceptor.HttpCacheInterceptor;
import com.example.moduledframe.net.interceptor.HttpHeaderInterceptor;
import com.example.moduledframe.net.interceptor.LoggingInterceptor;
import com.example.moduledframe.net.interceptor.converter.MyConverterFactory;
import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


public class RetrofitUtil {

    private  static  String httpUrl ="";
    public static RequestBody toJsonBody(Object body) {
        return getJsonBody(new Gson().toJson(body));
    }

    public static RequestBody getJsonBody(String json) {
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        return requestBody;
    }


    /**
     * 无网: 读取缓存
     * 有网: 不读取缓存,正常请求
     * 场景: 无特殊情况,默认用这个
     */
    private static Retrofit retrofit;

    public static Retrofit getGsonRetrofit(String url) {
        if (retrofit == null) {
            retrofit = baseRetrofit(url).build();
        }
        return retrofit;
    }

    /**
     * 无网: 不读取缓存
     * 有网: 不读取缓存
     * 场景: 登录信息\用户操作\敏感信息就用这个
     */
    private static Retrofit retrofitNoCache;

    public static Retrofit getGsonRetrofitNoCache(String url) {
        if (retrofitNoCache == null) {
            retrofitNoCache = baseRetrofitNoCache(url).build();
        }
        return retrofitNoCache;
    }

    /**
     * 无网: 读取缓存
     * 有网: 在有效时间内读取缓存
     * 场景: 不经常更新内容的时候可以用.
     * 意义: 减少用户访问服务器,降低压力.
     */
    private static Retrofit retrofitCache;

    public static Retrofit getGsonRetrofitCache(String url) {
        if (retrofitCache == null) {
            retrofitCache = baseRetrofitCache(url).build();
        }
        return retrofitCache;
    }


    public static Retrofit.Builder baseRetrofit(String url) {
        return new Retrofit.Builder()
                .client(getOKhttpBuilder().build())
                .baseUrl(url)
                .addConverterFactory(MyConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    }

    /**
     * 无网的时候,读取缓存,有网的时候正常请求网络
     */
    private static OkHttpClient.Builder getOKhttpBuilder() {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(NetConstant.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(NetConstant.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpHeaderInterceptor())//顺序要对,先添加header拦截.
                .addInterceptor(new LoggingInterceptor())
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .cache(getCache());

//        if (AppSiteInfoUtils.getSiteInfoBean().getIs_capture()) {
//            okhttpClientBuilder.sslSocketFactory(Objects.requireNonNull(SSLSocketFactoryUtils.getSSLSocketFactory()),
//                    SSLSocketFactoryUtils.createTrustManager())
//                    .hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier());
//        }

        return okhttpClientBuilder;
    }


    public static Retrofit.Builder baseRetrofitCache(String url) {
        return new Retrofit.Builder()
                .client(getOKhttpBuilderCache().build())
                .baseUrl(url)
                .addConverterFactory(MyConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    /**
     * 有网的时候读取缓存,有效期为5分钟,
     * 期间不对服务器做请求,降低流量消耗和服务器压力
     */
    private static OkHttpClient.Builder getOKhttpBuilderCache() {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(NetConstant.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(NetConstant.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpHeaderInterceptor())//顺序要对,先添加header拦截.
                .addInterceptor(new LoggingInterceptor())
                .addNetworkInterceptor(new HttpCacheInterceptor(60 * 5))//一分钟
                .cache(getCache());

//        if (AppSiteInfoUtils.getSiteInfoBean().getIs_capture()) {
//            okhttpClientBuilder.sslSocketFactory(Objects.requireNonNull(SSLSocketFactoryUtils.getSSLSocketFactory()),
//                    SSLSocketFactoryUtils.createTrustManager())
//                    .hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier());
//        }

        return okhttpClientBuilder;
    }

    public static Retrofit.Builder baseRetrofitNoCache(String url) {
        return new Retrofit.Builder()
                .client(getOKhttpBuilderNoCache().build())
                .baseUrl(url)
                .addConverterFactory(MyConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    private static OkHttpClient.Builder getOKhttpBuilderNoCache() {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(NetConstant.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(NetConstant.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpHeaderInterceptor())//顺序要对,先添加header拦截.
                .addInterceptor(new LoggingInterceptor(true));

//        if (AppSiteInfoUtils.getSiteInfoBean().getIs_capture()) {
//            okhttpClientBuilder.sslSocketFactory(Objects.requireNonNull(SSLSocketFactoryUtils.getSSLSocketFactory()),
//                    SSLSocketFactoryUtils.createTrustManager())
//                    .hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier());
//        }
        return okhttpClientBuilder;
    }

    private static Cache getCache() {
        File cacheFile = new File(BaseManager.getInstance().getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        return cache;
    }

    /**
     * okhttp3,h5网络请求需要
     */
    private static OkHttpClient okHttpClient;
    private static OkHttpClient.Builder okhttpBuilder;
    public static OkHttpClient getOkHttpH5Instance() {
        if (okHttpClient == null) {
            if (okHttpClient == null) {
                okhttpBuilder = new OkHttpClient.Builder();
                okhttpBuilder.connectTimeout(NetConstant.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)//连接超时
                        .writeTimeout(NetConstant.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)//写入超时
                        .readTimeout(NetConstant.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)//读取超时
                        .addInterceptor(new HttpHeaderInterceptor())//顺序要对,先添加header拦截.
                        .addInterceptor(new LoggingInterceptor(true));

//                if (AppSiteInfoUtils.getSiteInfoBean().getIs_capture()) {
//                    okhttpBuilder.sslSocketFactory(Objects.requireNonNull(SSLSocketFactoryUtils.getSSLSocketFactory()),
//                            SSLSocketFactoryUtils.createTrustManager())
//                            .hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier());
//                }
                okHttpClient = okhttpBuilder.build();
            }
        }

        return okHttpClient;
    }

}
