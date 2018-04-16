package com.saifniaz.az.azscanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;

public class Picture extends AppCompatActivity {


    ImageView picture;
    Bitmap imageBitmap;
    int count = 0;
    float x1 = 10.0f, x2 = 140.0f, x3 = 140.0f, x4 = 10.0f;
    float y1 = 10.0f, y2 = 10.0f, y3 = 190.0f, y4 = 190.0f;

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

        picture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int count = 0;
                if(count< 4){
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        /*if(count == 0){
                            x1 = motionEvent.getX();
                            y1 = motionEvent.getY();
                            count++;
                        }else if(count == 1){
                            x2 = motionEvent.getX();
                            y2 = motionEvent.getY();
                            count++;
                        }else if(count == 2){
                            x3 = motionEvent.getX();
                            y3 = motionEvent.getY();
                            count++;
                        }else if(count == 3){
                            x4 = motionEvent.getX();
                            y4 = motionEvent.getY();
                            count++;
                        }else {
                            Toast.makeText(getApplicationContext(), "Four points selected!",
                                    Toast.LENGTH_SHORT).show();
                        }*/
                    }
                }else{
                    return false;
                }
                return true;
            }
        });

    }



    public void Process(View v){
        Log.e("Cood", ""+x1+", "+y1+", "+x2+", "+y2+", "+x3+", "+y3+", "+x4+", "+y4);
        Log.e("Shape", "" + imageBitmap.getHeight()+", "+imageBitmap.getWidth()+ "");
        Mat srcImg = new Mat(imageBitmap.getWidth(), imageBitmap.getHeight(),
                CvType.CV_8UC1);
        Utils.bitmapToMat(imageBitmap, srcImg);
        Mat destImg = new Mat(srcImg.width(), srcImg.height(), srcImg.type());
        Mat src = new MatOfPoint2f(new Point(x1, y1), new Point(x2, y2), new Point(x3, y3), new Point(x4, y4));
        Mat dst = new MatOfPoint2f(new Point(0, 0), new Point(destImg.width() - 1, 0), new Point(destImg.width() - 1, destImg.height() - 1),
                new Point(0, destImg.height() - 1));


        Mat transform = Imgproc.getPerspectiveTransform(src, dst);
        Imgproc.warpPerspective(srcImg, destImg, transform, destImg.size());


        //Imgproc.cvtColor(temp, temp, Imgproc.COLOR_RGB2GRAY);

        Bitmap bmp = Bitmap.createBitmap(destImg.cols(), destImg.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(destImg, bmp);

        //Utils.matToBitmap(destImg, imageBitmap);
        picture.setImageBitmap(bmp);

        imageBitmap = bmp;
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
