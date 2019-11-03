package com.example.scheduler;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseNum;
    private int creditHours;
    private String informalName;

    // Note that any boolean expression can be converted into a conjunctive normal form,
    // and AND of ORs - something like (a OR b OR c) AND (d OR e) AND f AND (g or h).
    // In order to take a course, you must have taken at least one course from each list
    // in prereqs.
    private List<List<String>> prereqs;

    /*
     * Single-argument constructor for Course with course number
     * Warning: Sets every other parameter to default values
     */
    Course(String courseNum) {
        this.courseNum = courseNum;
        this.creditHours = 0;
        this.informalName = "";
        this.prereqs = new ArrayList<>();
    }

    /*
     * No-argument constructor for Course
     */
    Course(){
        this.courseNum = "";
        this.creditHours = 0;
        this.informalName = "";
        this.prereqs = new ArrayList<>();
    }

    /*
     * Constructor for Course with all fields as arguments
     */
    Course(String courseNum, String informalName, int creditHours, List<List<String>> prereqs){
        this.courseNum = courseNum;
        this.informalName = informalName;
        this.creditHours = creditHours;
        this.prereqs = prereqs;
    }


    /*
     * Returns {String} this.courseNum
     */
    public String getCourseNum() {
        return this.courseNum;
    }

    /*
     * Returns {int} this.creditHours
     */
    public int getcreditHours() {
        return this.creditHours;
    }

    /*
     * Returns {String} this.informalName
     */
    public String getinformalName() {
        return this.informalName;
    }

    /*
     * Returns {List<List<String>>} prereqs
     */
    public List<List<String>> getPrereqs() {
        return this.prereqs;
    }

    /*
     * Sets {String} this.courseNum equal to courseNum
     */
    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    /*
     * Sets {int} this.creditHours equal to crhrs
     */
    public void setCreditHours(int crhrs) {
        this.creditHours = crhrs;
    }

    /*
     * Sets {String} this.informalName equal to infName
     */
    public void setInformalName(String infName) {
        this.informalName = infName;
    }
}
