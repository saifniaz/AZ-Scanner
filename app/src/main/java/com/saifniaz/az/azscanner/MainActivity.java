package com.saifniaz.az.azscanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    Button cameraR, picture, process;
    Uri imageUri;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraR = (Button)findViewById(R.id.roll);
        picture = (Button)findViewById(R.id.pic);
        process = (Button) findViewById(R.id.process);
        image = (ImageView) findViewById(R.id.captured);

        cameraR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenCameraRoll();
            }
        });

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TakePicture();
            }
        });

        process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(image.getDrawable() == null){
                    Toast.makeText(getApplicationContext(), "No Image Selected!",
                            Toast.LENGTH_LONG).show();
                }else{
                    Log.d("Image", "IT has Image");
                    Bitmap imageBitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    Intent process = new Intent(view.getContext(), Picture.class);
                    process.putExtra("picture", byteArray);
                    startActivityForResult(process, 3);
                }
            }
        });
    }

    public  void TakePicture(){
        Intent pic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(pic, 2);
    }

    public void OpenCameraRoll(){
        Intent media = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(media, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 1){
            imageUri = data.getData();
            image.setImageURI(imageUri);
        }else if(resultCode == RESULT_OK && requestCode == 2){
            Log.i("Message", "It works");
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(bitmap);
        }else if(resultCode == RESULT_OK && requestCode == 3){
        Log.i("Message", "It works");
    }
    }
}
