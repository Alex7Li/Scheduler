package com.example.scheduler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

class CourseAccessor {
    private DatabaseReference db;
    private List<Course> courses;

    CourseAccessor() {
        db = FirebaseDatabase.getInstance().getReference();
        updateCourseList();
    }

    /*
      Add a course to the database
     */
    void addCourse(Course c) {
        DatabaseReference courses = db.child("Courses");
        DatabaseReference coursesChild = courses.push();
        coursesChild.setValue(c);
        updateCourseList();
    }



    /*
       Get a course by course number, if it's not in the database, return null.
    */
    Course getCourseByNumber(String courseNum) {
        for (Course c : courses) {
            if (c.getCourseNum().equals(courseNum)) {
                return c;
            }
        }
        return null;
    }

    /*
     * Returns a {List<Course>} courseList populated with courses from this.db.child("Courses")
     */
    List<Course> updateCourseList() {
        DatabaseReference courseRef = db.child("Courses");
        ValueEventListener dataReader = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get map of users in datasnapshot
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


    /*
     * For use in getCourseList
     * Populates this.courses with courses from db
     */
    private void getListOfCoursesFromDatabase(DataSnapshot dataSnapshot) {
        courses = new ArrayList<>();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
             Course courseInfo = ds.getValue(Course.class);

             String courseNum = courseInfo.getCourseNum();
             int creditHours = courseInfo.getcreditHours();
             String informalName = courseInfo.getinformalName();
             List<List<String>> prereqs = courseInfo.getPrereqs();

             Course newCourse = new Course(courseNum, informalName, creditHours, prereqs);
             this.courses.add(newCourse);
        }
    }
}
