package com.meeladsd.memoriesapplication;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by alem1324 on 12/9/2015.
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder _surfaceHolder;
    private Camera _camera;

    // Constructor that obtains context and camera
    @SuppressWarnings("deprecation")
    public CameraPreview(Context context, Camera camera) {
        super(context);
        this._camera = camera;
        this._surfaceHolder = this.getHolder();
        this._surfaceHolder.addCallback(this);
        this._surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            _camera.setPreviewDisplay(surfaceHolder);
            _camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        _camera.stopPreview();
        _camera.release();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        // start preview with new settings
        try {
            _camera.setPreviewDisplay(surfaceHolder);
            _camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
