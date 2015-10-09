package com.sf.np.network;

import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;


import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Jamie on 2015/9/28.
 */
public class VolleyGsonRequest {
    public static final int TIME_OUT = 15 * 1000;
    private int method;
    private String url;
    private View errorView;
    private View loadingView;
//    private PullToRefreshAdapterViewBase listView;


    public VolleyGsonRequest(String url, View errorView) {
        this.method = Request.Method.GET;
        this.url = url;
        this.errorView = errorView;
    }

    public VolleyGsonRequest(String url, View loadingView, View errorView) {
        this.method = Request.Method.GET;
        this.url = url;
        this.loadingView = loadingView;
        this.errorView = errorView;
    }

//    public VolleyGsonRequest(String url, View loadingView, View errorView, PullToRefreshAdapterViewBase listView) {
//        this.method = Request.Method.GET;
//        this.url = url;
//        this.loadingView = loadingView;
//        this.errorView = errorView;
//        this.listView = listView;
//    }

    public VolleyGsonRequest(int method, String url, View loadingView, View errorView) {
        this.method = method;
        this.url = url;
        this.loadingView = loadingView;
        this.errorView = errorView;
    }

    public VolleyGsonRequest(int method, String url) {
        this.method = method;
        this.url = url;
    }


    public JsonObjectRequest getRequest(Response.Listener<JSONObject> listener) {
        VolleyJsonObjectRequest request = new VolleyJsonObjectRequest(this.url, listener, new VolleyErrorListener(errorView, loadingView/*, listView*/));
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, 1, 1.0f));
        return request;
    }

    public JsonObjectRequest getRequestWithBody(Response.Listener<JSONObject> listener, final Map<String, String> requestBody) {
        JSONObject jsonObject = new JSONObject(requestBody);
        VolleyJsonObjectRequest request = new VolleyJsonObjectRequest(this.method, this.url, jsonObject, listener, new VolleyErrorListener(errorView, loadingView/*, listView*/));
        request.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, 1, 1.0f));
        return request;
    }

}
