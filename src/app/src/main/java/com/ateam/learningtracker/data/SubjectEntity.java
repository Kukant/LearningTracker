package com.ateam.learningtracker.data;

import com.orm.SugarRecord;

public class SubjectEntity extends SugarRecord<SubjectEntity> {
    public String name;
    public int studyTime;

    public SubjectEntity(){
    }

    public SubjectEntity(String name, int studyTime){
        this.name = name;
        this.studyTime = studyTime;
    }
}
