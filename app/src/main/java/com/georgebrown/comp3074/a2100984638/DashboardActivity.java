package com.georgebrown.comp3074.a2100984638;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Owner on 11/3/2017.
 */

public class DashboardActivity extends AppCompatActivity {
    String intentUserId;
    String intentUserFullName;

    String intentPatientId;
    String intentFName;
    String intentLName;
    String patientFullName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();
        //Get User id and name from the intent
        intentUserId = intent.getStringExtra("userId");//user id
        intentUserFullName = intent.getStringExtra("userFullName");//user full name
        //Get patient id and name from the intent
        intentPatientId = intent.getStringExtra("id");//patient id
        intentFName = intent.getStringExtra("firstName");//patient first name
        intentLName = intent.getStringExtra("lastName");//patient last name
        patientFullName = intentFName + " " + intentLName; //connect first name and last name

        //display signed in user
        TextView userView = (TextView)findViewById(R.id.txtUser);
        userView.setText(intentUserFullName);
        //Display the patient id
        TextView idView = (TextView)findViewById(R.id.txtPatientId);
        idView.setText(intentPatientId);//id
        //Display the patient name
        TextView nameView = (TextView)findViewById(R.id.txtPatientName);
        nameView.setText(patientFullName);

    }

    //when click logout button, the page change to Login
    public void onClickLogout(View v){
        Toast.makeText(DashboardActivity.this,"You loged out successfully.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
        startActivity(intent);
    }

    //when click patient information button, the page change to patient information
    public void onClickPatientInfo(View v){
        Intent intent = new Intent(DashboardActivity.this, PatientInfoActivity.class);
        intent.putExtra("userId", intentUserId);
        intent.putExtra("userFullName", intentUserFullName);
        intent.putExtra("patientId", intentPatientId);
        startActivity(intent);
    }

    //when click view test data button, the page change to view test data
    public void onClickViewTestData(View v){
        Intent intent = new Intent(DashboardActivity.this, SearchTestActivity.class);
        intent.putExtra("userId", intentUserId);
        intent.putExtra("userFullName", intentUserFullName);
        intent.putExtra("patientId", intentPatientId);
        intent.putExtra("patientFullName", patientFullName);
        startActivity(intent);
    }

    //when click input test data button, the page change to input test data
    public void onClickInputTestData(View v){
        Intent intent = new Intent(DashboardActivity.this, InputTestDataActivity.class);
        intent.putExtra("userId", intentUserId);
        intent.putExtra("userFullName", intentUserFullName);
        intent.putExtra("patientId", intentPatientId);
        intent.putExtra("patientFullName", patientFullName);
        startActivity(intent);
    }

    //when click back to menu button, the page change to menu
    public void onClickBackToMenu(View v){
        Intent intent = new Intent(DashboardActivity.this, ReturnMenuActivity.class);
        intent.putExtra("userId", intentUserId);
        intent.putExtra("userFullName", intentUserFullName);
        startActivity(intent);
    }
}
