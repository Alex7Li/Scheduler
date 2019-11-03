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

    // get the valid tags for a course
    protected List<String> validTags() {
        DatabaseAccessor d = new DatabaseAccessor();
        List<String> courseIDs = new ArrayList<>();
        for (Course c : d.getCourseList()) {
            courseIDs.add(c.getCourseNum());
        }
        return courseIDs;
    }

    protected void addCourse() {
        String courseNum = ((TextView) findViewById(R.id.addCourseTitle)).getText().toString();
        ;
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

<<<<<<< HEAD
=======
        Button addCourseBtn = (Button) findViewById(R.id.addCourse);
>>>>>>> c2d65a48dafd49a39e716d39632b2af7d5e95240
        TextView addCourseName = findViewById(R.id.addCourseName);
        TextView addCredit = findViewById(R.id.addCourseCredits);
        TextView addTerm = findViewById(R.id.courseTerm);

        Button addCourseBtn = (Button) findViewById(R.id.addCourse);
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< HEAD
                String courseName = addCourseName.getText().toString();
                String creditName = addCredit.getText().toString();
                String termYear = addTerm.getText().toString();
=======
                // add to course to the database
                addCourse();
                String[] x = new String[2];
                x[0] = addCourseName.getText().toString();
                x[1] = addCredit.getText().toString();
>>>>>>> c2d65a48dafd49a39e716d39632b2af7d5e95240

            }
        });
<<<<<<< HEAD


=======
>>>>>>> c2d65a48dafd49a39e716d39632b2af7d5e95240
    }
}