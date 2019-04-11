package com.ateam.learningtracker.data;

import com.orm.SugarRecord;

public class LearningSessionEntity extends SugarRecord {
    public long timeStart;
    public long timeEnd;
    public String note;
    public SubsectionEntity subsection;

    public LearningSessionEntity(){
    }

    public LearningSessionEntity(long timeStart, long timeEnd, String note, SubsectionEntity subsection){
        this.timeEnd = timeEnd;
        this.timeStart = timeStart;
        this.note = note;
        this.subsection = subsection;
    }
}
