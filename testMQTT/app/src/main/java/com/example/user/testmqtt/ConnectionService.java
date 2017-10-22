package com.example.user.testmqtt;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class ConnectionService extends Service {
    public ConnectionService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //Log.d(LOG_TAG, "onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Log.d(LOG_TAG, "onStartCommand");
        Toast.makeText(this, "Start Service", Toast.LENGTH_SHORT).show();
        //someTask();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.d(LOG_TAG, "onDestroy");
        Toast.makeText(this, "Service is Destroyed", Toast.LENGTH_SHORT).show();
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
