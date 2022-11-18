package com.example.textmodule.net;

import com.example.moduledframe.net.RetrofitUtil;
import com.example.moduledframe.net.interceptor.DefaultObserver;
import com.example.moduledframe.net.interceptor.converter.MyConverterFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CourseRetrofit extends RetrofitUtil {
    private static String BASE_URL="https://uat-jk.jlflove.com/flove/";

    private static String BASE_URL2="https://uat-jk.jlflove.com/flove1/";

    public static void getCourse(DefaultObserver<List<Object>> observer, String json) {
        Map<String, Object> map = new HashMap<>();
        map.put("channel_id", "");
        map.put("page", 1);
        map.put("page_size", "20");
        getGsonRetrofitNoCache(BASE_URL)
                .create(ApiCourseService.class)
                .getTestList(toJsonBody(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    public static void getCourse2(DefaultObserver<List<Object>> observer, String json) {
        Map<String, Object> map = new HashMap<>();
        map.put("channel_id", "");
        map.put("page", 1);
        map.put("page_size", "20");
        getGsonRetrofitNoCache(BASE_URL2, MyConverterFactory.create())
                .create(ApiCourseService.class)
                .getTestList(toJsonBody(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
