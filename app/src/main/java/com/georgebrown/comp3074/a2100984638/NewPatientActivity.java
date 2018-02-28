package com.georgebrown.comp3074.a2100984638;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.georgebrown.comp3074.a2100984638.HospitalContract.*;


/**
 * Created by Owner on 11/3/2017.
 */

public class NewPatientActivity extends AppCompatActivity {
    String intentUserId;
    String intentUserFullName;

    Spinner spinner;
    ArrayAdapter<String> adapter;

    private EditText fNameEditText;
    private EditText lNameEditText;
    private Spinner departmentSpinner;
    private Spinner doctorSpinner;
    private Spinner roomSpinner;

    //value from spinner
    private String department;
    private String doctor;//to display spinner
    private String room;
    private String doctorId;//for inserting db

    /** Database helper that will provide us access to the database*/
    private HospitalDbHelper dbHelper;



    //doctor array generated from database
    ArrayList<Doctor> doctorArr = new ArrayList<Doctor>();
    public static final ArrayList<String> doctors = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpatient);

        //Get User id and name from the intent
        Intent intent = getIntent();
        intentUserId = intent.getStringExtra("userId");//user id
        intentUserFullName = intent.getStringExtra("userFullName");//user full name

        //display signed in user
        TextView userView = (TextView)findViewById(R.id.txtUser);
        userView.setText(intentUserFullName);


        /*
        find all relevant views
         */
        fNameEditText = (EditText) findViewById(R.id.txtFirstName);
        lNameEditText = (EditText) findViewById(R.id.txtLastName);
        departmentSpinner = (Spinner) findViewById(R.id.spinDepartment);
        doctorSpinner = (Spinner) findViewById(R.id.spinDoctor);
        roomSpinner = (Spinner) findViewById(R.id.spinRoom);

        setupSpinner();
    }

    //when click logout button, the page change to Login
    public void onClickLogout(View v){
        Toast.makeText(NewPatientActivity.this,"You loged out successfully.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(NewPatientActivity.this, MainActivity.class);
        startActivity(intent);
    }

    //generate each spinner
    private void setupSpinner(){
         /*
        generate department spinner
         */
        spinner = (Spinner) findViewById(R.id.spinDepartment);
        //create an ArrayAdapter using the string array and a dfault spinner layout
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Hospital.departments);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //get value from spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department = (String) parent.getItemAtPosition(position);
            }
            //spinner default value (if nothing is selected)
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                department = "surgery";//the first value
            }
        });

        /*
        generate doctor spinner
         */
        spinner = (Spinner) findViewById(R.id.spinDoctor);
        //create an ArrayAdapter using the string array and a dfault spinner layout
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Hospital.doctors);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //get value from spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                doctor = (String) parent.getItemAtPosition(position);
                doctorId = "d" + String.format("%04d",(position + 1));
            }
            //spinner default value (if nothing is selected)
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                doctor = "Dr. Miranda Bailey";//the first value
                doctorId = "d0001";
            }
        });

        /*
        generate room spinner
         */
        spinner = (Spinner) findViewById(R.id.spinRoom);
        //create an ArrayAdapter using the string array and a dfault spinner layout
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Hospital.rooms);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //get value from spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                room = (String) parent.getItemAtPosition(position);
            }
            //spinner default value (if nothing is selected)
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                room = "ICU";//the first value
            }
        });
    }

    //get user input and save new patient into database
    private void insertPatient(){
        //read from input field
        String firstName = fNameEditText.getText().toString().trim();
        String lastName = lNameEditText.getText().toString().trim();
        //spinner values are read in setupSpinner()

        //validate user input
        if(Validation.isMissing(firstName) || Validation.isMissing(lastName)){//first name and last name are required
            Toast.makeText(this, "Patient's first name and last name are required." ,Toast.LENGTH_SHORT).show();

        }else if(!Validation.isAlphabet(firstName)|| !Validation.isAlphabet(lastName)){//first name and last name must be alphabet
            Toast.makeText(this, "Patient's first name and last name must contain only alphabets.",Toast.LENGTH_SHORT).show();

        }else {
            //create database helper
            HospitalDbHelper helper = new HospitalDbHelper(this);

            //get the database in write mode
            SQLiteDatabase db = helper.getWritableDatabase();

            //insert values to database
            //create a ContentValue object column name = user input
            ContentValues values = new ContentValues();
            values.put(PatientEntry.COLUMN_PATIENT_FIRSTNAME, firstName);
            values.put(PatientEntry.COLUMN_PATIENT_LASTNAME, lastName);
            values.put(PatientEntry.COLUMN_PATIENT_DEPARTMENT, department);
            values.put(PatientEntry.COLUMN_PATIENT_DOCTORID, doctorId);
            values.put(PatientEntry.COLUMN_PATIENT_ROOM, room);

            //insert a new row for patient in the database
            //returning the ID of that new row
            long newRowId = db.insert(PatientEntry.TABLE_NAME, null, values);

            //show a toast message if insertion is succeeded
            if (newRowId == -1) {
                //if the row ID is -1, then there was an error with insertion.
                Toast.makeText(this, "Error with saving patient", Toast.LENGTH_SHORT).show();
            } else {
                //otherwise, the insertion was successful and we can display a toast with the row ID
                Toast.makeText(this, "Patient is saved with row id.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NewPatientActivity.this, ReturnMenuActivity.class);
                intent.putExtra("userId", intentUserId);
                intent.putExtra("userFullName", intentUserFullName);
                startActivity(intent);
            }
        }
    }

    //when click submit button, the page change to Menu
    public void onClickPatientSubmit(View v){
        /*
        send inputted data to database
         */
        insertPatient();
    }
}
