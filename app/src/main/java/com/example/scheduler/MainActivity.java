package com.example.scheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference db;

    public void changeOnClick(View v){
        startActivity(new Intent(MainActivity.this, AddCourse.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Map<String, String[]> classNameAndCredit = new HashMap<>();

        Button fab = findViewById(R.id.addCourse);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView courseName = findViewById(R.id.addCourseName);
                TextView courseTerm = findViewById(R.id.courseTerm);
                TextView credit = findViewById(R.id.courseCredits);

                String[] x = new String[2];
                x[0] = courseName.getText().toString();
                x[1] = credit.getText().toString();

                classNameAndCredit.put(courseTerm.getText().toString(), x);

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });

        TextView firstClass = findViewById(R.id.firstClass);
        TextView firstSem = findViewById(R.id.firstSem);

        for (Map.Entry<String, String[]> entry : classNameAndCredit.entrySet()) {
            firstSem.setText(entry.getKey());
            firstClass.setText(entry.getValue()[0] + "      " + entry.getValue()[1]);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}