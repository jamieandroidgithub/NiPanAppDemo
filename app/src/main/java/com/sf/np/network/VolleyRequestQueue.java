package com.sf.np.network;

import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.sf.np.NPApplication;

/**
 * Created by 887938 on 2015/9/28.
 */
public class VolleyRequestQueue {

    private static String TAG = VolleyRequestQueue.class.getSimpleName();

    private static RequestQueue mRequestQueue;

    /**
     * 获取volley请求队列
     *
     * @return
     */
    public static RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(NPApplication.getInstance());
        }
        return mRequestQueue;
    }

    /**
     * 向请求队列中添加一个请求
     *
     * @param req
     * @param tag
     * @param <T>
     */
    public static <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        VolleyLog.d("Adding request to queue: %s", req.getUrl());
        getRequestQueue().add(req);
    }

    /**
     * 向请求队列中添加一个请求
     *
     * @param req
     * @param <T>
     */
    public static <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * 取消请求
     *
     * @param tag
     */
    public static void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /**
     * 取消请求
     */
    public static void cancelPendingRequests() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }

}
