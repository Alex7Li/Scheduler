package com.example.scheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference db;
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance().getReference();
        getCourseList();
    }

    protected void getCourseList() {
        DatabaseReference courses = db.child("Courses") ;
        ValueEventListener dataReader = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get map of users in datasnapshot
                Log.d(TAG, "GOod");
                System.out.println(dataSnapshot.getKey());
                System.out.println(dataSnapshot.getValue());
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
        //dataReader.
        //db.push("Test");
        //db.setValue("Something");
    }
}
