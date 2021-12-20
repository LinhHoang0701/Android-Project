package com.example.androidproject.Entity;

import android.content.Context;
import android.widget.Toast;

public class Common {

    public static void displayMessage(Context context, String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
