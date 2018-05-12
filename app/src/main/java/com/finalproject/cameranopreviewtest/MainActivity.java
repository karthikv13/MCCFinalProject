package com.finalproject.cameranopreviewtest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {

    CameraControllerV2WithoutPreview ccv2WithoutPreview;
    AutoFitTextureView textureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = getIntent();

        textureView = (AutoFitTextureView)findViewById(R.id.textureview);
        ccv2WithoutPreview = new CameraControllerV2WithoutPreview(getApplicationContext());

        findViewById(R.id.getpicture).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(ccv2WithoutPreview != null){
                    ccv2WithoutPreview.openCamera();
                    try { Thread.sleep(20); } catch (InterruptedException e) {}
                    ccv2WithoutPreview.takePicture();
                }
            }
        });

        getPermissions();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void getPermissions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                //Requesting permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override //Override from ActivityCompat.OnRequestPermissionsResultCallback Interface
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission granted
                }
                return;
            }
        }
    }
}
