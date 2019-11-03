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
    private TreeMap<String, List<String>> coursesByTerm;
    int startYear;
    private String accountName;
    private MainActivity ma;
    /*
     * Constructor for account with {String} accountName
     */
    AccountAccessor(String accountName, MainActivity ma) {
        db = FirebaseDatabase.getInstance().getReference();
        this.ma = ma;
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

    void makeAccount(){
        int startYear = 18;
        Map<String, Object> semesterData = new TreeMap<>(termComparator);
        for (int i = 0; i < 4; i++) {
            semesterData.put("AU" + (startYear + i), new HashMap<String, String>());
            semesterData.put("SP" + (startYear + i), new HashMap<String, String>());
        };
        Map<String, Object> accountCoursesAndYear = new HashMap<>();
        accountCoursesAndYear.put("StartYear", startYear);
        accountCoursesAndYear.put(accountName + "Courses", semesterData);
        Map<String, Object> profile = new HashMap<>();
        profile.put(accountName, accountCoursesAndYear);
        db.push().setValue(profile);
        ma.display();
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