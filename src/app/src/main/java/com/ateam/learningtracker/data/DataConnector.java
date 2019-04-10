package com.ateam.learningtracker.data;

import android.util.Log;

import com.orm.SugarDb;

import java.util.ArrayList;
import java.util.List;

public class DataConnector {
    public static List<SubjectProgress> getSubjectsProgressInfo() {
        List<SubjectEntity> allSubjects = SubjectEntity.listAll(SubjectEntity.class);
        List<SubjectProgress> subjectProgresses = new ArrayList<>();

        for (SubjectEntity subject:allSubjects) {
            SubjectProgress sp = new SubjectProgress();
            sp.name = subject.name;
            sp.overallProgress = getSubjectProgress(subject);

            subjectProgresses.add(sp);
        }
        return subjectProgresses;
    }

    /**
     * Creates some basic subjects, subsections etc for testing purposes
     */
    public static void initDbData() {
        long subjectsCount = SubjectEntity.count(SubjectEntity.class, "1", null);
        if (subjectsCount != 0) {
            LearningSessionEntity.deleteAll(LearningSessionEntity.class);
            SubsectionEntity.deleteAll(SubsectionEntity.class);
            SubjectEntity.deleteAll(SubjectEntity.class);
        }

        String[] subjects = new String[]{"Maths", "Physics", "English"};

        for (String subject: subjects) {
            SubjectEntity subjectEntity = new SubjectEntity(subject, (int) (Math.random() * 100) );
            subjectEntity.save();

            for (int i = 0; i < 4; i++) {
                SubsectionEntity subsection = new SubsectionEntity(subject + "_subsection" + i, (float) Math.random(), subjectEntity);
                subsection.save();

                int numberOfSessions = (int) (Math.random() * 5);

                for (int j = 0; j < numberOfSessions; j++) {
                    long timestamp = System.currentTimeMillis() / 1000;
                    LearningSessionEntity session = new LearningSessionEntity(timestamp - (int)(Math.random() * 3600 * 5), timestamp, "nope," + j, subsection);
                    session.save();
                }
            }

        }

        List<LearningSessionEntity> allSessions = LearningSessionEntity.listAll(LearningSessionEntity.class);
        for (LearningSessionEntity s:allSessions) {
            Log.v("DATA CONNECTOR", s.note);
        }

    }

    private static float getSubjectProgress(SubjectEntity subject) {
        // TODO
        return 0f;
    }


}
