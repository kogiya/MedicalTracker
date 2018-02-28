package com.georgebrown.comp3074.a2100984638;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.georgebrown.comp3074.a2100984638.HospitalContract.*;

/**
 * Created by Owner on 11/4/2017.
 */

public class HospitalDbHelper extends SQLiteOpenHelper{
    /*
    create constants
     */
    //name of database file
    private static final String DB_NAME = "hospital.db";
    //version
    private static final int DB_VERSION = 1;

    /*
    constractor
     */
    public HospitalDbHelper(Context context){super(context, DB_NAME, null, DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {

        //SQL create Patient table
        String SQL_CREATE_PATIENT_TABLE = "CREATE TABLE "+ PatientEntry.TABLE_NAME + "("+
                PatientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                PatientEntry.COLUMN_PATIENT_FIRSTNAME + " TEXT NOT NULL, " +
                PatientEntry.COLUMN_PATIENT_LASTNAME + " TEXT NOT NULL, " +
                PatientEntry.COLUMN_PATIENT_DEPARTMENT + " TEXT, " +
                PatientEntry.COLUMN_PATIENT_DOCTORID + " TEXT, " +
                PatientEntry.COLUMN_PATIENT_ROOM + " TEXT, " +
                "FOREIGN KEY (" + PatientEntry.COLUMN_PATIENT_DOCTORID + ") REFERENCES " + DoctorEntry.TABLE_NAME + "(" + DoctorEntry._ID + "));";

        //execute the SQL statement
        db.execSQL(SQL_CREATE_PATIENT_TABLE);


        //SQL create Doctor table
        String SQL_CREATE_DOCTOR_TABLE = "CREATE TABLE "+ DoctorEntry.TABLE_NAME + "("+
                DoctorEntry._ID + " TEXT NOT NULL, " +
                DoctorEntry.COLUMN_DOCTOR_FIRSTNAME + " TEXT NOT NULL, " +
                DoctorEntry.COLUMN_DOCTOR_LASTNAME + " TEXT NOT NULL, " +
                DoctorEntry.COLUMN_DOCTOR_DEPARTMENT + " TEXT);";

        //execute the SQL statement
        db.execSQL(SQL_CREATE_DOCTOR_TABLE);

        //insert doctors
        insertDoctor(db, "d0001", "Miranda", "Bailey", "surgery");
        insertDoctor(db, "d0002", "Owen", "Hunt", "internal medicine");
        insertDoctor(db, "d0003", "Derek", "Shepherd", "pediatrics");
        insertDoctor(db, "d0004", "Richard", "Webber", "obstetrics");
        insertDoctor(db, "d0005", "Meredith", "Grey", "gynecology");


        //SQL create Nurse table
        String SQL_CREATE_NURSE_TABLE = "CREATE TABLE "+ NurseEntry.TABLE_NAME + "("+
                NurseEntry._ID + " TEXT PRIMARY KEY," +
                NurseEntry.COLUMN_NURSE_FIRSTNAME + " TEXT NOT NULL, " +
                NurseEntry.COLUMN_NURSE_LASTNAME + " TEXT NOT NULL, " +
                NurseEntry.COLUMN_NURSE_DEPARTMENT + " TEXT);";

        //execute the SQL statement
        db.execSQL(SQL_CREATE_NURSE_TABLE);

        //insert nurses
        insertNurse(db, "n0001", "Heleh", "Adams", "surgery");
        insertNurse(db, "n0002", "Sue", "Batron", "internal medicine");
        insertNurse(db, "n0003", "Christine", "Chapel", "pediatrics");
        insertNurse(db, "n0004", "Shirley", "Daniels", "obstetrics");
        insertNurse(db, "n0005", "Jane", "Foster", "gynecology");


        //SQL create Test table
        String SQL_CREATE_TEST_TABLE = "CREATE TABLE "+ TestEntry.TABLE_NAME + "("+
                TestEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TestEntry.COLUMN_TEST_PATIENTID + " INTEGER NOT NULL, " +
                TestEntry.COLUMN_TEST_BT + " TEXT, " +
                TestEntry.COLUMN_TEST_BPH + " TEXT, " +
                TestEntry.COLUMN_TEST_BPL + " TEXT, " +
                TestEntry.COLUMN_TEST_HR + " TEXT, " +
                TestEntry.COLUMN_TEST_RR + " TEXT, " +
                TestEntry.COLUMN_TEST_URINE + " TEXT, " +
                TestEntry.COLUMN_TEST_TESTER + " TEXT NOT NULL, " +
                TestEntry.COLUMN_TEST_TIME + " TEXT NOT NULL);";//+
                //"FOREIGN KEY (" + TestEntry.COLUMN_TEST_PATIENTID + ") REFERENCES " + PatientEntry.TABLE_NAME + "(" + PatientEntry._ID + "));";

        //execute the SQL statement
        db.execSQL(SQL_CREATE_TEST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void insertDoctor(SQLiteDatabase db,
                                    String id,
                                    String firstName,
                                     String lastName,
                                     String department) {
        ContentValues doctorValues = new ContentValues();
        doctorValues.put(DoctorEntry._ID,id);
        doctorValues.put(DoctorEntry.COLUMN_DOCTOR_FIRSTNAME,firstName);
        doctorValues.put(DoctorEntry.COLUMN_DOCTOR_LASTNAME, lastName);
        doctorValues.put(DoctorEntry.COLUMN_DOCTOR_DEPARTMENT, department);
        db.insert(DoctorEntry.TABLE_NAME, null, doctorValues);
    }

    private static void insertNurse(SQLiteDatabase db,
                                     String id,
                                     String firstName,
                                     String lastName,
                                     String department) {
        ContentValues nurseValues = new ContentValues();
        nurseValues.put(NurseEntry._ID,id);
        nurseValues.put(NurseEntry.COLUMN_NURSE_FIRSTNAME,firstName);
        nurseValues.put(NurseEntry.COLUMN_NURSE_LASTNAME, lastName);
        nurseValues.put(NurseEntry.COLUMN_NURSE_DEPARTMENT, department);
        db.insert(NurseEntry.TABLE_NAME, null, nurseValues);
    }
}
