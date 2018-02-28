package com.georgebrown.comp3074.a2100984638;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import static com.georgebrown.comp3074.a2100984638.HospitalContract.*;

/**
 * Created by Owner on 11/3/2017.
 */

public class Hospital {

    //departments array
    public static final String[] departments ={
            "surgery",
            "internal medicine",
            "pediatrics",
            "obstetrics",
            "gynecology",
            "ophthalmology",
            "otolaryngology",
            "dermatology",
            "psychiatry",
            "orthopedics",
            "urology",
            "dental surgery"
    };

    //doctors array

    public static final String[] doctors = {
            "Dr. Miranda Bailey",
            "Dr. Owen Hunt",
            "Dr. Derek Shepherd",
            "Dr. Richard Webber",
            "Dr. Meredith Grey"


            /*
            "Dr. Eva Knifed",
            "Dr. Matthew MacLeod",
            "Dr. Amirreza Aalamat",
            "Dr. Kavita Randhawa",
            "Dr. Monika Madan",
            "Dr. Bill Harvey",
            "Dr. Rodolfo Dominguez",
            "Dr. Balchandra Dhawan",
            "Dr. Abraham Jacob",
            "Dr. Farzana Haq",
            "Dr. Hannah Hughes",
            "Dr. Jessica Grewal"
            */
    };

    //rooms array
    public static final String[] rooms={
            "ICU",
            "ER",
            "A501",
            "A502",
            "A401",
            "A402",
            "A403",
            "B301",
            "B302",
            "B303",
            "B304",
            "B305"
    };
}
