package com.example.scheduler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AccountAccessor {
    private DatabaseReference db;
    private TreeMap<String, List<String>> termsAndRelatedCourses;
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
    public TreeMap<String, List<String>> getAccountCourses(){
        return this.termsAndRelatedCourses;
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

        return termsAndRelatedCourses;
    }

    /*
     * For use in getCourseList
     * Populates {List<List<String>>} this.termsAndRelatedCourses with {List<String>} terms from db
     */
    private void getListOfTermsFromDatabase(DataSnapshot dataSnapshot) {
        this.termsAndRelatedCourses = new TreeMap<>();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            HashMap.Entry<String, List<String>> termAndRelatedCourses = ds.getValue(TreeMap.Entry.class);

            this.termsAndRelatedCourses.put(termAndRelatedCourses.getKey(), termAndRelatedCourses.getValue());
        }
    }

    /*
     * For use in getCourseList
     * Populates
     */
}
