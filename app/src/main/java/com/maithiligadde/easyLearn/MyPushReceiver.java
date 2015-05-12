package com.maithiligadde.easyLearn;

import android.content.Context;
import android.content.Intent;

import com.parse.ParsePushBroadcastReceiver;

public class MyPushReceiver extends ParsePushBroadcastReceiver {
    public MyPushReceiver() {
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        intent.setClass(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
