package com.example.androidproject.DAL;

import android.content.Context;

import com.example.androidproject.Entity.Admin;


public class AdminSQLiteHelper extends BaseSQLiteHelper<Admin> {


    public AdminSQLiteHelper(Context context) {
        super(context);
    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS \"Admin\" (\n" +
//                "\t\"Username\"\tTEXT NOT NULL UNIQUE,\n" +
//                "\t\"Password\"\tTEXT NOT NULL,\n" +
//                "\tPRIMARY KEY(\"Username\")\n" +
//                ");");
//        db.execSQL("INSERT INTO \"Admin\" (\"Username\",\"Password\") VALUES ('duccm4','duccm4'),\n" +
//                " ('trungtq','trungtq'),\n" +
//                " ('dungdv11','dungdv11');");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
}
