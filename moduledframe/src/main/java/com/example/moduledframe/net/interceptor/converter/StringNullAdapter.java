package com.example.moduledframe.net.interceptor.converter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class StringNullAdapter extends TypeAdapter<String> {

    /**
     * Created by User on 2018/4/13.
     * 自定义一个String适配器
     */
    @Override
    public void write(JsonWriter jsonWriter, String s) throws IOException {
        if (s == null) {//序列化使用的是adapter的write方法
            //jsonWriter.nullValue();//这个方法是错的，而是应该将null转成""
            jsonWriter.value("");
            return;
        }
        jsonWriter.value(s);
    }

    @Override
    public String read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {//反序列化使用的是read方法
            jsonReader.nextNull();
            return "";
        }
        return jsonReader.nextString();
    }
}
