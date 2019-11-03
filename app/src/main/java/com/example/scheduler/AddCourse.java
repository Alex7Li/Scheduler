package com.example.scheduler;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.EditText;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;


public class AddCourse extends AppCompatActivity {
    private static final String TAG = AddCourse.class.getName();
    CourseAccessor da;
    String[] fruits = {"CSE 2221", "CSE 1223", "CSE 2321", "CSE 2231", "CSE 2421"};

    private LinearLayout mLayout1;
    private EditText mEditText1;
    private Button mButton1;

    private LinearLayout mLayout2;
    private EditText mEditText2;
    private Button mButton2;

    private LinearLayout mLayout3;
    private EditText mEditText3;
    private Button mButton3;

    private LinearLayout mLayout4;
    private EditText mEditText4;
    private Button mButton4;

    private static List<String> prereqs1 = new ArrayList<>();
    private static List<String> prereqs2 = new ArrayList<>();
    private static List<String> prereqs3 = new ArrayList<>();
    private static List<String> prereqs4 = new ArrayList<>();


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


    // a list of sublists, where each sublist contains a selection of possible prereqs (OR)
    // and each list must be combined in and (ex. (CSE 1122 OR CSE 1121) AND (CSE 1111)
    private static List<List<String>> sampleData(){
        List<List<String>> preReqStr = new ArrayList<>();
        preReqStr.add(new ArrayList<>());
        preReqStr.add(new ArrayList<>());
        preReqStr.add(new ArrayList<>());
        preReqStr.add(new ArrayList<>());
        preReqStr.get(0).addAll(prereqs1);
        preReqStr.get(1).addAll(prereqs2);
        preReqStr.get(2).addAll(prereqs3);
        preReqStr.get(3).addAll(prereqs4);
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

        mLayout1 = (LinearLayout) findViewById(R.id.linearlayout1);
        mEditText1 = (EditText) findViewById(R.id.prereq1);
        mButton1 = (Button) findViewById(R.id.addOrPrereq1);
        mButton1.setOnClickListener(onClick1());
        TextView textView1 = new TextView(this);
        textView1.setText("New text");

        mLayout2 = (LinearLayout) findViewById(R.id.linearlayout2);
        mEditText2 = (EditText) findViewById(R.id.prereq2);
        mButton2 = (Button) findViewById(R.id.addOrPrereq2);
        mButton2.setOnClickListener(onClick2());
        TextView textView2 = new TextView(this);
        textView2.setText("New text");

        mLayout3 = (LinearLayout) findViewById(R.id.linearlayout3);
        mEditText3 = (EditText) findViewById(R.id.prereq3);
        mButton3 = (Button) findViewById(R.id.addOrPrereq3);
        mButton3.setOnClickListener(onClick3());
        TextView textView3 = new TextView(this);
        textView3.setText("New text");

        mLayout4 = (LinearLayout) findViewById(R.id.linearlayout4);
        mEditText4 = (EditText) findViewById(R.id.prereq4);
        mButton4 = (Button) findViewById(R.id.addOrPrereq4);
        mButton4.setOnClickListener(onClick4());
        TextView textView4 = new TextView(this);
        textView4.setText("New text");

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


                }
            });
                //CLEAR TEXTS FIELD
                //db.child[by name userYear[tableYear-1]].add(courseName);
    }

    private OnClickListener onClick1() {
        return new OnClickListener() {

            @Override
            public void onClick(View v) {
                mLayout1.addView(createNewTextView1(mEditText1.getText().toString()));
            }
        };
    }

    private OnClickListener onClick2() {
        return new OnClickListener() {

            @Override
            public void onClick(View v) {
                mLayout2.addView(createNewTextView2(mEditText2.getText().toString()));
            }
        };
    }

    private OnClickListener onClick3() {
        return new OnClickListener() {

            @Override
            public void onClick(View v) {
                mLayout3.addView(createNewTextView3(mEditText3.getText().toString()));

            }
        };
    }


    private OnClickListener onClick4() {
        return new OnClickListener() {

            @Override
            public void onClick(View v) {
                mLayout4.addView(createNewTextView4(mEditText4.getText().toString()));
            }
        };
    }


    private TextView createNewTextView1(String text) {
        final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        textView.setText(text);
        prereqs1.add(text);
        return textView;
    }

    private TextView createNewTextView2(String text) {
        final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        textView.setText(text);
        prereqs2.add(text);
        return textView;
    }

    private TextView createNewTextView3(String text) {
        final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        textView.setText(text);
        prereqs3.add(text);
        return textView;
    }

    private TextView createNewTextView4(String text) {
        final LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        textView.setText(text);
        prereqs4.add(text);
        return textView;
    }

}
