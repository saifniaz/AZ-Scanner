package com.saifniaz.az.azscanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Picture extends AppCompatActivity {

    Button check;
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        picture = (ImageView) findViewById(R.id.picture);

        Bundle extras = getIntent().getExtras();
        byte[] ByteArray = extras.getByteArray("picture");

        Bitmap imageBitmap = BitmapFactory.decodeByteArray(ByteArray, 0 , ByteArray.length);

        picture.setImageBitmap(imageBitmap);

    }

    public void Cancel(View v){
        finish();
    }

    public void Check(View v){
        finish();
    }
}
