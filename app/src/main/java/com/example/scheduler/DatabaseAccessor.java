package com.example.scheduler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DatabaseAccessor {
    private DatabaseReference db;
    private List<Course> courses;

    DatabaseAccessor() {
        db = FirebaseDatabase.getInstance().getReference();
    }

    /*
      Add a course to the database
     */
    void addCourse(Course c) {
        DatabaseReference courses = db.child("Courses");
        DatabaseReference coursesChild = courses.push();
        coursesChild.setValue(c);
    }


    /*
       Get a course by course number, if it's not in the database, return null.
    */
    protected Course getCourseByNumber(String courseNum) {
        getCourseList();
        for (Course c : courses) {
            if (c.getCourseNum().equals(courseNum)) {
                return c;
            }
        }
        return null;
    }

    protected List<Course> getCourseList() {
        DatabaseReference courseRef = db.child("Courses");
        ValueEventListener dataReader = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get map of users in datasnapshot
                System.out.println(dataSnapshot.getKey());
                System.out.println(dataSnapshot.getValue());
                getListOfCoursesFromDatabase(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //handle databaseError
                System.out.println("UH OH");
            }
        };
        courseRef.addListenerForSingleValueEvent(dataReader);
        return courses;
    }


    protected void getListOfCoursesFromDatabase(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
             Course courseInfo = ds.getValue(Course.class);

             String courseNum = courseInfo.getCourseNum();
             int creditHours = courseInfo.getcreditHours();
             String informalName = courseInfo.getinformalName();
             List<List<String>> prereqs = courseInfo.getPrereqs();

             Course newCourse = new Course(courseNum, informalName, creditHours, prereqs);
             this.courses.add(courseInfo);
        }
    }
}
