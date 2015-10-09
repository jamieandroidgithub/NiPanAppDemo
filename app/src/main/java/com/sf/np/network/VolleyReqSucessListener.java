package com.sf.np.network;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.sf.np.NPApplication;
import com.sf.np.variable.ToastMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jamie on 2015/9/28.
 */
public abstract class VolleyReqSucessListener implements Response.Listener<JSONObject> {
    public static final String TAG = "VolleyReqSucessListener";
    public static final int SUCCESS = 0;
    public static final int DATA_NOT_FOUND = 10003;

    @Override
    public void onResponse(JSONObject response) {
        try {
            if (response.getInt("code") == SUCCESS) {
                if(response.has("data")){
                    doResponse(response.getString("data"));
                }else{
                    doResponse(null);
                }
            }else if(response.getInt("code") == DATA_NOT_FOUND){
                doResponse(null);
                Log.w(TAG, "无数据返回：" + response.toString());
            } else {
                doErrorResponse(response);
            }
        } catch (JSONException e) {
            Toast.makeText(NPApplication.getInstance(), ToastMessage.ERROR, Toast.LENGTH_SHORT).show();
            Log.w(TAG, "Json解析错误：" + response.toString());
        }
    }

    public abstract void doResponse(String dataJsonStr);
    public void doErrorResponse(JSONObject response) throws JSONException {
        Toast.makeText(NPApplication.getInstance(), ToastMessage.ERROR, Toast.LENGTH_SHORT).show();
        Log.w(TAG, "接口返回错误：" + response.toString());
    }
}
