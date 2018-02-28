package com.georgebrown.comp3074.a2100984638;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;

import static com.georgebrown.comp3074.a2100984638.HospitalContract.*;

/**
 * Created by Owner on 11/3/2017.
 */

public class InputTestDataActivity extends AppCompatActivity {
    String intentUserId;
    String intentUserFullName;
    String intentPatientId;
    String intentPatientFullName;

    private EditText btEditText;
    private EditText bphEditText;
    private EditText bplEditText;
    private EditText hrEditText;
    private EditText rrEditText;
    private EditText urineEditText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputtestdata);

        Intent intent = getIntent();
        //Get User id and name from the intent
        intentUserId = intent.getStringExtra("userId");//user id
        intentUserFullName = intent.getStringExtra("userFullName");//user full name
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

         /*
        find all relevant views
         */
        btEditText = (EditText) findViewById(R.id.editBT);
        bphEditText = (EditText) findViewById(R.id.editBPH);
        bplEditText = (EditText) findViewById(R.id.editBPL);
        hrEditText = (EditText) findViewById(R.id.editHR);
        rrEditText = (EditText) findViewById(R.id.editRR);
        urineEditText = (EditText) findViewById(R.id.editUrine);
    }

    //when click logout button, the page change to Login
    public void onClickLogout(View v){
        Toast.makeText(InputTestDataActivity.this,"You loged out successfully.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(InputTestDataActivity.this, MainActivity.class);
        startActivity(intent);
    }

    //when click submit button, the page change to Dashboard
    public void onClickTestSubmit(View v){
        // send inputted data to database
        insertTestData();

        Intent intent = new Intent(InputTestDataActivity.this, ReturnDashboardActivity.class);
        //take patient id and name
        //take user id and name
        intent.putExtra("userId", intentUserId);
        intent.putExtra("userFullName", intentUserFullName);
        intent.putExtra("patientId", intentPatientId);
        intent.putExtra("patientFullName", intentPatientFullName);
        startActivity(intent);
    }

    //get user input and save new test data into database
    private void insertTestData(){
        int patientId = Integer.parseInt(intentPatientId);
        //read from input field
        String bt = btEditText.getText().toString().trim();
        String bph = bphEditText.getText().toString().trim();
        String bpl = bplEditText.getText().toString().trim();
        String hr = hrEditText.getText().toString().trim();
        String rr = rrEditText.getText().toString().trim();
        String urine = urineEditText.getText().toString().trim();

        //get timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String time = timestamp.toString();

        //create database helper
        HospitalDbHelper helper = new HospitalDbHelper(this);

        //get the database in write mode
        SQLiteDatabase db = helper.getWritableDatabase();

        //create a ContentValue object column name = user input
        ContentValues values = new ContentValues();
        values.put(TestEntry.COLUMN_TEST_PATIENTID, patientId);
        values.put(TestEntry.COLUMN_TEST_BT, bt);
        values.put(TestEntry.COLUMN_TEST_BPH, bph);
        values.put(TestEntry.COLUMN_TEST_BPL, bpl);
        values.put(TestEntry.COLUMN_TEST_HR, hr);
        values.put(TestEntry.COLUMN_TEST_RR, rr);
        values.put(TestEntry.COLUMN_TEST_URINE, urine);
        values.put(TestEntry.COLUMN_TEST_TESTER, intentUserId);
        values.put(TestEntry.COLUMN_TEST_TIME, time);

        //insert a new row for patient in the database
        //returning the ID of that new row
        long newRowId = db.insert(TestEntry.TABLE_NAME, null, values);

        //show a toast message if insertion is succeeded
        if(newRowId == -1){
            //if the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving patient",Toast.LENGTH_SHORT).show();
        }else{
            //otherwise, the insertion was successful and we can display a toast with the row ID
            Toast.makeText(this, "Test data is saved with row id.",Toast.LENGTH_SHORT).show();
        }

    }
}
