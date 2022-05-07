package com.example.moduledframe.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.moduledframe.R;

public class ImageLoadUtils {

    /**
     * 加载圆形图片
     * @param context
     * @param imgPath
     * @param imgage
     */
    public static void ImageCircleLoad(Context context, String imgPath, ImageView imgage){
        Glide.with(context)
                .load(imgPath)
                .error(R.mipmap.load_error_img).fallback(R.mipmap.load_error_img)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imgage);

    }

    /**
     * 加载圆形图片
     * @param context
     * @param imgPath
     * @param imgage
     */
    public static void ImageCircleLoad(Context context, String imgPath, ImageView imgage,int errorImg,int notimg){
        Glide.with(context)
                .load(imgPath)
                .error(errorImg).fallback(notimg)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imgage);

    }

    /**
     * 加载图片 可设置圆角
     * @param context
     * @param imgPath
     * @param imgage
     * @param rounded
     */
    public static void ImageLoad(Context context, String imgPath, ImageView imgage,int rounded){
        Glide.with(context)
                .load(imgPath)
                .error(R.mipmap.load_error_img).fallback(R.mipmap.load_error_img)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(rounded)))
                .into(imgage);

    }

    /**
     * 加载图片 可设置圆角
     * @param context
     * @param imgPath
     * @param imgage
     * @param rounded
     */
    public static void ImageLoad(Context context, String imgPath, ImageView imgage,int rounded,int errorImg,int notimg){
        Glide.with(context)
                .load(imgPath)
                .error(errorImg).fallback(notimg)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(rounded)))
                .into(imgage);

    }
}
