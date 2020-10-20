package com.example.tutorial13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.opengl.ETC1;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editNumber,editSMS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editNumber=findViewById(R.id.editPhone);
        editSMS=findViewById(R.id.editMsg);
    }

    public void callToNumber(View view) {
        if (isCallPermisstionAllowed()){
            call();
        }
    }

    private void call(){
        String phoneNumber = editNumber.getText().toString();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    private boolean isCallPermisstionAllowed() {
        if (Build.VERSION.SDK_INT>=23){
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                Log.v("TAG","Permission is Granted");
                return true;
            } else {
                Log.v("TAG","Permission is Revoked");
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
                return false;
            }
        } else {
            Log.v("TAG","Permission is Granted");
            return true;
        }
    }

    public void sendTextMessage(View view) {

        if (isSMSPermisstionAllowed()){
            sms();
        }
    }

    private void sms() {
        String phoneNumber = editNumber.getText().toString();
        String smstext = editSMS.getText().toString();
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber,null,smstext,null,null);
        Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_SHORT).show();
    }

    private boolean isSMSPermisstionAllowed() {
        if (Build.VERSION.SDK_INT>=23){
            if (checkSelfPermission(android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                Log.v("TAG","Permission is Granted");
                return true;
            } else {
                Log.v("TAG","Permission is Revoked");
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},2);
                return false;
            }
        } else {
            Log.v("TAG","Permission is Granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case 1:
                if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    call();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    sms();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}