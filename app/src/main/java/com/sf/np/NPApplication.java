package com.sf.np;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

/**
 * Created by 887938 on 2015/9/28.
 */
public class NPApplication extends Application {
    private static String TAG = NPApplication.class.getSimpleName();


    static {
        System.loadLibrary("iconv");
    }

    private static NPApplication instance = null;

    public static NPApplication getInstance() {
        if (instance == null) {
            instance = new NPApplication();
        }
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


}
