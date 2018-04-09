package com.saifniaz.az.azscanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;

public class Picture extends AppCompatActivity {


    ImageView picture;
    Bitmap imageBitmap;

    private static final String TAG = "OpenCv";

    static {
        if(!OpenCVLoader.initDebug()){
            Log.d(TAG, "Opencv not loaded");
        }else{
            Log.d(TAG, "Opencv loaded successfully");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        picture = (ImageView) findViewById(R.id.picture);

        Bundle extras = getIntent().getExtras();
        byte[] ByteArray = extras.getByteArray("picture");

        imageBitmap = BitmapFactory.decodeByteArray(ByteArray, 0 , ByteArray.length);

        picture.setImageBitmap(imageBitmap);



    }

    public void Process(View v){
        Mat temp = new Mat(imageBitmap.getWidth(), imageBitmap.getHeight(),
                CvType.CV_8UC1);
        Utils.bitmapToMat(imageBitmap, temp);
        Imgproc.cvtColor(temp, temp, Imgproc.COLOR_RGB2GRAY);
        Utils.matToBitmap(temp, imageBitmap);

        picture.setImageBitmap(imageBitmap);
    }

    public void Cancel(View v){
        finish();
    }

    public void Check(View v){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Intent processed = new Intent(v.getContext(), MainActivity.class);

        processed.putExtra("picture", byteArray);
        setResult(3, processed);
        finish();
    }
}
