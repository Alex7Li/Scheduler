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
import java.util.List;

public class AddCourse extends AppCompatActivity {
    private static final String TAG = AddCourse.class.getName();

    String[] fruits = {"CSE 2221", "CSE 1223", "CSE 2321", "CSE 2231", "CSE 2421"};

    // get the valid tags for a course
    protected List<String> validTags(){
        Database d = new Database();
        List<String> courseIDs = new ArrayList<>();
        for(Course c: d.getCourseList()){
            courseIDs.add(c.getCourseNum());
        }
        return courseIDs;
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

//        FloatingActionButton fab = findViewById(R.id.toJohnAct);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getCourseList();
//                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                //       .setAction("Action", null).show();
//
//            }
//        });

        Button switchActBtn = (Button) findViewById(R.id.addCourse);

        switchActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("");
            }
        });

        TextView firstClass = findViewById(R.id.firstClass);
        TextView firstSem = findViewById(R.id.firstSem);

//        for (Map.Entry<String, String[]> entry : classNameAndCredit.entrySet()) {
//            firstSem.setText(entry.getKey());
//            firstClass.setText(entry.getValue()[0] + "      " + entry.getValue()[1]);
//        }


    }
}