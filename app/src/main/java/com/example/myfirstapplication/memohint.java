package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class memohint extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memohint);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = findViewById(R.id.textView6);

//        外部存储
//        File dir = new File("/storage/emulated/0/Documents/lyDiary/");
//        File dir = new File("/Internal storage/Documents/lyDiary/");
        File dir = new File("/storage/self/primary/Documents/lyDiary/");
        if (dir.isDirectory()) {
        } else {
            dir.mkdir();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");//格式
        Date curDate = new Date(System.currentTimeMillis());
        String datefilename = formatter.format(curDate);


        //        File datefile = new File("/storage/emulated/0/Documents/lyDiary/"+datefilename+".txt");
        File datefile = new File("/storage/self/primary/Documents/lyDiary/" + datefilename + ".txt");
        // 判断文件是否存在
        if (datefile.exists()) {
        } else {
            try {
                datefile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter fw = new FileWriter(datefile, true);
            BufferedWriter bw = new BufferedWriter(fw);

            SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");//格式
            Date curDate2 = new Date(System.currentTimeMillis());
            String curtime = formatter2.format(curDate2);

            textView.setText("Completed!");
            bw.append(curtime + "\n");
            bw.append(message + "\n\n\n");
            bw.close();
            fw.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}