package com.example.scheduler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AccountAccessor {
    private DatabaseReference db;
    private Map<String, List<String>> coursesByTerm;
    private int startYear;
    private String accountName;
    private MainActivity ma;
    /*
     * Constructor for account with {String} accountName
     */
    AccountAccessor(String accountName) {
        db = FirebaseDatabase.getInstance().getReference();
        this.accountName = accountName;
        updateCourseList();

    }
    /*
     * Not good constructor for when in mainactivity because
     * race conditions
     */
    AccountAccessor(String accountName, int year, MainActivity ma) {
        db = FirebaseDatabase.getInstance().getReference();
        this.ma = ma;
        this.startYear = year;
        this.accountName = accountName;
        updateCourseList();

    }

    /*
     * Set {String} this.accountName equal to accountName
     */
    public void setAccountName(String accountName){
        this.accountName = accountName;
    }

    public int getStartYear(){
        return startYear;
    }
    /*
     * Return {List<Course>} this.accountCourses
     */
    public Map<String, List<String>> getAccountCourses(){
        return new TreeMap<>(this.coursesByTerm);
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

    void makeAccount(){
        int startYear = getStartYear();
        String prefix = accountName + "/" + accountName + "Courses/";
        Map<String, Object> data = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            data.put(prefix + "AU" + (startYear + i), "");
            data.put(prefix + "SP" + (startYear + i), "");
        }
        data.put(accountName + "/" + "StartYear", startYear);
        db.updateChildren(data);
        if(ma!=null) {
            ma.display();
        }
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

        DatabaseReference yearRef = db.child(accountName);
        ValueEventListener yearReader = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getYearFromSnapshot(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //handle databaseError
                System.out.println("UH OH");
            }
        };
        yearRef.addListenerForSingleValueEvent(yearReader);

        return coursesByTerm;
    }

    private int getYearFromSnapshot(DataSnapshot dataSnapshot){
        if(dataSnapshot.exists()) {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                if (ds.getKey().equals("StartYear")) {
                    try {
                        this.startYear = Integer.parseInt(dataSnapshot.getValue().toString());
                    } catch (NumberFormatException e) {
                        System.out.println("Error Account accessor");
                        this.startYear = 42;
                    }
                }
            }
<<<<<<< HEAD
            if(ma!=null){
=======
            if(ma!=null) {
>>>>>>> 0103b0b9dd8abb35197618ed79a155d7dee0a48f
                ma.display();
            }
        }else{
            makeAccount();
        }
        return this.startYear;
    }
    /*
     * For use in getCourseList
     * Populates {List<List<String>>} this.coursesByTerm with {List<String>} terms from db
     */
    private void getListOfTermsFromDatabase(DataSnapshot dataSnapshot) {
        this.coursesByTerm = new HashMap();
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            if(ds.getValue().equals("")){
                continue;
            }
            System.out.println(ds.getValue());
            if(ds.getValue() instanceof String){
                List<String> courseList = new ArrayList<>();
                courseList.add(ds.getValue().toString());
                this.coursesByTerm.put(ds.getKey(), courseList);
            }else if(ds.getValue() instanceof HashMap) {
                HashMap termCourses = (HashMap)ds.getValue();
                List<String> courses = new ArrayList<String>();
                courses.addAll(termCourses.values());
                this.coursesByTerm.put(ds.getKey(), courses);
            }else{
                System.out.println(ds.getValue());
                throw new AssertionError("Bad type of ds get value");
            }
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