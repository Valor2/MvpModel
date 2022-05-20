package com.example.moduledframe.net;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.moduledframe.utils.spfkey.SPFKey;

public class ResponseResult<T> implements Parcelable {
    private int code;
    private String message;
    private T data;

    protected ResponseResult(Parcel in) {
        code = in.readInt();
        message = in.readString();
    }

    public static final Creator<ResponseResult> CREATOR = new Creator<ResponseResult>() {
        @Override
        public ResponseResult createFromParcel(Parcel in) {
            return new ResponseResult(in);
        }

        @Override
        public ResponseResult[] newArray(int size) {
            return new ResponseResult[size];
        }
    };

    public boolean isSuccess(){
        return code == SPFKey.isSuccess;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(message);
    }
}
