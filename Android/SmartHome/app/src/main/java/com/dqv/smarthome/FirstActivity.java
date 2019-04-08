package com.dqv.smarthome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dqv.smarthome.Activity.LoginActivity;
import com.dqv.smarthome.Activity.MainActivity;
import com.dqv.smarthome.Application.MyApplication;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        if (MyApplication.getInstance().getPrefManager().getUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else
        {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
