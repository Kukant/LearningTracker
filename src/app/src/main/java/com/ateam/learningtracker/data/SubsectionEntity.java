package com.ateam.learningtracker.data;

import com.orm.SugarRecord;

public class SubsectionEntity extends SugarRecord<SubsectionEntity> {
    public String name;
    public float importancy;
    public SubjectEntity subject;

    public SubsectionEntity(){
    }

    public SubsectionEntity(String name, float importancy, SubjectEntity subject){
        this.name = name;
        this.importancy = importancy;
        this.subject = subject;
    }
}
