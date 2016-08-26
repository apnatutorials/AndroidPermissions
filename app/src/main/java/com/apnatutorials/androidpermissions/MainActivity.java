package com.apnatutorials.androidpermissions;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_PHONE_STATE_READ = 100;
    private int checkedPermission = PackageManager.PERMISSION_DENIED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkedPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (Build.VERSION.SDK_INT >= 23 && checkedPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else
            checkedPermission = PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {
        Toast.makeText(MainActivity.this, "Requesting permission", Toast.LENGTH_SHORT).show();
        // disable inspection
        this.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                REQUEST_CODE_PHONE_STATE_READ);
    }

    /**
     * Method will be use to show device info
     *
     * @param v
     */
    public void showDeviceInfo(View v) {

        AlertDialog.Builder dBuilder = new AlertDialog.Builder(this);
        dBuilder.setTitle("Permission status");

        if (checkedPermission != PackageManager.PERMISSION_DENIED) {
            dBuilder.setMessage("Permission granted. Do whatever you want to do !");
        } else {
            dBuilder.setMessage("Permission denied !");
        }
        dBuilder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PHONE_STATE_READ:
                if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED ) {
                    checkedPermission = PackageManager.PERMISSION_GRANTED;
                }
                else{

                    if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this) ;
                        builder.setTitle("Request info");
                        builder.setMessage("Here goes explanation, Explain why app need this permission") ;
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                requestPermission();
                            }
                        });
                    }
                    else{
                        // user has selected never ask me again option
                    }
                }
                break;
        }
    }
}
