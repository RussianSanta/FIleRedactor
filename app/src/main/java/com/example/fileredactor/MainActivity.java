package com.example.fileredactor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText textInput;
    private EditText fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fileName = findViewById(R.id.fileName);
        textInput = findViewById(R.id.textInput);
    }

    private File getExternalPath(String fileName) {
        if (fileName.length()==0) fileName = "text.txt";
        File file = new File(getExternalFilesDir(null), fileName);
        System.out.println(file.getName());
        System.out.println(file.getAbsolutePath());
        return file;
    }

    // сохранение файла
    public void saveText(View view) {
        try (FileOutputStream fos = new FileOutputStream(getExternalPath(fileName.getText().toString()))) {
            String text = textInput.getText().toString();
            fos.write(text.getBytes());
            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        } catch (IOException ex) {
            Toast.makeText(this, "Файл не найден", Toast.LENGTH_SHORT).show();
        }
    }

    // открытие файла
    public void openFile(View view) {
        File file = getExternalPath(fileName.getText().toString());
        // если файл не существует, выход из метода
        if (!file.exists()) {
            Toast.makeText(this, "Файла не существует", Toast.LENGTH_SHORT).show();
            return;
        }
        try (FileInputStream fin = new FileInputStream(file)) {
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            if (file.getName().contains(".txt")) {
                String text = new String(bytes);
                textInput.setText(text);
            }
            else {
                Intent intent = new Intent();
                intent.setClass(this, ImgActivity.class);
                intent.putExtra("filePath",file.getAbsolutePath());
                startActivity(intent);
            }
        } catch (IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}