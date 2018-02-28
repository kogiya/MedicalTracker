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

public class MenuActivity  extends AppCompatActivity {

    String intentUserId;
    String intentUserFName;
    String intentUserLName;
    String userFullName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Get User id and name from the intent
        Intent intent = getIntent();
        intentUserId = intent.getStringExtra("userId");//user id
        intentUserFName = intent.getStringExtra("userFName");//user first name
        intentUserLName = intent.getStringExtra("userLName");//user last name
        userFullName = intentUserFName + " " + intentUserLName; //connect user first name and last name

        //display signed in user
        TextView userView = (TextView)findViewById(R.id.txtUser);
        userView.setText(userFullName);
    }

    //when click enter button, the page change to Login
    public void onClickLogout(View v){
        Toast.makeText(MenuActivity.this,"You loged out successfully.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(intent);
    }

    //when click new patient button, the page change to New Patient
    public void onClickNewPatient(View v){
        Intent intent = new Intent(MenuActivity.this, NewPatientActivity.class);
        intent.putExtra("userId", intentUserId);
        intent.putExtra("userFullName", userFullName);
        startActivity(intent);
    }

    //when click new patient button, the page change to Search Patient
    public void onClickSearchPatient(View v){
        Intent intent = new Intent(MenuActivity.this, SearchPatientActivity.class);
        intent.putExtra("userId", intentUserId);
        intent.putExtra("userFullName", userFullName);
        startActivity(intent);
    }
}
