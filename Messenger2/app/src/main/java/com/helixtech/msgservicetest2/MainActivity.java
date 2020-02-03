package com.helixtech.msgservicetest2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
/*
    private Messenger mService;
    private final Messenger mMessenger = new Messenger(new IncomingHandler());
    private static final String TAG = "MainActivity";
    private static final int MSG_REGISTER_CLIENT = 44;
    private static final int MSG_SET_VALUE = 66;
    private static final int MSG_UNREGISTER_CLIENT = 55;

    private static boolean mIsBound = false;

    class IncomingHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MessengerService.MSG_SET_VALUE:
                    Log.d(TAG, msg.arg1 + " -> msg set value");
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
            mService = new Messenger(service);
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
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*        LinearLayout ll = new LinearLayout(getBaseContext());
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        ll.setOrientation(LinearLayout.VERTICAL);

        Button btn3 = new Button(getBaseContext());
        btn3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MessengerService.class);
                //i.setPackage("com.helixtech.msgservicetest");
                i.setAction("com.helixtech.msgservicetest2.MessengerService");
                //Intent ii = new Intent();
                //i.setClassName("com.helixtech.msgservicetest2", "MessengerService");
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
        setContentView(ll);*/
    }

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsBound) {
            unbindService(conn);
            mIsBound = false;
        }

    }*/
}
