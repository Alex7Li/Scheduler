package com.example.scheduler;

import java.util.HashSet;

public class Course {
    private String courseNum;
    private int creditHours;
    private String informalName;
    private HashSet<String> prereqs;

    Course(String courseNum){
        this.courseNum = courseNum;
        this.creditHours = 0;
        this.informalName = "";
        this.prereqs = new HashSet<String>();
    }
    Course(String courseNum, String informalName, int creditHours, HashSet<String> prereqs){
        this.courseNum = courseNum;
        this.informalName = "";
        this.creditHours = 0;
        this.prereqs = new HashSet<>();
    }

    public String getCourseNum() {
        return this.courseNum;
    }
    public int getcreditHours() {
        return this.creditHours;
    }
    public String getinformalName() { return this.informalName; }
    public HashSet<String> getPrereqs() { return this.prereqs; }

    public void setCourseNum(String courseNum) {
        this.courseNum = courseNum;
    }
    public void setCreditHours(int crhrs) { this.creditHours = crhrs; }
    public void setInformalName (String infName) { this.informalName = infName; }
}
