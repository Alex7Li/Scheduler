package com.example.scheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static com.example.scheduler.CourseAccessor.addCourse;
import static com.example.scheduler.CourseAccessor.courses;


public class AddCourse extends AppCompatActivity {
    AccountAccessor aa;

    private LinearLayout mLayout1;
    private EditText mEditText1;

    private LinearLayout mLayout2;
    private EditText mEditText2;

    private LinearLayout mLayout3;
    private EditText mEditText3;

    private LinearLayout mLayout4;
    private EditText mEditText4;

    private static List<String> prereqs1 = new ArrayList<>();
    private static List<String> prereqs2 = new ArrayList<>();
    private static List<String> prereqs3 = new ArrayList<>();
    private static List<String> prereqs4 = new ArrayList<>();


    /*
     * Returns a {List<String>} containing all courseIDs in {CourseAccessor} d
     */
    protected String[] validTags() {
        int n = courses == null ? 0 : courses.size();
        String[] tags = new String[n];
        for (int i = 0; i < n; i++) {
            tags[i] = courses.get(i).getCourseNum();
        }
        return tags;
    }

    // a list of sublists, where each sublist contains a selection of possible prereqs (OR)
    // and each list must be combined in and (ex. (CSE 1122 OR CSE 1121) AND (CSE 1111)
    private static List<List<String>> sampleData() {
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
        String courseNum = ((TextView) findViewById(R.id.addCourseName)).getText().toString();
        int creditHours;
        try {
            creditHours = Integer.parseInt(((TextView) findViewById(R.id.addCourseCredits)).getText().toString());
        } catch (NumberFormatException e) {
            creditHours = 0;
        }
        String informalName = ((TextView) findViewById(R.id.addCourseTitle)).getText().toString();
        List<List<String>> prereqs = new ArrayList<>();

        List<List<String>> prereqStrings = sampleData();
        for (List<String> andList : prereqStrings) {
            List<String> andCourses = new ArrayList<>();
            for (String courseName : andList) {
                Course c = CourseAccessor.getCourseByNumber(courseName);
                if (c == null) {
                    // the database doesn't contain a prereq, add it as a stub
                    c = new Course(courseName);
                    CourseAccessor.addCourse(c);
                }
                andCourses.add(courseName);
            }
            prereqs.add(andCourses);
        }
        CourseAccessor.addCourse(new Course(courseNum, informalName, creditHours, prereqs));
        String term = ((TextView) findViewById(R.id.courseTerm)).getText().toString().toUpperCase();
        aa.addCourseToTerm(courseNum, term);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, validTags());
        AutoCompleteTextView addCourseName = findViewById(R.id.addCourseName);
        addCourseName.setAdapter(adapter);

        Bundle b = getIntent().getExtras();
        final String name = b == null ? "john" : b.getString("name");

        aa = new AccountAccessor(name);

        Button addCourseBtn = findViewById(R.id.addCourse);

        AutoCompleteTextView courseFill = (AutoCompleteTextView) findViewById(R.id.addCourseName);

        addCourseName.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                System.out.println(selected);
                Course c = CourseAccessor.getCourseByNumber(selected);
                TextView addCourseTitle = findViewById(R.id.addCourseTitle);
                TextView addCourseCredits = findViewById(R.id.addCourseCredits);
                addCourseTitle.setText(c.getinformalName());
                addCourseCredits.setText(c.getcreditHours()+"");
            }
        });

        String[] userYear = new String[8];
        userYear[0] = "AU1";
        userYear[1] = "SP1";
        userYear[2] = "AU2";
        userYear[3] = "SP2";
        userYear[4] = "AU3";
        userYear[5] = "SP3";
        userYear[6] = "AU4";
        userYear[7] = "SP4";

        mLayout1 = findViewById(R.id.linearlayout1);
        mEditText1 = findViewById(R.id.prereq1);
        Button mButton1 = findViewById(R.id.addOrPrereq1);
        mButton1.setOnClickListener(onClick1());
        TextView textView1 = new TextView(this);
        textView1.setText("New text");

        mLayout2 = findViewById(R.id.linearlayout2);
        mEditText2 = findViewById(R.id.prereq2);
        Button mButton2 = findViewById(R.id.addOrPrereq2);
        mButton2.setOnClickListener(onClick2());
        TextView textView2 = new TextView(this);
        textView2.setText("New text");

        mLayout3 = findViewById(R.id.linearlayout3);
        mEditText3 = findViewById(R.id.prereq3);
        Button mButton3 = findViewById(R.id.addOrPrereq3);
        mButton3.setOnClickListener(onClick3());
        TextView textView3 = new TextView(this);
        textView3.setText("New text");

        mLayout4 = findViewById(R.id.linearlayout4);
        mEditText4 = findViewById(R.id.prereq4);
        Button mButton4 = findViewById(R.id.addOrPrereq4);
        mButton4.setOnClickListener(onClick4());
        TextView textView4 = new TextView(this);
        textView4.setText("New text");

        addCourseBtn.setOnClickListener(view -> {
            addCourseToDB();
            openActivity1(name);
        });

    }

    public void openActivity1(String name) {
        Intent intent = new Intent(AddCourse.this, MainActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }

    private OnClickListener onClick1() {
        return v -> mLayout1.addView(createNewTextView1(mEditText1.getText().toString()));
    }

    private OnClickListener onClick2() {
        return v -> mLayout2.addView(createNewTextView2(mEditText2.getText().toString()));
    }

    private OnClickListener onClick3() {
        return v -> mLayout3.addView(createNewTextView3(mEditText3.getText().toString()));
    }


    private OnClickListener onClick4() {
        return v -> mLayout4.addView(createNewTextView4(mEditText4.getText().toString()));
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

