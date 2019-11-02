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

    Course(String courseNum) {
        this.courseNum = courseNum;
        this.creditHours = 0;
        this.informalName = "";
        this.prereqs = new ArrayList<>();
    }

    Course(String courseNum, String informalName, int creditHours, List<List<String>> prereqs) {
        this.courseNum = courseNum;
        this.informalName = informalName;
        this.creditHours = creditHours;
        this.prereqs = prereqs;
    }


    public String getCourseNum() {
        return this.courseNum;
    }

    public int getcreditHours() {
        return this.creditHours;
    }

    public String getinformalName() {
        return this.informalName;
    }

    public List<List<String>> getPrereqs() {
        return this.prereqs;
    }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }

    public void setCreditHours(int crhrs) {
        this.creditHours = crhrs;
    }

    public void setInformalName(String infName) {
        this.informalName = infName;
    }
}
