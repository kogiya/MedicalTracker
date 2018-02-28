package com.georgebrown.comp3074.a2100984638;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.georgebrown.comp3074.a2100984638.HospitalContract.*;

public class MainActivity extends AppCompatActivity {

    /*find all relevant view*/
    EditText userIdEditText;
    EditText passwordEditText;

    /** Database helper that will provide us access to the database*/
    private HospitalDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*find all relevant view*/
        userIdEditText = (EditText) findViewById(R.id.txtUserId);
        passwordEditText = (EditText) findViewById(R.id.txtPassword);

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        dbHelper = new HospitalDbHelper(this);
    }

    //when click enter button, the page change to Category
    public void onClickEnter(View v){
        ValidateUser();
    }

    /*
    Check if valid user
    */
    private void ValidateUser(){

        //read from input field
        String inputtedId = userIdEditText.getText().toString().trim();
        String inputtedPassword = passwordEditText.getText().toString().trim();

        // Create and/or open a database to read from it
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        //for doctor
        String[] doctorProjection = {
                DoctorEntry._ID,
                DoctorEntry.COLUMN_DOCTOR_FIRSTNAME,
                DoctorEntry.COLUMN_DOCTOR_LASTNAME
        };

       // Filter results WHERE "ID" = 'inputtedId'
        String doctorSelection = DoctorEntry._ID + " = ?";
        String[] doctorSelectionArgs = {inputtedId};

        Cursor doctorCursor = db.query(
                DoctorEntry.TABLE_NAME,                     // The table to query
                doctorProjection,                               // The columns to return
                doctorSelection,                                // The columns for the WHERE clause
                doctorSelectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );


        //for nurse
        String[] nurseProjection = {
                NurseEntry._ID,
                NurseEntry.COLUMN_NURSE_FIRSTNAME,
                NurseEntry.COLUMN_NURSE_LASTNAME
        };

        // Filter results WHERE "ID" = 'inputtedId'
        String nurseSelection = NurseEntry._ID + " = ?";
        String[] nurseSelectionArgs = { inputtedId };

        Cursor nurseCursor = db.query(
                NurseEntry.TABLE_NAME,                     // The table to query
                nurseProjection,                               // The columns to return
                nurseSelection,                                // The columns for the WHERE clause
                nurseSelectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        try {
            // Figure out the index of each column
            //for doctor
            int idColumnIndexDoctor = doctorCursor.getColumnIndex(DoctorEntry._ID);
            int firstNameColumnIndexDoctor = doctorCursor.getColumnIndex(DoctorEntry.COLUMN_DOCTOR_FIRSTNAME);
            int lastNameColumnIndexDoctor = doctorCursor.getColumnIndex(DoctorEntry.COLUMN_DOCTOR_LASTNAME);

            //for nurse
            int idColumnIndexNurse = nurseCursor.getColumnIndex(NurseEntry._ID);
            int firstNameColumnIndexNurse = nurseCursor.getColumnIndex(NurseEntry.COLUMN_NURSE_FIRSTNAME);
            int lastNameColumnIndexNurse = nurseCursor.getColumnIndex(NurseEntry.COLUMN_NURSE_LASTNAME);

            // check if there is matching
            if (doctorCursor.moveToFirst()) {//check doctor table
                String doctorId = doctorCursor.getString(idColumnIndexDoctor);
                String doctorFName = doctorCursor.getString(firstNameColumnIndexDoctor);
                String doctorLName = doctorCursor.getString(lastNameColumnIndexDoctor);

                if (doctorId.equals(inputtedId)  && "admin".equals(inputtedPassword) ) {//both id and password are matched
                    //move to menu activity with first name and last name
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    intent.putExtra("userId", doctorId);
                    intent.putExtra("userFName", doctorFName);
                    intent.putExtra("userLName", doctorLName);
                    startActivity(intent);
                } else {//not match
                    Toast.makeText(this, "Incorrect user id and/or password.", Toast.LENGTH_LONG).show();
                }
            } else if (nurseCursor.moveToFirst()) {//check nurse table
                String nurseId = nurseCursor.getString(idColumnIndexNurse);
                String nurseFName = nurseCursor.getString(firstNameColumnIndexNurse);
                String nurseLName = nurseCursor.getString(lastNameColumnIndexNurse);
                if (nurseId.equals(inputtedId)  && "admin".equals(inputtedPassword) ) {//both id and password are matched
                    //move to menu activity with first name and last name
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    intent.putExtra("userId", nurseId);
                    intent.putExtra("userFName", nurseFName);
                    intent.putExtra("userLName", nurseLName);
                    startActivity(intent);
                } else {//not match
                    Toast.makeText(this, "Incorrect user id and/or password.", Toast.LENGTH_LONG).show();
                }
            }else{ //if nothing input
                Toast.makeText(this, "Incorrect user id and/or password.", Toast.LENGTH_LONG).show();
            }
        }catch(SQLiteException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        } finally {
            doctorCursor.close();
            nurseCursor.close();
        }
    }
}

