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

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference db;
    private Button switchActBtn;
    private FloatingActionButton addCourseFloatBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountAccessor accountAccessor = new AccountAccessor("john");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addCourseFloatBtn = findViewById(R.id.toJohnAct);

        addCourseFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });

        //set text to table layout
        TextView[] termsAU1toSP4 = new TextView[8];
        TextView[] classNamesAndCredit = new TextView[8];

        TreeMap<String, List<String>> coursesByTerm = accountAccessor.getAccountCourses();

        int i = 0;
        for(Map.Entry<String, List<String>> entry : coursesByTerm.entrySet()) {
            String setText = termsAU1toSP4[i].getText().toString();
            termsAU1toSP4[i].setText(setText + "start year" + (i % 2) );
            for (String courseTaken : entry.getValue()) {
                classNamesAndCredit[i].append(courseTaken + "      " + "Credit");
                //idk if this append string to next line
            }
            i++;
        }



    }

    /*
     * Starts activity with MainActivity.this as intent
     */
    public void openActivity2() {
        Intent intent = new Intent(MainActivity.this, AddCourse.class);
        startActivity(intent);
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