package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextTextMultiLine);
        String message = editText.getText().toString();
        message=message.replaceAll("\\n*$", "");
        if(message != null && message.length() != 0) {
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
    }

    public void memoinput(View view) {
        Intent intent = new Intent(this, memohint.class);
        EditText editText = (EditText) findViewById(R.id.editTextTextMultiLine);
        String message = editText.getText().toString();
        message=message.replaceAll("\\n*$", "");
        if(message != null && message.length() != 0) {
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
    }

}