package com.example.scheduler;

import android.os.Bundle;
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
    DatabaseAccessor da;
    String[] fruits = {"CSE 2221", "CSE 1223", "CSE 2321", "CSE 2231", "CSE 2421"};

    // get the valid tags for a course
    protected List<String> validTags() {
        DatabaseAccessor d = new DatabaseAccessor();
        List<String> courseIDs = new ArrayList<>();
        for (Course c : d.updateCourseList()) {
            courseIDs.add(c.getCourseNum());
        }
        return courseIDs;
    }

    private static List<List<String>> sampleData(){
        List<List<String>> preReqStr = new ArrayList<>();
        preReqStr.add(new ArrayList<>());
        preReqStr.add(new ArrayList<>());
        preReqStr.get(0).add("CSE 1111");
        preReqStr.get(1).add("CSE 1121");
        preReqStr.get(1).add("CSE 1122");
        return preReqStr;
    }
    protected void addCourseToDB() {
        String courseNum = ((TextView) findViewById(R.id.addCourseTitle)).getText().toString();
        int creditHours;
        try {
            creditHours = Integer.parseInt(((TextView) findViewById(R.id.addCourseCredits)).getText().toString());
        }catch (NumberFormatException e){
            creditHours = 0;
        }
        String informalName = ((TextView) findViewById(R.id.addCourseName)).getText().toString();
        List<List<String>> prereqs = new ArrayList<>();
        // populate prereqs... for now we use made up data
        List<List<String>> prereqStrings = sampleData();
        for (List<String> andList:prereqStrings){
            List<String> andCourses = new ArrayList<>();
            for (String courseName : andList){
                Course c = da.getCourseByNumber(courseName);
                if(c==null){
                    // the database doesn't contain a prereq, add it as a stub
                    c = new Course(courseName);
                    da.addCourse(c);
                }
                andCourses.add(courseName);
            }
            prereqs.add(andCourses);
        }
        da.addCourse(new Course(courseNum, informalName, creditHours, prereqs));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        da = new DatabaseAccessor();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, fruits);
        AutoCompleteTextView addCourseName = findViewById(R.id.addCourseName);
        addCourseName.setAdapter(adapter);

        Button addCourseBtn = findViewById(R.id.addCourse);
        TextView addCredit = findViewById(R.id.addCourseCredits);
        TextView addTerm = findViewById(R.id.courseTerm);

        Map<String, String[]> classNameAndCredit = new HashMap<>();

        addCourseBtn.setOnClickListener(view -> {
            // add to course to the database
            addCourseToDB();
            String[] x = new String[2];
            x[0] = addCourseName.getText().toString();
            x[1] = addCredit.getText().toString();

            classNameAndCredit.put(addTerm.getText().toString(), x);
        });
    }
}