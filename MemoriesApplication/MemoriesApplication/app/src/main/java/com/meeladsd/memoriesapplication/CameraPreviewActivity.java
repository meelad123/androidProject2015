package com.meeladsd.memoriesapplication;


import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraPreviewActivity extends Activity {
    private Camera _camera;
    private CameraPreview _cameraPreview;
    private byte[] _cameraData;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);
        _camera = getCameraInstance();
        _camera.setDisplayOrientation(90);
        _cameraPreview = new CameraPreview(this, _camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(_cameraPreview);

        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _camera.takePicture(null, null, mPicture);
            }
        });
    }

    /**
     * Helper method to access the camera returns null if it cannot get the
     * camera or does not exist
     *
     * @return
     */
    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            // cannot get camera or does not exist
        }
        return camera;
    }

    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            _cameraData = data;
            Intent intent = new Intent(getApplicationContext(), ViewMemoriesActivity.class);
            intent.putExtra("imageData",_cameraData);
            setResult(Activity.RESULT_OK, intent);
            finish();

        }
    };

}
