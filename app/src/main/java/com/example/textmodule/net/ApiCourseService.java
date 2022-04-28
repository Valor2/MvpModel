package com.example.textmodule.net;

import com.example.moduledframe.net.ResponseResult;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiCourseService {

    /**
     *
     */
    @POST("user/login")
     Observable<ResponseResult<List<Object>>> getTestList(@Body RequestBody requestBody);

}
