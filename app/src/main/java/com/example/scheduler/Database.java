package com.example.scheduler;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashSet;
import java.util.List;

public class Database {
    private DatabaseReference db;
    private List<Course> courses;
    public Database(){
        db = FirebaseDatabase.getInstance().getReference();
    }
    protected void addCourse() {
        DatabaseReference courses = db.child("Courses");
        Course c = new Course("CSE1113", "Programming for Nerds", 3, new HashSet<String>());
        DatabaseReference coursesChild = courses.push();
        coursesChild.setValue(c);

        //courses.SetValueAsync(c);
    }

    protected void getCourseList() {
        DatabaseReference courses = db.child("Courses") ;
        ValueEventListener dataReader = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get map of users in datasnapshot
                System.out.println(dataSnapshot.getKey());
                System.out.println(dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //handle databaseError
                System.out.println("UH OH");
            }
        };
        courses.addListenerForSingleValueEvent(dataReader);
    }
}
