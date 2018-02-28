package com.georgebrown.comp3074.a2100984638;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.georgebrown.comp3074.a2100984638.HospitalContract.*;

/**
 * Created by Owner on 11/3/2017.
 */

public class PatientInfoActivity extends AppCompatActivity {
    String intentUserId;
    String intentUserFullName;
    String intentId;
    /** Database helper that will provide us access to the database*/
    private HospitalDbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientinfo);

        Intent intent = getIntent();
        //Get User id and name from the intent
        intentUserId = intent.getStringExtra("userId");//user id
        intentUserFullName = intent.getStringExtra("userFullName");//user full name
        //Get patient id and name from the intent
        intentId = intent.getStringExtra("patientId");//patient id

        //display signed in user
        TextView userView = (TextView)findViewById(R.id.txtUser);
        userView.setText(intentUserFullName);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        dbHelper = new HospitalDbHelper(this);

        displayPatientInfo();
    }

    //when click logout button, the page change to Login
    public void onClickLogout(View v){
        Toast.makeText(PatientInfoActivity.this,"You loged out successfully.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(PatientInfoActivity.this, MainActivity.class);
        startActivity(intent);
    }

    //
    private void displayPatientInfo(){

        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        String[] projection = {
                PatientEntry._ID,
                PatientEntry.COLUMN_PATIENT_FIRSTNAME,
                PatientEntry.COLUMN_PATIENT_LASTNAME,
                PatientEntry.COLUMN_PATIENT_DEPARTMENT,
                PatientEntry.COLUMN_PATIENT_DOCTORID,
                PatientEntry.COLUMN_PATIENT_ROOM
        };
        // Filter results WHERE "ID" = 'intentId'
        String selection = PatientEntry._ID + " = ?";
        String[] selectionArgs = { intentId };

        Cursor cursor = db.query(
                PatientEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        /*
        find all relevant views
         */
        TextView idText = (TextView) findViewById(R.id.txtPatientId);
        TextView fNameText = (TextView) findViewById(R.id.txtFirstName);
        TextView lNameText = (TextView) findViewById(R.id.txtLastName);
        TextView departmentText = (TextView) findViewById(R.id.txtDepartment);
        TextView doctorText = (TextView) findViewById(R.id.txtDoctor);
        TextView roomText = (TextView) findViewById(R.id.txtRoom);

        try{
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(PatientEntry._ID);
            int firstNameColumnIndex = cursor.getColumnIndex(PatientEntry.COLUMN_PATIENT_FIRSTNAME);
            int lastNameColumnIndex = cursor.getColumnIndex(PatientEntry.COLUMN_PATIENT_LASTNAME);
            int departmentColumnIndex = cursor.getColumnIndex(PatientEntry.COLUMN_PATIENT_DEPARTMENT);
            int doctorColumnIndex = cursor.getColumnIndex(PatientEntry.COLUMN_PATIENT_DOCTORID);
            int roomColumnIndex = cursor.getColumnIndex(PatientEntry.COLUMN_PATIENT_ROOM);

            cursor.moveToFirst();

            //get value from database
            String id = Integer.toString(cursor.getInt(idColumnIndex));
            String FName = cursor.getString(firstNameColumnIndex);
            String LName = cursor.getString(lastNameColumnIndex);
            String department = cursor.getString(departmentColumnIndex);
            String doctor = cursor.getString(doctorColumnIndex);
            String room = cursor.getString(roomColumnIndex);

            //display value from DB to view
            idText.setText(id);
            fNameText.setText(FName);
            lNameText.setText(LName);
            departmentText.setText(department);
            doctorText.setText(findDoctorName(doctor));
            roomText.setText(room);

        }catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        } finally {
            cursor.close();
        }
    }

    private String findDoctorName(String doctorId){
        String doctorFullName = null;
        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        String[] projection = {
                DoctorEntry._ID,
                DoctorEntry.COLUMN_DOCTOR_FIRSTNAME,
                DoctorEntry.COLUMN_DOCTOR_LASTNAME
        };

        // Filter results WHERE "ID" = 'doctorId'
        String selection = DoctorEntry._ID + " = ?";
        String[] selectionArgs = { doctorId };

        Cursor cursor = db.query(
                DoctorEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        try{
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(DoctorEntry._ID);
            int firstNameColumnIndex = cursor.getColumnIndex(DoctorEntry.COLUMN_DOCTOR_FIRSTNAME);
            int lastNameColumnIndex = cursor.getColumnIndex(DoctorEntry.COLUMN_DOCTOR_LASTNAME);

            cursor.moveToFirst();

            //get value from database
            String id = Integer.toString(cursor.getInt(idColumnIndex));
            String FName = cursor.getString(firstNameColumnIndex);
            String LName = cursor.getString(lastNameColumnIndex);

            doctorFullName = "Dr. " + FName + " " + LName;

        }catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        } finally {
            cursor.close();
        }

        return doctorFullName;
    }
}
