package com.example.trawler;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.maps.model.LatLng;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class CameraFragment extends Fragment {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PreviewView previewView;
    Button take_pic;
    ImageCapture imageCapture;
    Intent i;
    PopupWindow popUp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera , container, false);
        previewView = view.findViewById(R.id.previewView);
        //i = new Intent(this.getContext(), ShowPopUp.class);
        popUp = new PopupWindow(this.getContext());
        cameraProviderFuture = ProcessCameraProvider.getInstance(view.getContext().getApplicationContext());
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                cameraProvider.unbindAll();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(view.getContext().getApplicationContext()));
        imageCapture =
                new ImageCapture.Builder()
                        .build();

        take_pic = view.findViewById(R.id.camera_capture_button);
        take_pic.setOnClickListener((v)->{
            imageCapture.takePicture(ContextCompat.getMainExecutor(view.getContext().getApplicationContext()),
                    new ImageCapture.OnImageCapturedCallback() {
                        @Override
                        public void onCaptureSuccess(ImageProxy image) {
                            // insert your code here.
                            Toast.makeText(view.getContext().getApplicationContext(),  "Image Taken", Toast.LENGTH_SHORT).show();
                            Catch_Metadata c = MainActivity.process(image);
                            MainActivity.add_data(c);
                            showPopUp("Congrats! You caught a "+c.getFish_info().getComName() + "!");
                        }
                        @Override
                        public void onError(ImageCaptureException error) {
                            // insert your code here.
                            Toast.makeText(view.getContext().getApplicationContext(),  "Nope", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

        });
        return view;
    }

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this.getContext(), cameraSelector, preview, imageCapture);
    }

    void showPopUp(String message){
        LinearLayout layout = new LinearLayout(this.getContext());
        LinearLayout mainLayout = new LinearLayout(this.getContext());
        TextView tv = new TextView(this.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        tv.setText(message);
        layout.addView(tv, params);
        popUp.setContentView(layout);
        // popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
        popUp.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
        popUp.update(50, 50, 300, 80);

    }

}

