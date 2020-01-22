package com.helixtech.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.IRemoteService;
import com.example.myapplication.IRemoteServiceCallback;


public class MainActivity extends AppCompatActivity {
    /*private final String TAG = "MainActivity";

    private Messenger mServiceCallback = null;
    private Messenger mClientCallback = new Messenger(new CallbackHandler());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnAddValue = findViewById(R.id.btn_add_value);
        btnAddValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mServiceCallback != null) {
                    Message msg = Message.obtain(null, RemoteService.MSG_ADDED_VALUE);
                    msg.arg1 = 10;
                    try {
                        mServiceCallback.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "Send MSG_ADD_VALUE message to Service");
                }
            }
        });

        Log.d(TAG, "Trying to connect to Service");
        Intent intent = new Intent(getApplicationContext(), RemoteService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            mServiceCallback = new Messenger(service);

            Message connect_msg = Message.obtain(null, RemoteService.MSG_CLIENT_CONNECT);
            connect_msg.replyTo = mClientCallback;
            try {
                mServiceCallback.send(connect_msg);
                Log.d(TAG, "Send MSG_CLIENT_CONNECT message to Service");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            mServiceCallback = null;
        }
    };

    private class CallbackHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case RemoteService.MSG_ADDED_VALUE:
                    Log.d(TAG, "Recevied MSG_ADDED_VALUE message from service ~ value : " + msg.arg1);
                    break;
            }
        }
    }*/

    IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub() {
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
        Log.d("진입","startServiceBind()");
        //startService(new Intent(this, RemoteService.class));
        //Intent intent = new Intent(this, RemoteService.class);
        Intent i = new Intent();
        i.setPackage("com.example.myapplication");
        i.setAction("com.example.myapplication.RemoteService");
        Log.d("중간","startServiceBind()");
        bindService(i, mConnection, Context.BIND_AUTO_CREATE);
        Log.d("끝","startServiceBind()");
    }

    private void stopServiceBind() {
        unbindService(mConnection);
        //startService(new Intent(this, RemoteService.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        startServiceBind();
        super.onResume();
    }

    @Override
    protected void onStop() {
        stopServiceBind();
        super.onStop();
    }

}




