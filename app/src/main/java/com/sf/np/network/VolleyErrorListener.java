package com.sf.np.network;

import android.view.View;

import com.android.volley.*;
import com.android.volley.Response;

public class VolleyErrorListener implements Response.ErrorListener {
    private View errorView;
    private View loadingView;
//    private PullToRefreshAdapterViewBase listView;

    public VolleyErrorListener(View errorView, View loadingView/*, PullToRefreshAdapterViewBase listView*/){
        this.errorView = errorView;
        this.loadingView = loadingView;
//        this.listView = listView;
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        VolleyExceptionHelper.helper(error, errorView, loadingView/*, listView*/);
    }
}