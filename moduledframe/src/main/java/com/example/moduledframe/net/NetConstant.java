package com.example.moduledframe.net;



public interface NetConstant {
    boolean LOGON = true;
    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 1000*30;

    /**
     * 打印网络数据的tag
     */
    String logTag="net";

    String Secret = "Dds#SDafu23!4R5SDFS8*2";//网站秘钥

    int SUCCESS = 0; // 访问成功
    int NO_DATA_MSG = 1; //  失败


    int COMMENT_ERROR = 110;//"服务器繁忙，稍后再试！"; //
    int ARGS_MISS_ERROR =111; //  "参数缺失！";//111
    int ARGS_ILLEGAL_ERROR = 112; // "参数非法！";//112
    int UPLOAD_FAIL_ERROR =113; //  "文件上传失败！";//113
    int FETCH_OSS_STS_ERROR =114; //  "获取sts授权失败！";//114
    int MISSING_WEB_TOKEN_ERROR = 115; // "缺失参数webToken！";//115
    int VERIFY_SIGN_FAIL_ERROR =116; //  "验证签名失败！";//116
    int NETWORK_REQUEST_TIMEOUT_ERROR = 117; // "网络请求超时！";//117
    int NO_EXPIRED = 401;//账号过期

    int USER_LOGOUT = 124;//用户已注销
    int FORCED_TO_LOGOFF_ERROR = 119; //此账号已在其它设备登录！
    int USER_LOGIN_OUT_OK_ERROR  =20020;//=>'此账号已经注销、请重新注册！',//20020
    int CHECHKED_THIRD_ID  =132;//=>'此用户账号未注册！',//20020
    int  USER_PHONE = 20024; //该用户未绑定手机，会跳入绑定手机界面


    int FOLLOW_ALL = 1200001;//拉黑失败code

    int Exception = 99999;//系统错误
    int PARSE_ERROR = 99998;//解析数据失败
    int BAD_NETWORK = 99997;//网络问题
    int CONNECT_ERROR = 99996;//连接错误
    int CONNECT_TIMEOUT = 99995;//连接超时
    int UNKNOWN_ERROR = 99994;//未知错误
}
