package com.georgebrown.comp3074.a2100984638;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static com.georgebrown.comp3074.a2100984638.HospitalContract.*;

/**
 * Created by Owner on 11/3/2017.
 */

public class ViewTestDataActivity extends AppCompatActivity {
    String intentUserId;
    String intentUserFullName;
    String intentPatientId;
    String intentPatientFullName;

    /** Database helper that will provide us access to the database*/
    private HospitalDbHelper dbHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewtestdata);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        dbHelper = new HospitalDbHelper(this);

        Intent intent = getIntent();
        //Get User id and name from the intent
        intentUserId = intent.getStringExtra("userId");//user id
        intentUserFullName = intent.getStringExtra("userFullName");//user full name
        //Get patient id and name from the intent
        intentPatientId = intent.getStringExtra("patientId");//patient id
        intentPatientFullName = intent.getStringExtra("patientFullName");//patient full name
        //Get test data from the intent
        String intentTestId = intent.getStringExtra("testId");
        String intentBT = intent.getStringExtra("bt");
        String intentBPH = intent.getStringExtra("bph");
        String intentBPL = intent.getStringExtra("bpl");
        String intentHR = intent.getStringExtra("hr");
        String intentRR = intent.getStringExtra("rr");
        String intentUrine = intent.getStringExtra("urine");
        String intentTester = intent.getStringExtra("tester");
        String intentTime = intent.getStringExtra("time");


        //display signed in user
        TextView userView = (TextView)findViewById(R.id.txtUser);
        userView.setText(intentUserFullName);
        //Display the patient id
        TextView idView = (TextView)findViewById(R.id.txtPatientId);
        idView.setText(intentPatientId);//id
        //Display the patient name
        TextView nameView = (TextView)findViewById(R.id.txtPatientName);
        nameView.setText(intentPatientFullName);
        //display test data
        TextView testIdView = (TextView)findViewById(R.id.txtTestId);
        testIdView.setText(intentTestId);
        TextView btView = (TextView)findViewById(R.id.txtBT);
        btView.setText(intentBT);
        TextView bphView = (TextView)findViewById(R.id.txtBPH);
        bphView.setText(intentBPH);
        TextView bplView = (TextView)findViewById(R.id.txtBPL);
        bplView.setText(intentBPL);
        TextView hrView = (TextView)findViewById(R.id.txtHR);
        hrView.setText(intentHR);
        TextView rrView = (TextView)findViewById(R.id.txtRR);
        rrView.setText(intentRR);
        TextView urineView = (TextView)findViewById(R.id.txtUrine);
        urineView.setText(intentUrine);
        TextView testerView = (TextView)findViewById(R.id.txtTester);
        testerView.setText(findTesterName(intentTester));
        TextView timeView = (TextView)findViewById(R.id.txtTime);
        timeView.setText(intentTime);

    }

    //when click logout button, the page change to Login
    public void onClickLogout(View v){
        Toast.makeText(ViewTestDataActivity.this,"You loged out successfully.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ViewTestDataActivity.this, MainActivity.class);
        startActivity(intent);
    }


    //convert tester id to string name
    private String findTesterName(String testerId){
        boolean flag = false;
        String testerFullName = null;
        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        //doctor
        String[] projectionDoc = {
                DoctorEntry._ID,
                DoctorEntry.COLUMN_DOCTOR_FIRSTNAME,
                DoctorEntry.COLUMN_DOCTOR_LASTNAME
        };

        // Filter results WHERE "ID" = 'testerId'
        String selectionDoc = DoctorEntry._ID + " = ?";
        String[] selectionArgsDoc = { testerId };

        Cursor cursorDoc = db.query(
                DoctorEntry.TABLE_NAME,                     // The table to query
                projectionDoc,                               // The columns to return
                selectionDoc,                                // The columns for the WHERE clause
                selectionArgsDoc,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        //nurse
        String[] projectionNur = {
                NurseEntry._ID,
                NurseEntry.COLUMN_NURSE_FIRSTNAME,
                NurseEntry.COLUMN_NURSE_LASTNAME
        };

        // Filter results WHERE "ID" = 'testerId'
        String selectionNur = NurseEntry._ID + " = ?";
        String[] selectionArgsNur = { testerId };

        Cursor cursorNur = db.query(
                NurseEntry.TABLE_NAME,                     // The table to query
                projectionNur,                               // The columns to return
                selectionNur,                                // The columns for the WHERE clause
                selectionArgsNur,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

       try{
            // Figure out the index of each column
            //doctor
            int idDoctorColumnIndex = cursorDoc.getColumnIndex(DoctorEntry._ID);
            int firstNameDoctorColumnIndex = cursorDoc.getColumnIndex(DoctorEntry.COLUMN_DOCTOR_FIRSTNAME);
            int lastNameDoctorColumnIndex = cursorDoc.getColumnIndex(DoctorEntry.COLUMN_DOCTOR_LASTNAME);
            //nurse
            int idNurseColumnIndex = cursorNur.getColumnIndex(NurseEntry._ID);
            int firstNameNurseColumnIndex = cursorNur.getColumnIndex(NurseEntry.COLUMN_NURSE_FIRSTNAME);
            int lastNameNurseColumnIndex = cursorNur.getColumnIndex(NurseEntry.COLUMN_NURSE_LASTNAME);

            cursorDoc.moveToFirst();

            //get value from database
            String FName = null;
            String LName = null;
            if(cursorDoc.moveToFirst()){
                FName = cursorDoc.getString(firstNameDoctorColumnIndex);
                LName = cursorDoc.getString(lastNameDoctorColumnIndex);
            }else if(!cursorDoc.moveToFirst()){
                cursorNur.moveToFirst();
                FName = cursorNur.getString(firstNameNurseColumnIndex);
                LName = cursorNur.getString(lastNameNurseColumnIndex);
            }

            testerFullName = FName + " " + LName;

      }catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        } finally {
            cursorDoc.close();
            cursorNur.close();
        }

        return testerFullName;
    }

}
