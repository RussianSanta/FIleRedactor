package com.example.fileredactor;

import static java.lang.System.out;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImgActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);

        imageView = findViewById(R.id.imageView);
        String filePath = getIntent().getExtras().get("filePath").toString();
        imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
        out.println(filePath);
    }

    public void back(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    public void save(View view) {
        String filePath = getIntent().getExtras().get("filePath").toString();
        imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        String fileName = "newImage.jpg";

        try (FileOutputStream fos = new FileOutputStream(new File(getExternalFilesDir(null), fileName))) {
            bitmap.compress(Bitmap.CompressFormat.JPEG,90,fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}