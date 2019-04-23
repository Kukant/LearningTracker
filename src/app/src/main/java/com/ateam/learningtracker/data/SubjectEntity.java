package com.ateam.learningtracker.data;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class SubjectEntity extends SugarRecord {
    @Unique
    public String name;
    public int studyTime;
    public boolean active;

    public SubjectEntity(){
    }

    public SubjectEntity(String name, int studyTime){
        this.name = name;
        this.studyTime = studyTime;
        this.active = true;
    }

    public SubjectEntity(String name, int studyTime, boolean active){
        this.name = name;
        this.studyTime = studyTime;
        this.active = active;
    }
}
