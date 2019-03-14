package com.example.a10244985.androidhttp;

import android.net.Uri;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class GetInfo {

    private static final String TAG = "GetInfo";

    private String aid;
    private String result_str;
    private String base_url;
    private String cookies;
    private RequestQueue queue;

    GetInfo(String aid_){
        this.aid = aid_;
        this.result_str = null;

    }

    void set_parameter(String url, String cookie, RequestQueue queue){

        this.base_url = url;
        this.cookies = cookie;
        this.queue = queue;

    }

    void send_request(final VolleyCallback callback){

        String url = this.base_url;
        final String put_aid = this.aid;
        final GetInfo this_task = this;

        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("aid", put_aid);
        String paramUrl=builder.build().toString();
        Log.d(TAG, paramUrl);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, paramUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        callback.onSuccessResponse(response, this_task);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("OnErrorResponse", error.toString());
                    }
                });

        queue.add(jsonObjectRequest);
    }

    void analyse_result(JSONObject result){

//        Log.d("Data confirm", result.toString());

        String lines = "";
        String[] keys = {"title", "tname", "dynamic", "videos", "desc"};

        try{

            JSONObject data = result.getJSONObject("data");

            for (String key: keys) {
                String item = data.getString(key);
                String line = String.format("%s: %s\n", key, item);
                lines = lines.concat(line);
            }

        }catch (JSONException e){
            Log.e(TAG, e.toString());
        }

        this.result_str = lines;

    }

    String get_response(){

        return this.result_str;
    }


}


