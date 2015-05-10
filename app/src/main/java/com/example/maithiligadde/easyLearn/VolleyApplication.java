package com.example.maithiligadde.easyLearn;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.parse.Parse;

public class VolleyApplication extends Application {
    private static VolleyApplication sInstance;

    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "iivVAWy5b6TbI3KLczeOiV54f79Ij4whAyI704d2", "isECpylgKDEeQr7lj8CkyzKrmXS2d5HDHkgyBSxU");
        mRequestQueue = Volley.newRequestQueue(this);
        sInstance = this;
    }

    public synchronized static VolleyApplication getInstance() {
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}