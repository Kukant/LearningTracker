package com.ateam.learningtracker.data;

import com.orm.SugarRecord;

public class SubsectionEntity extends SugarRecord {
    public String name;
    public float importancy;
    public SubjectEntity subject;
    public boolean subActive;

    public SubsectionEntity(){
    }

    public SubsectionEntity(String name, float importancy, SubjectEntity subject){
        this.name = name;
        this.importancy = importancy;
        this.subject = subject;
        this.subActive = subActive;
    }
}
