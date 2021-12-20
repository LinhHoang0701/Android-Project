package com.example.androidproject.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.androidproject.Entity.User;

public class UserSQLiteHelper extends BaseSQLiteHelper<User> {
    private static final String TAG = "DAL USER: ";
    public UserSQLiteHelper(Context context) {
        super(context);
    }

    //update user information
    public void updateUserInfo(User user) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE User SET fullname = ?" +
                ",avatar = ?" +
                ",email = ?" +
                ",phone = ?" +
                ",address = ?";
        SQLiteStatement statement  = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, user.getFullname());
        statement.bindBlob(2, user.getAvatar());
        statement.bindString(3, user.getEmail());
        statement.bindString(4, user.getPhone());
        statement.bindString(5, user.getAddress());
        statement.execute();
        db.close();
        Log.d(TAG, "info updated successful ");
    }

    //change user's password
    public boolean changeUserPassword(String username, String oldPassword, String newPassword) {
        if(authenticateUser(username, oldPassword)) {
            if(!oldPassword.equals(newPassword.trim())) {
                SQLiteDatabase db = getWritableDatabase();
                String sql = "UPDATE User SET password = ?";
                SQLiteStatement statement = db.compileStatement(sql);
                statement.clearBindings();
                statement.bindString(1, newPassword);
                statement.execute();
                db.close();
                Log.d(TAG, "password updated successful");
                return true;
            } else {
                Log.d(TAG, "password updated failed");
                return false;
            }

        } else {
            Log.d(TAG, "password updated failed");
            return false;
        }
    }

    //insert new user to database
    public void registerNewUser(User user) {
        //check if username already existed in db
        if (getUserByUsername(user.getUsername()) == null) {
            //insert new user
            SQLiteDatabase db = getWritableDatabase();
            String sql = "INSERT INTO User (username,password,fullname,avatar,email,phone,address) " +
                    "VALUES (?,?,?,?,?,?,?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();
            statement.bindString(1, user.getUsername());
            statement.bindString(2, user.getPassword());
            statement.bindString(3, user.getFullname());
            statement.bindBlob(4, user.getAvatar());
            statement.bindString(5, user.getEmail());
            statement.bindString(6, user.getPhone());
            statement.bindString(7, user.getAddress());
            statement.executeInsert();
            db.close();
            Log.d(TAG, "register new user successful");
        } else {
            //already existed in db
            Log.d(TAG, "User already existed in db");
        }
    }

    //authenticate user
    public boolean authenticateUser(String username, String password) {
        // empty username or password
        if(username.trim().isEmpty() || password.trim().isEmpty()) {
            Log.d(TAG, "empty username or password");
            return false;
        } else {
            User user = getUserByUsername(username);
            if(user == null) {
                Log.d(TAG, "User not existed in DB");
                return false;
            } else {
                if(!password.trim().equals(user.getPassword())) {
                    Log.d(TAG, "Password is incorrect");
                    return false;
                } else {
                    Log.d(TAG, "authenticated user successful");
                    return true;
                }
            }
        }
    }

    public User getUserByUsername(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE username = ?",new String[]{username});
        if (cursor.moveToNext()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            user.setAvatar(cursor.getBlob(cursor.getColumnIndex("avatar")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            user.setFullname(cursor.getString(cursor.getColumnIndex("fullname")));
            user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            cursor.close();
            return user;
        } else {
            return null;
        }
    }
}
