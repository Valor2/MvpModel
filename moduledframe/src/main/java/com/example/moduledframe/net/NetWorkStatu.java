package com.example.moduledframe.net;

import java.io.Serializable;

public class NetWorkStatu implements Serializable {

    private int statu;//0-网络已连接  1-网络断开

    public NetWorkStatu(int statu){
        setStatu(statu);
    }

    public int getStatu() {
        return statu;
    }

    public void setStatu(int statu) {
        this.statu = statu;
    }

    public boolean isNetWorkConn(){
        if(statu == 0){
            return true;
        }
        return false;
    }
}
