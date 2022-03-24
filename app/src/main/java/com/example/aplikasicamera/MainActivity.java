package com.example.aplikasicamera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fr_kamera;
    private SurfaceView sv;
    private SurfaceHolder sh;
    private Camera cm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        fr_kamera = findViewById(R.id.fr_kamera);
        sv = new SurfaceView(this);
        sh = sv.getHolder();
        sh.addCallback(new SurfaceHolderCallback());
        fr_kamera.addView(sv);
    }

    private class SurfaceHolderCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            cm = Camera.open();
            Camera.Parameters parameters = cm.getParameters();
            List<Camera.Size> ss = parameters.getSupportedPreviewSizes();
            Camera.Size pictSize = ss.get(0);
            parameters.setPictureSize(pictSize.width, pictSize.height);
            cm.setParameters(parameters);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            try {
                cm.setDisplayOrientation(90);
                cm.setPreviewDisplay(sv.getHolder());
                Camera.Parameters parameters = cm.getParameters();
                List<Camera.Size> previewSize = cm.getParameters().getSupportedPreviewSizes();
                Camera.Size pre = previewSize.get(0);
                parameters.setPreviewSize(pre.width, pre.height);

                ViewGroup.LayoutParams lp = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.FILL_PARENT);

                sv.setLayoutParams(lp);
                cm.setParameters(parameters);
                cm.startPreview();
            }catch (Exception e){}

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            cm.stopPreview();
            cm.release();
        }
    }
}