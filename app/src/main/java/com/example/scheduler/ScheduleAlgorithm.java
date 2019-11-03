package com.example.scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScheduleAlgorithm {
    /**
     * @param needed:         A list of courses that are required for the semester.
     * @param taken:          For each semester, a list of the course numbers.
     * @param maxCreditHours: The maxmimum number of credit hours to take each semester
     * @param startingAt:     The semester to start planning from, in terms of the index in taken.
     * @return A list in the same format as taken: The outer list is each semester, and the semesters
     * are ordered starting from where the taken list begins. Each semester contains a list of classes
     * for that semester by their string course number. It is guaranteed that classes listed in the taken
     * array do not move, and that no classes are added before the year startingAt.
     */
    public List<List<String>> findSchedule(List<Course> needed, List<List<String>> taken, int maxCreditHours, CourseAccessor ca, int startingAt) {
        List<Course> need = new ArrayList<>();
        Collections.copy(needed, need);
        Set<String> takenCourses = new HashSet<>();
        // TODO: Add the following heuristic: try to take courses that start long 'chains
        // of prerequisite courses' rather than adding courses in random order. This is done
        // by sorting the need list
        List<List<String>> recommendations = new ArrayList<>();
        for (int i = 0; i < taken.size() || !need.isEmpty(); i++) {
            List<String> semesterPlan = new ArrayList<>();
            int creditsUsed = 0;
            if(i<taken.size()) {
                for (String course : taken.get(i)) {
                    semesterPlan.add(course);
                    takenCourses.add(course);
                    creditsUsed += ca.getCourseByNumber(course).getcreditHours();
                }
            }
            if(i>=startingAt) {
                while (creditsUsed < maxCreditHours && !need.isEmpty()) {
                    Course next = need.remove(need.size() - 1);
                    if (hasPrereqs(next, takenCourses)) {
                        semesterPlan.add(next.getCourseNum());
                        takenCourses.add(next.getCourseNum());
                        creditsUsed += next.getcreditHours();
                    }
                }
            }
            recommendations.add(semesterPlan);
        }
        return recommendations;
    }


    // check if a person who has taken a list of courses has
    // the prereqs for a given course.
    private boolean hasPrereqs(Course c, Set<String> taken) {
        boolean isOk = true;
        List<List<String>> s = c.getPrereqs();
        for (List<String> neededSet : s) {
            boolean haveCourseFromCategory = false;
            for (String possibility : neededSet) {
                if (taken.contains(possibility)) {
                    haveCourseFromCategory = true;
                    break;
                }
            }
            if (!haveCourseFromCategory) {
                isOk = false;
                break;
            }
        }
        return true;
    }

}
