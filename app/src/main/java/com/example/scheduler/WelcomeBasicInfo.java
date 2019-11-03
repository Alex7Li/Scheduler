package com.example.scheduler;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.view.View;
import android.widget.TextView;

public class WelcomeBasicInfo extends AppCompatActivity {

    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_basic_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        start = findViewById(R.id.letsStart);

        start.setOnClickListener(view -> openActivity2());
    }

    public void openActivity2() {
<<<<<<< HEAD
        Intent intent = new Intent(WelcomeBasicInfo.this, AddCourse.class);
=======
        Intent intent = new Intent(WelcomeBasicInfo.this, MainActivity.class);
        String name = ((TextView)findViewById(R.id.AccountName)).getText().toString();
        intent.putExtra("name", name);
>>>>>>> 159fdcf881f4dc3742285f64d0f25f4763f05707
        startActivity(intent);
    }

}