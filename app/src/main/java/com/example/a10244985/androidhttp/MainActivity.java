package com.example.a10244985.androidhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    EditText aid=null;
    Button get=null;
    TextView content=null;
    ImageView cover=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RequestQueue requestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();

        aid = (EditText) findViewById(R.id.aidText);
        get = (Button) findViewById(R.id.getInfo);
        content = (TextView) findViewById(R.id.aidInfo);
        cover = (ImageView) findViewById(R.id.cover);

        content.setMovementMethod(ScrollingMovementMethod.getInstance());

//        final RequestQueue queue = Volley.newRequestQueue(this);

        get.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {

               String base_url = getResources().getString(R.string.URL_UP_ALBUM);
               String aid_number = aid.getText().toString();
               String cookies = getResources().getString(R.string.COOKIES);

               GetInfo task = new GetInfo(aid_number);
               task.set_parameter(base_url, cookies, requestQueue);

               VolleyCallback call = new VolleyCallback() {
                   @Override
                   public void onSuccessResponse(JSONObject result, GetInfo task) {
                       task.analyse_result(result);

                       String info = task.get_response();
                       content.setText(info);

                       String image_url = task.get_image_url(result);
                       Log.d(TAG, image_url);

                       task.image_url = image_url;

                   }
               };

               task.send_request(call);

               ImageCallback image_call = new ImageCallback() {
                   @Override
                   public void onSuccessDown(Bitmap image_bit) {

                       cover.setImageBitmap(image_bit);

                   }
               };

//               String image_url = task.image_url;
//               Log.d(TAG,image_url);
               task.get_image_bit(image_call);

            }

        });


    }
}
