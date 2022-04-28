package com.example.moduledframe.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;


import com.example.moduledframe.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by kenny on 2020/5/23.
 */
public abstract class BaseDialogFragment extends DialogFragment {

    private static final String TAG = "BaseDialogFragment";

    public final String TOP = "TOP";
    public final String BOTTM = "BOTTM";
    public final String CENTER = "CENTER";
    public final String RIGHT = "RIGHT";

    private View main;

    /**
     * 数据中转
     */
    protected Object tag;
    private Map<String, Object> mapTags = new HashMap<>();

    public void setData(Object tag) {
        this.tag = tag;
    }

    public void setData(String key, Object tag) {
        this.mapTags.put(key, tag);
    }

    public Object getData() {
        return tag;
    }

    public Object getData(String key) {
        return this.mapTags.get(key);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        int viewId = getContentViewResId();
        if (viewId > 0) {
            View view = inflater.inflate(viewId, container, false);
            main=view;
            return initData(view);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getCloseBtnId() > 0) {
            View view = getDialog().findViewById(getCloseBtnId());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Objects.requireNonNull(getDialog()).dismiss();
                }
            });
        }

        Window dialogWindow = getDialog().getWindow();
        if (dialogWindow != null) {
            dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
            dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            if (TOP.equals(getLocation())) {
                lp.gravity = Gravity.TOP;
            } else if (CENTER.equals(getLocation())) {
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
            } else if (RIGHT.equals(getLocation())) {
                int width = WindowManager.LayoutParams.WRAP_CONTENT;
                WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                if (windowManager != null) {
                    DisplayMetrics outMetrics = new DisplayMetrics();
                    windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
                    width = outMetrics.widthPixels - Utils.dipToPx(getContext(), 50);
                }
                lp.width = width;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                lp.gravity = Gravity.BOTTOM | Gravity.RIGHT;
            } else {
                lp.gravity = Gravity.BOTTOM;
            }
            lp.windowAnimations = android.R.style.Animation_InputMethod;
            dialogWindow.setAttributes(lp);
            resize();
        }
    }

    public void show(FragmentActivity activity){
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()){
            activity.getSupportFragmentManager().beginTransaction().add(this, this.getClass().getSimpleName()).commitAllowingStateLoss();
        }
    }

    public void resize() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

//    public void getInstanceState(Bundle savedInstanceState){}

    public abstract View initData(View view);

    public abstract int getContentViewResId();

    public abstract int getCloseBtnId();

    public abstract String getLocation();



}
