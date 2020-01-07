package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class RemoteService extends Service {
   /* private final String TAG = "RemoteService";
    public static final int MSG_CLIENT_CONNECT = 1;
    public static final int MSG_CLIENT_DISCONNECT = 2;
    public static final int MSG_ADD_VALUE = 3;
    public static final int MSG_ADDED_VALUE = 4;

    private ArrayList<Messenger> mClientCallbacks = new ArrayList<>();
    final Messenger mMessenger = new Messenger(new CallbackHandler());
    int mValue = 0;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class CallbackHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MSG_CLIENT_CONNECT:
                    Log.d(TAG, "Reveived MSG_CLIENT_CONNECT message from client");
                    mClientCallbacks.add(msg.replyTo);
                    break;
                case MSG_CLIENT_DISCONNECT:
                    Log.d(TAG, "Reveived MSG_CLIENT_DISCONNECT message from client");
                    mClientCallbacks.remove(msg.replyTo);
                    break;
                case MSG_ADD_VALUE:
                    Log.d(TAG, "Reveived message from client : MSG_ADD_VALUE");
                    mValue += msg.arg1;
                    for (int i = mClientCallbacks.size() - 1; i >= 0; i--) {
                        try {
                            Log.d(TAG, "Send msg_added_value mnessage to client");
                            Message added_msg = Message.obtain(null, RemoteService.MSG_ADDED_VALUE);
                            added_msg.arg1 = mValue;
                            mClientCallbacks.get(i).send(added_msg);
                        } catch (RemoteException e) {
                            mClientCallbacks.remove(i);
                        }
                    }
                    break;
            }
        }
    }*/

    public static final String INTENT_ACTION = "com.example.myapplication";
    private static final int MSG_WORK = 1;


    final RemoteCallbackList<IRemoteServiceCallback> callbacks = new RemoteCallbackList<IRemoteServiceCallback>();

    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {

        @Override
        public boolean registerCallback(IRemoteServiceCallback callback) throws RemoteException {
            boolean flag = false;
            if (callback != null) {
                flag = unregisterCallback(callback);
            }
            return flag;
        }

        @Override
        public boolean unregisterCallback(IRemoteServiceCallback callback) throws RemoteException {
            boolean flag = false;
            if (callback != null) {
                flag = callbacks.register(callback);
            }
            return flag;
        }


    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("TEST", "onBind..");
        if (intent.getAction().equals(INTENT_ACTION)) {
            Log.d("TEST", "action is equals : " + intent.getAction());
            return mBinder;
        }
        Log.d("TEst", "action is not equals : " + intent.getAction());
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("test", "service onCreate()");
        handler.sendEmptyMessage(MSG_WORK);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        handler.removeMessages(MSG_WORK);
        super.onDestroy();
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            switch (msg.what) {
                case MSG_WORK:
                    int n = callbacks.beginBroadcast();
                    for (int i = 0; i < n; i++) {
                        try {
                            callbacks.getBroadcastItem(i).valueChanged(System.currentTimeMillis());
                            Log.d("value", callbacks.getBroadcastItem(i).toString());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    Log.d("TEST", "Handler work : callbacks clients count is " + n);
                    callbacks.finishBroadcast();

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(MSG_WORK);
                    break;
                default:
                    break;
            }
            return false;
        }
    });
}
