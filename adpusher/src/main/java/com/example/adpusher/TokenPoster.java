package com.example.adpusher;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TokenPoster extends AsyncTask<Void, String, String> {
    private Map<String, String> mData = null;// post data

    /**
     * constructor
     */
    public TokenPoster(Map<String, String> data) {
        mData = data;
    }

    /**
     * background
     */
    @Override
    protected String doInBackground(Void... params) {
        byte[] result = null;
        String str = "";
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://push.pandatainment.ru/setToken");// in this case, params[0] is URL
        try {
            JSONObject jsonObject = new JSONObject(mData);

            post.setEntity(new StringEntity(jsonObject.toString(), "UTF-8"));
            post.setHeader("Content-Type","application/json");

            HttpResponse response = client.execute(post);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpURLConnection.HTTP_OK) {
                result = EntityUtils.toByteArray(response.getEntity());
                str = new String(result, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * on getting result
     */
    @Override
    protected void onPostExecute(String result) {
        Log.d("AdPusher", "Token set result: "+result);
    }
}