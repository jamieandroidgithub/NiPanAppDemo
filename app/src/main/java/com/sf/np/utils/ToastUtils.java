package com.sf.np.utils;

import android.content.Context;
import android.widget.Toast;
import com.sf.np.NPApplication;

/**
 * 自定义单例toast 防止过个显示
 * Created by Jamie on 2015/9/2.
 */
public class ToastUtils extends Toast{

    private static Toast mToast;

    private ToastUtils(Context context) {
        super(context);
    }

    /**
     *  以下是系统 toast
     * @param context
     * @param duration
     */
    public static Toast makeText(Context context, CharSequence text,int duration) {
        if (mToast == null) {
            if (context == null) {
                mToast = Toast.makeText(NPApplication.getInstance(), "", duration);
            } else {
                mToast = Toast.makeText(context, "", duration);
            }
        }
        mToast.setText(text);
        mToast.setDuration(duration);
        return mToast;

    }

    public static Toast makeText(Context context, int resId, int duration) {
        if (mToast == null) {
            if (context == null) {
                mToast = Toast.makeText(NPApplication.getInstance(), "", duration);
            } else {
                mToast = Toast.makeText(context, "", duration);
            }
        }
        mToast.setText(context.getString(resId));
        mToast.setDuration(duration);
        return mToast;

    }

    public static void makeShortText(CharSequence text) {
        if (mToast == null) {
            mToast = Toast.makeText(NPApplication.getInstance(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();

    }

    public static void makeShortText(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(NPApplication.getInstance(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(NPApplication.getInstance().getString(resId));
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void makeLongText(CharSequence text) {
        if (mToast == null) {
            mToast = Toast.makeText(NPApplication.getInstance(), "", Toast.LENGTH_LONG);
        }
        mToast.setText(text);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();

    }

    public static void makeLongText(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(NPApplication.getInstance(), "", Toast.LENGTH_LONG);
        }
        mToast.setText(NPApplication.getInstance().getString(resId));
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }


   /* *//**
     *  以下是自定义 toast
     * @param context
     * @param resId
     * @param duration
     *//*
    private static void show(Context context, int resId, int duration) {
        if(mToast == null){
            mToast=new Toast(context);
        }
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast_textview, null);
        mToast.setView(toastRoot);
        TextView tv=(TextView)toastRoot.findViewById(R.id.text_toast);
        tv.setText(context.getString(resId));
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(duration);
        mToast.show();
    }

    private static void show(Context context, String message, int duration) {
        if(mToast == null){
            mToast=new Toast(context);
        }
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast_textview, null);
        mToast.setView(toastRoot);
        TextView tv=(TextView)toastRoot.findViewById(R.id.text_toast);
        tv.setText(message);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(duration);
        mToast.show();
    }

    public static void showShort(int resId) {
        if(mToast == null){
            mToast=new Toast(MiAppalication.getInstance());
        }
        View toastRoot = LayoutInflater.from(MiAppalication.getInstance()).inflate(R.layout.toast_textview, null);
        mToast.setView(toastRoot);
        TextView tv=(TextView)toastRoot.findViewById(R.id.text_toast);
        tv.setText(MiAppalication.getInstance().getString(resId));
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void showShort(String message) {
        if(mToast == null){
            mToast=new Toast(MiAppalication.getInstance());
        }
        View toastRoot = LayoutInflater.from(MiAppalication.getInstance()).inflate(R.layout.toast_textview, null);
        mToast.setView(toastRoot);
        TextView tv=(TextView)toastRoot.findViewById(R.id.text_toast);
        tv.setText(message);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void showLong(int resId) {
        if(mToast == null){
            mToast=new Toast(MiAppalication.getInstance());
        }
        View toastRoot = LayoutInflater.from(MiAppalication.getInstance()).inflate(R.layout.toast_textview, null);
        mToast.setView(toastRoot);
        TextView tv=(TextView)toastRoot.findViewById(R.id.text_toast);
        tv.setText(MiAppalication.getInstance().getString(resId));
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }

    public static void showLong(String message) {
        if(mToast == null){
            mToast=new Toast(MiAppalication.getInstance());
        }
        View toastRoot = LayoutInflater.from(MiAppalication.getInstance()).inflate(R.layout.toast_textview, null);
        mToast.setView(toastRoot);
        TextView tv=(TextView)toastRoot.findViewById(R.id.text_toast);
        tv.setText(message);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }*/
}
