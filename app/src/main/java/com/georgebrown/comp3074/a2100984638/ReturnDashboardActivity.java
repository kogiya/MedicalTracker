package com.georgebrown.comp3074.a2100984638;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Owner on 11/6/2017.
 */

public class ReturnDashboardActivity extends AppCompatActivity {
    String intentUserId;
    String intentUserFullName;

    String intentPatientId;
    String intentPatientFullName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

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

    }

    //when click logout button, the page change to Login
    public void onClickLogout(View v){
        Toast.makeText(ReturnDashboardActivity.this,"You loged out successfully.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ReturnDashboardActivity.this, MainActivity.class);
        startActivity(intent);
    }

    //when click patient information button, the page change to patient information
    public void onClickPatientInfo(View v){
        Intent intent = new Intent(ReturnDashboardActivity.this, PatientInfoActivity.class);
        intent.putExtra("userId", intentUserId);
        intent.putExtra("userFullName", intentUserFullName);
        intent.putExtra("patientId", intentPatientId);
        startActivity(intent);
    }

    //when click view test data button, the page change to view test data
    public void onClickViewTestData(View v){
        Intent intent = new Intent(ReturnDashboardActivity.this, SearchTestActivity.class);
        intent.putExtra("userId", intentUserId);
        intent.putExtra("userFullName", intentUserFullName);
        intent.putExtra("patientId", intentPatientId);
        intent.putExtra("patientFullName",intentPatientFullName);
        startActivity(intent);
    }

    //when click input test data button, the page change to input test data
    public void onClickInputTestData(View v){
        Intent intent = new Intent(ReturnDashboardActivity.this, InputTestDataActivity.class);
        intent.putExtra("userId", intentUserId);
        intent.putExtra("userFullName", intentUserFullName);
        intent.putExtra("patientId", intentPatientId);
        intent.putExtra("patientFullName", intentPatientFullName);
        startActivity(intent);
    }

    //when click back to menu button, the page change to menu
    public void onClickBackToMenu(View v){
        Intent intent = new Intent(ReturnDashboardActivity.this, ReturnMenuActivity.class);
        intent.putExtra("userId", intentUserId);
        intent.putExtra("userFullName", intentUserFullName);
        startActivity(intent);
    }
}
