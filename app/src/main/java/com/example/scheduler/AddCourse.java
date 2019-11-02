package com.example.scheduler;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

<<<<<<< HEAD
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
=======
import java.util.ArrayList;
import java.util.List;
>>>>>>> 5ba7282f343bde226a63f664bc8cd4a4151bb32c

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

        Button fab = findViewById(R.id.addCourse);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCourseList();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();

            }
        });

        Button addCourseBtn = (Button) findViewById(R.id.addCourse);
        TextView addCourseName = findViewById(R.id.addCourseName);
        TextView addCredit = findViewById(R.id.courseCredits);
        TextView addTerm = findViewById(R.id.courseTerm);


        TextView firstClass = findViewById(R.id.firstClass);
        TextView firstSem = findViewById(R.id.firstSem);

        Map<String, String[]> classNameAndCredit = new HashMap<>();

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] x = new String[2];
                x[0] = addCourseName.getText().toString();
                x[1] = addCredit.getText().toString();

                classNameAndCredit.put(addTerm.getText().toString(), x);

                for (Map.Entry<String, String[]> entry : classNameAndCredit.entrySet()) {
                    firstSem.setText(entry.getKey());
                    firstClass.setText(entry.getValue()[0] + "      " + entry.getValue()[1]);
                }
            }
        });
    }
<<<<<<< HEAD


    protected void getCourseList() {
        DatabaseReference courses = db.child("Courses");
        ValueEventListener dataReader = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get map of users in datasnapshot
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "GOod");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //handle databaseError
                System.out.println("UH OH");
            }
        };
        courses.getDatabase();
        Log.d(TAG, "Test");
        //FirebaseDatabase.getInstance().getReference().child("hello").addValueEventListener();
        courses.addValueEventListener(dataReader);
        //courses.addListenerForSingleValueEvent(dataReader);
        //courses.setValue("Hello");
        // dataReader.
        //db.push("Test");
        //db.setValue("Something");
    }
=======
>>>>>>> 5ba7282f343bde226a63f664bc8cd4a4151bb32c
}