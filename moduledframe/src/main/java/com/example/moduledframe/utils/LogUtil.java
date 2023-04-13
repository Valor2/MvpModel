package com.example.moduledframe.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.moduledframe.net.NetConstant;


/**
 * 提供统一的log打印工具类
 * @author wsl
 */
public class LogUtil {

	 public static boolean  isDebug = false;
	
	/** 五种Log日志类型 */
	
	/** 调试日志类型 */
	public static final int DEBUG = 111;
	/** 错误日志类型 */
	public static final int ERROR = 112;
	/** 信息日志类型 */
	public static final int INFO = 113;
	/** 详细信息日志类型 */
	public static final int VERBOSE = 114;
	/** 警告日志类型 */
	public static final int WARN = 115;

	public static void i(Class cl, String message){
		if(TextUtils.isEmpty(message)){
			return;
		}
		if(isDebug){
			Log.i(cl.getSimpleName(), message);
		}
	}

	public static void v(Class cl,String message){
		if(TextUtils.isEmpty(message)){
			return;
		}
		if(isDebug){
			Log.v(cl.getSimpleName(), message);
		}
	}

	public static void d(Class cl,String message){
		if(TextUtils.isEmpty(message)){
			return;
		}
		if(isDebug){
			Log.d(cl.getSimpleName(), message);
		}
	}

	public static void e(Class cl,String message){
		if(TextUtils.isEmpty(message)){
			return;
		}
		if(isDebug){
			Log.e(cl.getSimpleName(), message);
		}
	}

	public static void d(String Tag, String Message) {
		if(TextUtils.isEmpty(Message)){
			return;
		}
		if (isDebug) {
			Log.d(Tag, Message);
		}
	}

	public static void v(String Tag, String Message) {
		if(TextUtils.isEmpty(Message)){
			return;
		}
		if (isDebug) {
			Log.v(Tag, Message);
		}
	}


	public static void i(String Tag, String format, Object... args) {

		if(TextUtils.isEmpty(format)){
			return;
		}

		if (isDebug) {
			Log.i(Tag, String.format(format, args));
		}
	}

	public static void i(String Tag, String Message) {
		if(TextUtils.isEmpty(Message)){
			return;
		}
		if (isDebug) {
			Log.i(Tag, Message);
		}
	}

	public static void i(Context context, String message){
		if(TextUtils.isEmpty(message)){
			return;
		}
		if(isDebug){
			Log.i(context.getClass().getSimpleName(), message);
		}
	}

	public static void i(Object obj, String message){
		if(TextUtils.isEmpty(message)){
			return;
		}
		if(isDebug){
			Log.i(obj.getClass().getSimpleName(), message);
		}
	}

	public static void i(String Tag, String Message, int Style) {
		if(TextUtils.isEmpty(Message)){
			return;
		}
		if (isDebug) {
			switch (Style) {
			case DEBUG: {
				Log.d(Tag, Message);
			}
				break;
			case ERROR: {
				Log.e(Tag, Message);
			}
				break;
			case INFO: {
				Log.i(Tag, Message);
			}
				break;
			case VERBOSE: {
				Log.v(Tag, Message);
			}
				break;
			case WARN: {
				Log.w(Tag, Message);
			}
				break;
			default:
				Log.i(Tag, Message);
				break;
			}
		}
	}


}
