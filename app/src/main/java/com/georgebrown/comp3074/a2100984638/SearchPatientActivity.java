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

import com.georgebrown.comp3074.a2100984638.HospitalContract.PatientEntry;

/**
 * Created by Owner on 11/3/2017.
 */

public class SearchPatientActivity  extends AppCompatActivity {
    String intentUserId;
    String intentUserFullName;

    /** Database helper that will provide us access to the database*/
    private HospitalDbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpatient);

        //Get User id and name from the intent
        Intent intent = getIntent();
        intentUserId = intent.getStringExtra("userId");//patient id
        intentUserFullName = intent.getStringExtra("userFullName");//patient first name

        //display signed in user
        TextView userView = (TextView)findViewById(R.id.txtUser);
        userView.setText(intentUserFullName);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        dbHelper = new HospitalDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayPatientList();
    }

    /*
    Show patients in DB
    */
    private void displayPatientList(){

        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        String[] projection = {
                PatientEntry._ID,
                PatientEntry.COLUMN_PATIENT_FIRSTNAME,
                PatientEntry.COLUMN_PATIENT_LASTNAME
        };

        Cursor cursor = db.query(
                PatientEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        TextView allPatientView = (TextView)findViewById(R.id.patientList);
        try{
            allPatientView.setText(PatientEntry._ID + " - " +
                    PatientEntry.COLUMN_PATIENT_FIRSTNAME + " -  " +
                    PatientEntry.COLUMN_PATIENT_LASTNAME + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(PatientEntry._ID);
            int firstNameColumnIndex = cursor.getColumnIndex(PatientEntry.COLUMN_PATIENT_FIRSTNAME);
            int lastNameColumnIndex = cursor.getColumnIndex(PatientEntry.COLUMN_PATIENT_LASTNAME);

            // Iterate through all the returned rows in the cursor
            while(cursor.moveToNext()){
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentId = cursor.getInt(idColumnIndex);
                String currentFName = cursor.getString(firstNameColumnIndex);
                String currentLName = cursor.getString(lastNameColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                allPatientView.append(("\n" + currentId + " - " +
                        currentFName + " " +
                        currentLName));
            }
        }finally {
            cursor.close();
       }
     }

    //when click logout button, the page change to Login
    public void onClickLogout(View v){
        Toast.makeText(SearchPatientActivity.this,"You loged out successfully.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SearchPatientActivity.this, MainActivity.class);
        startActivity(intent);
    }

    //when click search button, search result is shown in list view
    public void onClickSearch(View v){


        //connect DB
        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        String[] projection = {
                PatientEntry._ID,
                PatientEntry.COLUMN_PATIENT_FIRSTNAME,
                PatientEntry.COLUMN_PATIENT_LASTNAME
        };

        // Filter results WHERE "ID" = 'user input'
        //get user input
        EditText idEditText = (EditText) findViewById(R.id.txtPatientId);
        String inputId = idEditText.getText().toString().trim();

        //validate user input
        if(Validation.isMissing(inputId)){//check if it is not missing
            Toast.makeText(this, "Please input patient ID.",Toast.LENGTH_SHORT).show();
        }
        else {

            String selection = PatientEntry._ID + " = ?";
            String[] selectionArgs = {inputId};

            //create cursor
            Cursor cursor = db.query(
                    PatientEntry.TABLE_NAME,                     // The table to query
                    projection,                               // The columns to return
                    selection,                                // The columns for the WHERE clause
                    selectionArgs,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                 // The sort order
            );

            try {
                // Figure out the index of each column
                int idColumnIndex = cursor.getColumnIndex(PatientEntry._ID);
                int firstNameColumnIndex = cursor.getColumnIndex(PatientEntry.COLUMN_PATIENT_FIRSTNAME);
                int lastNameColumnIndex = cursor.getColumnIndex(PatientEntry.COLUMN_PATIENT_LASTNAME);

                //check if there is matched data
                //id is unique so if it matches, there is only one row
                if (cursor.moveToFirst()) {
                    // Use that index to extract the String or Int value of the word
                    // at the current row the cursor is on.
                    String currentId = Integer.toString(cursor.getInt(idColumnIndex));// parsed id from integer to string
                    String currentFName = cursor.getString(firstNameColumnIndex);
                    String currentLName = cursor.getString(lastNameColumnIndex);

                    //pass Id and name to next activity
                    Intent intent = new Intent(SearchPatientActivity.this, DashboardActivity.class);
                    intent.putExtra("userId", intentUserId);
                    intent.putExtra("userFullName", intentUserFullName);
                    intent.putExtra("id", currentId);
                    intent.putExtra("firstName", currentFName);
                    intent.putExtra("lastName", currentLName);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "There is no such patient.", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLiteException e) {
                Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            } finally {
                cursor.close();
            }
        }
    }


}
