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

import junit.framework.Test;

import static com.georgebrown.comp3074.a2100984638.HospitalContract.*;

/**
 * Created by Owner on 11/6/2017.
 */

public class SearchTestActivity extends AppCompatActivity {
    String intentUserId;
    String intentUserFullName;
    String intentPatientId;
    String intentPatientFullName;

    /** Database helper that will provide us access to the database*/
    private HospitalDbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchtest);

        //Get User id and name from the intent
        Intent intent = getIntent();
        intentUserId = intent.getStringExtra("userId");//patient id
        intentUserFullName = intent.getStringExtra("userFullName");//patient first name
        //Get patient id and name from the intent
        intentPatientId = intent.getStringExtra("patientId");//patient id
        intentPatientFullName = intent.getStringExtra("patientFullName");//patient full name

        //display signed in user
        TextView userView = (TextView)findViewById(R.id.txtUser);
        userView.setText(intentUserFullName);
        //Display the patient id
        TextView idView = (TextView)findViewById(R.id.txtPatientId);
        idView.setText(intentPatientId);//id
        //Display the patient name
        TextView nameView = (TextView)findViewById(R.id.txtPatientName);
        nameView.setText(intentPatientFullName);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        dbHelper = new HospitalDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayTestList();
    }

    /*
       Show test in DB
       */
    private void displayTestList(){

        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        String[] projection = {
                TestEntry._ID,
                TestEntry.COLUMN_TEST_TESTER,
                TestEntry.COLUMN_TEST_TIME
        };

        // Filter results WHERE "ID" = 'intentPatientId'
        String selection = TestEntry.COLUMN_TEST_PATIENTID + " = ?";
        String[] selectionArgs = { intentPatientId };

        Cursor cursor = db.query(
                TestEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        TextView allTestView = (TextView)findViewById(R.id.testList);
        try{
            allTestView.setText(TestEntry._ID + " - " +
                    TestEntry.COLUMN_TEST_TESTER + " - " + "Tester Name - Tested Time" + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(TestEntry._ID);
            int testerColumnIndex = cursor.getColumnIndex(TestEntry.COLUMN_TEST_TESTER);
            int timeColumnIndex = cursor.getColumnIndex(TestEntry.COLUMN_TEST_TIME);

            // Iterate through all the returned rows in the cursor
            while(cursor.moveToNext()){
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentId = cursor.getInt(idColumnIndex);
                String currentTester = cursor.getString(testerColumnIndex);
                String currentTime = cursor.getString(timeColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                allTestView.append(("\n" + currentId + " - " +
                        currentTester + " - " + findTesterName(currentTester) + " - " + currentTime));
            }
        }finally {
            cursor.close();
        }
    }

    //when click logout button, the page change to Login
    public void onClickLogout(View v){
        Toast.makeText(SearchTestActivity.this,"You loged out successfully.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SearchTestActivity.this, MainActivity.class);
        startActivity(intent);
    }

    //when click search button, search result is shown in list view
    public void onClickSearch(View v){


        //connect DB
        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        String[] projection = {
                TestEntry._ID,
                TestEntry.COLUMN_TEST_PATIENTID,
                TestEntry.COLUMN_TEST_BT,
                TestEntry.COLUMN_TEST_BPH,
                TestEntry.COLUMN_TEST_BPL,
                TestEntry.COLUMN_TEST_HR,
                TestEntry.COLUMN_TEST_RR,
                TestEntry.COLUMN_TEST_URINE,
                TestEntry.COLUMN_TEST_TESTER,
                TestEntry.COLUMN_TEST_TIME
        };

        // Filter results WHERE "ID" = 'user input'
        //get user input
        EditText idEditText = (EditText) findViewById(R.id.txtTestId);
        String inputId = idEditText.getText().toString().trim();

        String selection = TestEntry._ID + " = ? and " + TestEntry.COLUMN_TEST_PATIENTID +" = ?";
        String[] selectionArgs = { inputId, intentPatientId };

        //validate user input
        if(Validation.isMissing(inputId)){//check if value is missing
            Toast.makeText(this, "Please input patient ID.",Toast.LENGTH_SHORT).show();
        }
        else {
            //create cursor
            Cursor cursor = db.query(
                    TestEntry.TABLE_NAME,                     // The table to query
                    projection,                               // The columns to return
                    selection,                                // The columns for the WHERE clause
                    selectionArgs,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                 // The sort order
            );

            try {
                // Figure out the index of each column
                int idColumnIndex = cursor.getColumnIndex(TestEntry._ID);
                int btColumnIndex = cursor.getColumnIndex(TestEntry.COLUMN_TEST_BT);
                int bphColumnIndex = cursor.getColumnIndex(TestEntry.COLUMN_TEST_BPH);
                int bplColumnIndex = cursor.getColumnIndex(TestEntry.COLUMN_TEST_BPL);
                int hrColumnIndex = cursor.getColumnIndex(TestEntry.COLUMN_TEST_HR);
                int rrColumnIndex = cursor.getColumnIndex(TestEntry.COLUMN_TEST_RR);
                int urineColumnIndex = cursor.getColumnIndex(TestEntry.COLUMN_TEST_URINE);
                int testerColumnIndex = cursor.getColumnIndex(TestEntry.COLUMN_TEST_TESTER);
                int timeColumnIndex = cursor.getColumnIndex(TestEntry.COLUMN_TEST_TIME);

                //check if there is matched data
                //id is unique so if it matches, there is only one row
                if (cursor.moveToFirst()) {
                    // Use that index to extract the String or Int value of the word
                    // at the current row the cursor is on.
                    String currentId = Integer.toString(cursor.getInt(idColumnIndex));// parsed id from integer to string
                    String currentBT = cursor.getString(btColumnIndex);
                    String currentBPH = cursor.getString(bphColumnIndex);
                    String currentBPL = cursor.getString(bplColumnIndex);
                    String currentHR = cursor.getString(hrColumnIndex);
                    String currentRR = cursor.getString(rrColumnIndex);
                    String currentUrine = cursor.getString(urineColumnIndex);
                    String currentTester = cursor.getString(testerColumnIndex);
                    String currentTime = cursor.getString(timeColumnIndex);

                    //pass Id and name to next activity
                    Intent intent = new Intent(SearchTestActivity.this, ViewTestDataActivity.class);
                    intent.putExtra("userId", intentUserId);
                    intent.putExtra("userFullName", intentUserFullName);
                    intent.putExtra("patientId", intentPatientId);
                    intent.putExtra("patientFullName", intentPatientFullName);
                    intent.putExtra("testId", currentId);
                    intent.putExtra("bt", currentBT);
                    intent.putExtra("bph", currentBPH);
                    intent.putExtra("bpl", currentBPL);
                    intent.putExtra("hr", currentHR);
                    intent.putExtra("rr", currentRR);
                    intent.putExtra("urine", currentUrine);
                    intent.putExtra("tester", currentTester);
                    intent.putExtra("time", currentTime);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "There is no such test data.", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLiteException e) {
                Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            } finally {
                cursor.close();
            }
        }
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
