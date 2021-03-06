package com.helixtech.msgservicetest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Messenger mService;
    private final Messenger mMessenger = new Messenger(new IncomingHandler());
    private static final String TAG = "ClientActivity";
    private static final int MSG_REGISTER_CLIENT = 44;
    private static final int MSG_SET_VALUE = 66;
    private static final int MSG_UNREGISTER_CLIENT = 55;

    private static boolean mIsBound = false;

    class IncomingHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MainActivity.MSG_SET_VALUE:
                    Log.d(TAG, "Msg from Service : received/" + msg.arg1);
                    break;
                case MainActivity.MSG_UNREGISTER_CLIENT:
                    Log.d(TAG, "Msg from Service : UNREGISTER");
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected()");
            mService = null;
            mIsBound = false;
        }


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service); //서비스가 전달해준 IBinder 객체를 기반으로 Messenger 객체 생성
            Log.d(TAG, "onServiceConnected()");

            try {
                Message msg = Message.obtain(null, MSG_REGISTER_CLIENT);
                msg.replyTo = mMessenger;
                mService.send(msg);

                Log.d(TAG, "Msg set value 전 : " + this.hashCode());
                msg = Message.obtain(null, MSG_SET_VALUE, this.hashCode(), 0);
                mService.send(msg);
                Log.d(TAG, "Msg set value 후 : " + msg.arg1);
                mIsBound = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        LinearLayout ll = new LinearLayout(getBaseContext());
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        ll.setOrientation(LinearLayout.VERTICAL);

        Button btn3 = new Button(getBaseContext());
        btn3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent();
                i.setPackage("com.helixtech.msgservicetest2");
                i.setAction("com.helixtech.msgservicetest2.MessengerService");
                bindService(i, conn, Context.BIND_AUTO_CREATE);
                mIsBound = true;
            }
        });
        btn3.setText("Bind_Service");

        Button btn4 = new Button(getBaseContext());
        btn4.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                if (mIsBound) {
                    try {
                        Message msg = Message.obtain(null, MSG_UNREGISTER_CLIENT);
                        msg.replyTo = mMessenger;
                        mService.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    unbindService(conn);
                    mIsBound = false;
                }
            }
        });
        btn4.setText("Unbind_Service");

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(btn3, param);
        ll.addView(btn4, param);
        setContentView(ll);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsBound) {
            unbindService(conn);
            mIsBound = false;
        }

    }
}
