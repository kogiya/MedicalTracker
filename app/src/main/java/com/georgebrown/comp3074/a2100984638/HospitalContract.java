package com.georgebrown.comp3074.a2100984638;

import android.provider.BaseColumns;

/**
 * Created by Owner on 11/4/2017.
 */

public class HospitalContract {
    private HospitalContract(){}

    public static abstract class PatientEntry implements BaseColumns{
        public static final String TABLE_NAME = "patient";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PATIENT_FIRSTNAME = "firstName";
        public final static String COLUMN_PATIENT_LASTNAME = "lastName";
        public final static String COLUMN_PATIENT_DEPARTMENT = "department";
        public final static String COLUMN_PATIENT_DOCTORID = "doctorId";
        public final static String COLUMN_PATIENT_ROOM = "room";
    }

    public static abstract class DoctorEntry implements BaseColumns{
        public static final String TABLE_NAME = "doctor";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_DOCTOR_ID = "doctorId";
        public final static String COLUMN_DOCTOR_FIRSTNAME = "firstName";
        public final static String COLUMN_DOCTOR_LASTNAME = "lastName";
        public final static String COLUMN_DOCTOR_DEPARTMENT = "department";
    }

    public static abstract class NurseEntry implements BaseColumns{
        public static final String TABLE_NAME = "nurse";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NURSE_FIRSTNAME = "firstName";
        public final static String COLUMN_NURSE_LASTNAME = "lastName";
        public final static String COLUMN_NURSE_DEPARTMENT = "department";
    }

    public static abstract class TestEntry implements BaseColumns{
        public static final String TABLE_NAME = "test";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TEST_PATIENTID = "patientId";
        public final static String COLUMN_TEST_BT = "bt";
        public final static String COLUMN_TEST_BPH = "bph";
        public final static String COLUMN_TEST_BPL = "bpl";
        public final static String COLUMN_TEST_HR = "hr";
        public final static String COLUMN_TEST_RR = "rr";
        public final static String COLUMN_TEST_URINE = "urine";
        public final static String COLUMN_TEST_TESTER = "testerId";
        public final static String COLUMN_TEST_TIME = "time";

    }
}
