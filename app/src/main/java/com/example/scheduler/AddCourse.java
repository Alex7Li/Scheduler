package com.example.scheduler;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class AddCourse extends AppCompatActivity {
    private DatabaseReference db;
    private static final String TAG = AddCourse.class.getName();

    String[] fruits = {"CSE 2221", "CSE 1223", "CSE 2321", "CSE 2231", "CSE 2421"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = FirebaseDatabase.getInstance().getReference();

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

}