package com.example.scheduler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AccountAccessor {
    private DatabaseReference db;
    private TreeMap<String, List<String>> coursesByTerm;
    private String accountName;

    /*
     * Constructor for account with {String} accountName
     */
    AccountAccessor(String accountName) {
        db = FirebaseDatabase.getInstance().getReference();
        updateCourseList();
        this.accountName = accountName;
    }

    /*
     * Set {String} this.accountName equal to accountName
     */
    public void setAccountName(String accountName){
        this.accountName = accountName;
    }

    /*
     * Return {List<Course>} this.accountCourses
     */
    public Map<String, List<String>> getAccountCourses(){
        return this.coursesByTerm;
    }

    /*
     * Add a course to specified term in account's course history
     * (From Database)
     */
    void addCourseToTerm(String c, String term) {
        DatabaseReference addToTerm = db.child(accountName).child(accountName + "Courses").child(term);
        DatabaseReference coursesChild = addToTerm.push();
        coursesChild.setValue(c);
        updateCourseList();
    }

    /*
     * Returns a {List<Course>} courseList populated with courses from this.db.child(AccountName + "Courses")
     */
    Map<String, List<String>> updateCourseList() {
        DatabaseReference courseRef = db.child(accountName).child(accountName + "Courses");
        ValueEventListener dataReader = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Get map of users in datasnapshot
                getListOfTermsFromDatabase(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //handle databaseError
                System.out.println("UH OH");
            }
        };
        courseRef.addListenerForSingleValueEvent(dataReader);
        return coursesByTerm;
    }

    /*
     * For use in getCourseList
     * Populates {List<List<String>>} this.coursesByTerm with {List<String>} terms from db
     */
    private void getListOfTermsFromDatabase(DataSnapshot dataSnapshot) {
        this.coursesByTerm = new TreeMap<>(termComparator);
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            TreeMap<String, String> termCourses = ds.getValue(TreeMap.class);
            List<String> courses = new ArrayList<String>();
            courses.addAll(termCourses.values());
            this.coursesByTerm.put(ds.getKey(), courses);
        }
    }

    private static Comparator<String> termComparator = (o1, o2) -> {
        if(o1.substring(2,4).equals(o2.substring(2,4))){
            return o1.substring(0,2).compareTo(o2.substring(0,2));
        }else{
            return o1.substring(2,4).compareTo(o2.substring(2,4));
        }
    };

    /*
     * For use in getCourseList
     * Populates
     */
}