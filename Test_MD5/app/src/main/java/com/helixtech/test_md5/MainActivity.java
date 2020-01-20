package com.helixtech.test_md5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    TextView textMd5;
    String MD5 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textMd5 = findViewById(R.id.md5_txt);
        MD5 = testMD5("COM.ABC.ABCDTESTPLTE1805");
        textMd5.setText(MD5);
    }

    public static String testMD5(String str) {
        String MD5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            MD5 = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            MD5 = null;
        }
        return MD5;
    }
}
