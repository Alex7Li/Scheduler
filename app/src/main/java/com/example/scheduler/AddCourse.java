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
    CourseAccessor da;
    String[] fruits = {"CSE 2221", "CSE 1223", "CSE 2321", "CSE 2231", "CSE 2421"};

    /*
     * Returns a {List<String>} containing all courseIDs in {CourseAccessor} d
     */
    protected List<String> validTags() {
        CourseAccessor d = new CourseAccessor();
        List<String> courseIDs = new ArrayList<>();
        for (Course c : d.updateCourseList()) {
            courseIDs.add(c.getCourseNum());
        }
        return courseIDs;
    }

    private static List<List<String>> sampleData() {
        List<List<String>> preReqStr = new ArrayList<>();
        preReqStr.add(new ArrayList<>());
        preReqStr.add(new ArrayList<>());
        preReqStr.get(0).add("CSE 1111");
        preReqStr.get(1).add("CSE 1121");
        preReqStr.get(1).add("CSE 1122");
        return preReqStr;
    }

    /*
     * Function to add course from {TextView} findViewById to new {CourseAccessor}
     */
    protected void addCourseToDB() {
        String courseNum = ((TextView) findViewById(R.id.addCourseTitle)).getText().toString();
        int creditHours;
        try {
            creditHours = Integer.parseInt(((TextView) findViewById(R.id.addCourseCredits)).getText().toString());
        } catch (NumberFormatException e) {
            creditHours = 0;
        }
        String informalName = ((TextView) findViewById(R.id.addCourseName)).getText().toString();
        List<List<String>> prereqs = new ArrayList<>();
        // populate prereqs... for now we use made up data
        List<List<String>> prereqStrings = sampleData();
        for (List<String> andList : prereqStrings) {
            List<String> andCourses = new ArrayList<>();
            for (String courseName : andList) {
                Course c = da.getCourseByNumber(courseName);
                if (c == null) {
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
        da = new CourseAccessor();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, fruits);
        AutoCompleteTextView addCourseName = findViewById(R.id.addCourseName);
        addCourseName.setAdapter(adapter);

        Button addCourseBtn = findViewById(R.id.addCourse);
        TextView addCredit = findViewById(R.id.addCourseCredits);
        TextView addTerm = findViewById(R.id.courseTerm);


        String[] userYear = new String[8];
        userYear[0] = "AU1";
        userYear[1] = "SP1";
        userYear[2] = "AU2";
        userYear[3] = "SP2";
        userYear[4] = "AU3";
        userYear[5] = "SP3";
        userYear[6] = "AU4";
        userYear[7] = "SP4";

        TextView getStartingYear = findViewById(R.id.getStartingYear);

        String startYearStr = "";//getStartingYear.getText().toString().substring(3);
        int startYear = 3;// Integer.parseInt(startYearStr);


        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCourseToDB();
                String[] x = new String[2];
                x[0] = addCourseName.getText().toString(); //CSE 2221
                x[1] = addCredit.getText().toString();     //4
                String termYear = addTerm.getText().toString(); //AU18

                String courseYearStr = termYear.substring(3);
                int courseYear = 3;//Integer.parseInt(courseYearStr);

                int tableYear = 0;

                if (termYear.contains("AU")) {
                    tableYear = 2 * (courseYear - startYear) + 1;
                } else {
                    tableYear = 2 * (courseYear - startYear);
                }
                //CLEAR TEXTS FIELD
                //db.child[by name userYear[tableYear-1]].add(courseName);
            }
        });
    }
}







//        Map<String, String[]> classNameAndCredit = new HashMap<>();
//
//        addCourseBtn.setOnClickListener(view -> {
//            // add to course to the database
//            addCourseToDB();
//            String[] x = new String[2];
//            x[0] = addCourseName.getText().toString();
//            x[1] = addCredit.getText().toString();
//
//            classNameAndCredit.put(addTerm.getText().toString(), x);
//        });
