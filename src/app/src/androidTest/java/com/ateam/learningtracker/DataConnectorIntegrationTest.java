package com.ateam.learningtracker;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.ateam.learningtracker.data.DataConnector;
import com.ateam.learningtracker.data.SubjectEntity;
import com.ateam.learningtracker.data.SubsectionEntity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DataConnectorIntegrationTest {
    @Test
    public void testInitDbData() {
        DataConnector.initDbData();

        assertNotEquals("Database was not initialized.", 0, SubjectEntity.count(SubjectEntity.class, "", new String[]{}));
    }

    @Test
    public void testGetSubjectByName() {
        String subjectName = "test Subject";
        SubjectEntity s = new SubjectEntity("test Subject", 42);
        s.save();

        assertNotNull(DataConnector.getSubjectByName(subjectName));
    }

    @Test
    public void testGetSubsectionByName() {
        SubjectEntity s = new SubjectEntity("test Subject", 42);
        s.save();

        SubsectionEntity sub = new SubsectionEntity("test subsection", 0.5f, s);
        sub.save();

        assertNotNull(DataConnector.getSubsectionByName("test subsection", s));
    }

    @Test
    public void testGetSubjectsProgress() {
        DataConnector.initDbData();
        for(SubjectEntity s: SubsectionEntity.listAll(SubjectEntity.class)) {
            s.active = true;
            s.save();
        }

        assertTrue(DataConnector.getSubjectsProgressInfo().size() > 0);
    }
}
