package com.helixtech.msgservicetest2;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;

public class MessengerService extends Service {

    private static final String TAG = "MessengerService";
    ArrayList<Messenger> mClients = new ArrayList<Messenger>();

    static final int MSG_REGISTER_CLIENT = 44;
    static final int MSG_UNREGISTER_CLIENT = 55;
    static final int MSG_SET_VALUE = 66;
    int mValue = 0;


    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REGISTER_CLIENT:
                    Log.d(TAG, "MSG_REGISTER_CLIENT message from client");
                    mClients.add(msg.replyTo);
                    break;
                case MSG_UNREGISTER_CLIENT:
                    Log.d(TAG, "Reveived MSG_UNREGISTER_CLIENT message from client");
                    mClients.remove(msg.replyTo);
                    break;
                case MSG_SET_VALUE:
                    Log.d(TAG, "Reveived message from client : MSG_ADD_VALUE");
                    mValue = msg.arg1;
                    for (int i = mClients.size() - 1; i >= 0; i--) {
                        try {
                            Log.d(TAG, "Send msg_added_value mnessage to client : "+i);
                            Log.d(TAG, mValue+"");
                            mClients.get(i).send(Message.obtain(null, MSG_SET_VALUE, mValue, 0));
                        } catch (RemoteException e) {
                            mClients.remove(i);
                        }
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    final Messenger mMessenger = new Messenger(new IncomingHandler());

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mMessenger.getBinder();
    }
}
