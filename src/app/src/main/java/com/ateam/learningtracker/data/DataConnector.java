package com.ateam.learningtracker.data;

import java.util.ArrayList;
import java.util.List;

public class DataConnector {
    public static List<SubjectProgress> getSubjectsProgressInfo() {
        List<SubjectEntity> allSubjects = SubjectEntity.listAll(SubjectEntity.class);
        List<SubjectProgress> subjectProgresses = new ArrayList<SubjectProgress>();

        for (SubjectEntity subject:allSubjects) {
            SubjectProgress sp = new SubjectProgress();
            sp.name = subject.name;
            sp.overallProgress = getSubjectProgress(subject);

            subjectProgresses.add(sp);
        }
        return subjectProgresses;
    }

    private static float getSubjectProgress(SubjectEntity subject) {
        // TODO
        return 0f;
    }


}
