package com.example.a10244985.androidhttp;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class GetInfo {

    private String aid;
    private String result_json;
    private String base_url;
    private String cookies;

    public GetInfo(String aid_){
        this.aid = aid_;
        this.result_json = null;

    }

    public void set_parameter(String url, String cookie){

        this.base_url = url;
        this.cookies = cookie;

    }

    public void send_request(){

        String url = this.base_url;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });


    }



}
