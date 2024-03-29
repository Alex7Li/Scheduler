package com.example.scheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton addCourseFloatBtn;
    AccountAccessor accountAccessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle b = getIntent().getExtras();
        final int year = b == null ? 0 : b.getInt("year");
        final String name = (b == null || (b.getString("name") == null)) ? "john" : b.getString("name");

        accountAccessor = new AccountAccessor(name.toLowerCase(), year, this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addCourseFloatBtn = findViewById(R.id.toJohnAct);
        addCourseFloatBtn.setOnClickListener(view -> openActivity2(name));
    }

    //will be called by account accessor once it's done with updating the db
    public void display() {
        //set text to table layout
        TextView[] termsAU1toSP4 = new TextView[8];
        termsAU1toSP4[0] = findViewById(R.id.AU1);
        termsAU1toSP4[1] = findViewById(R.id.SP1);
        termsAU1toSP4[2] = findViewById(R.id.AU2);
        termsAU1toSP4[3] = findViewById(R.id.SP2);
        termsAU1toSP4[4] = findViewById(R.id.AU3);
        termsAU1toSP4[5] = findViewById(R.id.SP3);
        termsAU1toSP4[6] = findViewById(R.id.AU4);
        termsAU1toSP4[7] = findViewById(R.id.SP4);


        TextView[] classNamesAndCredit = new TextView[8];
        classNamesAndCredit[0] = findViewById(R.id.AUclass1);
        classNamesAndCredit[1] = findViewById(R.id.SPclass1);
        classNamesAndCredit[2] = findViewById(R.id.AUclass2);
        classNamesAndCredit[3] = findViewById(R.id.SPclass2);
        classNamesAndCredit[4] = findViewById(R.id.AUclass3);
        classNamesAndCredit[5] = findViewById(R.id.SPclass3);
        classNamesAndCredit[6] = findViewById(R.id.AUclass4);
        classNamesAndCredit[7] = findViewById(R.id.SPclass4);

        Map<String, List<String>> coursesByTerm = accountAccessor.getAccountCourses();

        int numStartYear = accountAccessor.getStartYear();

        for (int i = 0; i < 1; i++){
            String settext = termsAU1toSP4[i].getText().toString() + numStartYear;
            termsAU1toSP4[i].setText(settext); //input method to get start year later
            String semester = (i%2==0?"AU":"SP") + numStartYear;
            numStartYear +=i%2;

            try {
                for (String courseTaken : coursesByTerm.get(semester)) {
                    classNamesAndCredit[i].append(courseTaken + "\tCredits\t" + CourseAccessor.getCourseByNumber(courseTaken).getcreditHours());
                }
            }catch (NullPointerException ignored){}
        }
    }

    /*
     * Starts activity with MainActivity.this as intent
     */
    public void openActivity2(String name) {
        //send account name from main activity to addCourse
        Intent intent = new Intent(MainActivity.this, AddCourse.class);
        intent.putExtra("name", name);
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