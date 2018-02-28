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

//from dashboard by clicking back to menu
public class ReturnMenuActivity extends AppCompatActivity {
    String intentUserId;
    String intentUserFullName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Get User id and name from the intent
        Intent intent = getIntent();
        intentUserId = intent.getStringExtra("userId");//user id
        intentUserFullName = intent.getStringExtra("userFullName");

        //display signed in user
        TextView userView = (TextView)findViewById(R.id.txtUser);
            userView.setText(intentUserFullName);
    }

    //when click enter button, the page change to Login
    public void onClickLogout(View v){
        Toast.makeText(ReturnMenuActivity.this,"You loged out successfully.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ReturnMenuActivity.this, MainActivity.class);
        startActivity(intent);
    }

    //when click new patient button, the page change to New Patient
    public void onClickNewPatient(View v){
        Intent intent = new Intent(ReturnMenuActivity.this, NewPatientActivity.class);
        intent.putExtra("userId", intentUserId);
        intent.putExtra("userFullName",intentUserFullName);
        startActivity(intent);
    }

    //when click new patient button, the page change to Search Patient
    public void onClickSearchPatient(View v){
        Intent intent = new Intent(ReturnMenuActivity.this, SearchPatientActivity.class);
        intent.putExtra("userId", intentUserId);
        intent.putExtra("userFullName", intentUserFullName);
        startActivity(intent);
    }
}
