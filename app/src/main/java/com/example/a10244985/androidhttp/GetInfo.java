package com.example.a10244985.androidhttp;

import android.util.Log;
import android.webkit.WebSettings;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

class GetInfo {

    private static final String TAG = "GetInfo";

    private String aid;
    private String result_json;
    private String base_url;
    private String cookies;
    private RequestQueue queue;

    GetInfo(String aid_){
        this.aid = aid_;
        this.result_json = null;

    }

    void set_parameter(String url, String cookie, RequestQueue queue){

        this.base_url = url;
        this.cookies = cookie;
        this.queue = queue;

    }

    void send_request(final VolleyCallback callback){

        String url = this.base_url;
        final String put_aid = this.aid;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        callback.onSuccessResponse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("OnErrorResponse", error.toString());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Host", "api.bilibili.com");
                headers.put("Connection", "keep-alive");
                headers.put("Accept", "application/json, text/plain, */*");
                headers.put("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2224.3 Safari/537.36");
                headers.put("Referer", "https://www.bilibili.com");
                headers.put("Accept-Encoding", "gzip, deflate, br");
                headers.put("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");

                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("aid", put_aid);
                return params;

            }
        };

        queue.add(jsonObjectRequest);
    }

    void analyse_result(JSONObject result){
        this.result_json = result.toString();

    }

    String get_response(){

        return this.result_json;
    }


}


