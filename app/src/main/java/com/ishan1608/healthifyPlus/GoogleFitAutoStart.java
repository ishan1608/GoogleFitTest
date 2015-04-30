package com.ishan1608.healthifyPlus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class GoogleFitAutoStart extends BroadcastReceiver {
    public GoogleFitAutoStart() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent googleFitIntent = new Intent(context, GoogleFitService.class);
        context.startService(googleFitIntent);
        Log.i("GoogleFitAutoStart", "started on boot");
        Toast.makeText(context, "GoogleFitAutoStart" + " started on boot", Toast.LENGTH_LONG).show();
    }
}
