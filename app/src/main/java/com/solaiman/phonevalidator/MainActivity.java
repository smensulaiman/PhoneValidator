package com.solaiman.phonevalidator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements MyListener {

    TextView txtResponse;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestSmsPermission();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

        txtResponse = findViewById(R.id.txtResponse);
        new NotificationService().setListener(this);

        SMSListener.bindListener(new Common.OTPListener() {
            @Override
            public void onOTPReceived(String extractedOTP) {
                txtResponse.setText(extractedOTP);
            }
        });

    }

    @Override
    public void setValue(String txt) {
        txtResponse.setText(txt);
        if(txt.toLowerCase().contains("otp")){
            myRef.child("OTP").setValue(txt);
        }
    }

    private void requestSmsPermission() {
        String permission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if ( grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(MainActivity.this, permission_list, 1);
        }
    }
}
