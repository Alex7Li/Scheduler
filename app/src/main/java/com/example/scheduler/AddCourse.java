package com.example.scheduler;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddCourse extends AppCompatActivity {
    private static final String TAG = AddCourse.class.getName();

    String[] fruits = {"CSE 2221", "CSE 1223", "CSE 2321", "CSE 2231", "CSE 2421"};

    /*
     * Returns a {List<String>} containing all courseIDs in {DatabaseAccessor} d
     */
    protected List<String> validTags() {
        DatabaseAccessor d = new DatabaseAccessor();
        List<String> courseIDs = new ArrayList<>();
        for (Course c : d.getCourseList()) {
            courseIDs.add(c.getCourseNum());
        }
        return courseIDs;
    }

    /*
     * Function to add course from {TextView} findViewById to new {DatabaseAccessor}
     */
    protected void addCourse() {
        String courseNum = ((TextView) findViewById(R.id.addCourseTitle)).getText().toString();
        int creditHours = Integer.parseInt(((TextView) findViewById(R.id.addCourseCredits)).getText().toString());
        String informalName = ((TextView) findViewById(R.id.addCourseName)).getText().toString();
        List<List<String>> prereqs = new ArrayList<>();
        new DatabaseAccessor().addCourse(new Course(courseNum, informalName, creditHours, prereqs));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, fruits);
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.addCourseName);
        actv.setAdapter(adapter);

        TextView addCourseName = findViewById(R.id.addCourseName);
        TextView addCredit = findViewById(R.id.addCourseCredits);
        TextView addTerm = findViewById(R.id.courseTerm);

        Button addCourseBtn = (Button) findViewById(R.id.addCourse);

        String[] userYear = new String[8];
        userYear[0]="AU1";
        userYear[1]="SP1";
        userYear[2]="AU2";
        userYear[3]="SP2";
        userYear[4]="AU3";
        userYear[5]="SP3";
        userYear[6]="AU4";
        userYear[7]="SP4";

        TextView getStartingYear = findViewById(R.id.getStartingYear);

        String startYearStr = getStartingYear.getText().toString().substring(3);
        int startYear = Integer.parseInt(startYearStr);


         addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCourse();
                String[] x = new String[2];
                x[0] = addCourseName.getText().toString(); //CSE 2221
                x[1] = addCredit.getText().toString();     //4
                String termYear = addTerm.getText().toString(); //AU18

                String courseYearStr = termYear.substring(3);
                int courseYear = Integer.parseInt(courseYearStr);

                int tableYear = 0;

                if (termYear.contains("AU")) {
                    tableYear = 2*(courseYear - startYear) + 1;
                } else {
                    tableYear = 2*(courseYear - startYear);
                }

                //db.child[by name userYear[tableYear-1]].add(courseName);

            }
        });
    }
}