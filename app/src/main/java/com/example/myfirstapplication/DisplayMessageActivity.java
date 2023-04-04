package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.TimeZone;

public class DisplayMessageActivity extends AppCompatActivity {
    TextView mainTv;            //显示现在时间
    TextView textpassedtime;    //显示已消耗时间
    Date starttime;
    Date nowtime;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
//    public static final String EXTRA_MESSAGE2 = "com.example.myfirstapp.MESSAGE2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView2);

//        这一段用于内部存储的，暂时不用了
//        String filename = "myfile.txt";
//        String fileContents = message+"\n";
//        FileOutputStream outputStream;
//
//        try {
//            outputStream = openFileOutput(filename, Context.MODE_APPEND);
//            outputStream.write(fileContents.getBytes());
//            textView.setText(getFilesDir().getPath());
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            textView.setText("wrong");
//        }



//        外部存储
//        File dir = new File("/storage/emulated/0/Documents/lyDiary/");
//        File dir = new File("/Internal storage/Documents/lyDiary/");
        File dir = new File("/storage/self/primary/Documents/lyDiary/");
        if (dir.isDirectory()) {
            textView.setText("right");
        } else {
            dir.mkdir();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");//格式
        Date curDate = new Date(System.currentTimeMillis());
        starttime=curDate;
        String datefilename = formatter.format(curDate);


//        File datefile = new File("/storage/emulated/0/Documents/lyDiary/"+datefilename+".txt");
        File datefile = new File("/storage/self/primary/Documents/lyDiary/"+datefilename+".txt");
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

            textView.setText(message);
            bw.append("Start:\t\t"+curtime+"\n");
            bw.append("Event:\t\t"+message+"\n");
            bw.close();
            fw.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //用于实时显示时间，用的是线程，线程和handler写在了下面，这里是启动
//        TextView mainTv;这个要放在最开头
        mainTv = findViewById(R.id.textView7);
        new TimeThread().start();//启动线程

        //显示开始的时间：
        TextView textstarttime = findViewById(R.id.textView8);
        textstarttime.setText(new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())));

        //显示用时
        textpassedtime = findViewById(R.id.textView9);
    }



    //写一个新的线程每隔一秒发送一次消息,这样做会和系统时间相差1秒
    public class TimeThread extends Thread{
        @Override
        public void run() {
            super.run();
            do{
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (true);

        }
    }
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    nowtime=new Date(System.currentTimeMillis());;
                    mainTv.setText(new SimpleDateFormat("HH:mm:ss").format(nowtime));

                    long diff = nowtime.getTime() - starttime.getTime();//这样得到的差值是毫秒级别
                    diff=diff-TimeZone.getDefault().getRawOffset();//消除时区的影响，不然会差8小时
                    Date pasttime = new Date();
                    pasttime.setTime(diff);
                    textpassedtime.setText(new SimpleDateFormat("HH:mm:ss").format(pasttime));
                    break;
            }
            return false;
        }
    });



    //按钮
    public void finished(View view) {
        Intent intent = new Intent(this, FinishRecord.class);
        TextView textView = findViewById(R.id.textView9);
        String message = textView.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void suspended(View view) {
        Intent intent = new Intent(this, SuspendRecord.class);
        TextView textView = findViewById(R.id.textView2);
        String message = textView.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}