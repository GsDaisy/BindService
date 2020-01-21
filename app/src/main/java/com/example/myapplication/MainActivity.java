package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    /*IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub() {
        @Override
        public void valueChanged(long value) throws RemoteException {
            Log.d("main value", "value : " + value);
            Log.i("main", "Activity callback value : " + value);
        }
    };

    IRemoteService mService;
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            if (service != null) {
                mService = IRemoteService.Stub.asInterface(service);
                try {
                    mService.registerCallback(mCallback);
                    String message = mService.getMessage();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            if (mService != null) {
                try {
                    mService.unregisterCallback(mCallback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void startServiceBind() {
        //startService(new Intent(this, RemoteService.class));
        //Intent intent = new Intent(this, RemoteService.class);
        Intent i = new Intent();
        i.setPackage("com.example.myapplication");
        i.setAction("com.example.myapplication.RemoteService");
        bindService(i, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void stopServiceBind() {
        unbindService(mConnection);
        //startService(new Intent(this, RemoteService.class));
    }
*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /*@Override
    protected void onResume() {
        startServiceBind();
        super.onResume();
    }

    @Override
    protected void onStop() {
        stopServiceBind();
        super.onStop();
    }*/

}




