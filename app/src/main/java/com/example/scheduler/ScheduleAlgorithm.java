package com.example.scheduler;

import java.util.List;
import java.util.ListIterator;

public class ScheduleAlgorithm {
    List<Course> courses;

    public List<List<String>> findSchedule(List<Course> needed, List<Course> taken) {

        return null;
    }

    private boolean hasPrereqs(Course c, List<Course> taken) {
        boolean isOk = true;
        List<List<String>> s = c.getPrereqs();
        for(Course finished: taken){

        }
        return true;
    }

}
