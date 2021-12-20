package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidproject.DAL.UserSQLiteHelper;
import com.example.androidproject.Entity.Common;
import com.example.androidproject.Entity.User;

import io.paperdb.Paper;

public class ChangePasswordActivity extends MainActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText txtOldpassword;
    private EditText txtNewpassword;
    private EditText txtRenewpassword;
    private Button btnSaveChange;
    private UserSQLiteHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSession(navigationView);
        txtOldpassword = findViewById(R.id.edtOldPassword);
        txtNewpassword = findViewById(R.id.edtNewPassword);
        txtRenewpassword = findViewById(R.id.edtReNewPassword);
        btnSaveChange = findViewById(R.id.btnChangePw);
        helper = new UserSQLiteHelper(ChangePasswordActivity.this);
        Paper.init(ChangePasswordActivity.this);
        btnSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changePassword()) {
                    Intent intent = new Intent(ChangePasswordActivity.this,StoreHomePage.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void setDefault() {
        txtOldpassword.setText("");
        txtNewpassword.setText("");
        txtRenewpassword.setText("");
    }

    private boolean changePassword() {
        String oldpw = txtOldpassword.getText().toString();
        String newpw = txtNewpassword.getText().toString();
        String renewpw = txtRenewpassword.getText().toString();
        String username = Paper.book().read("username");
        if(oldpw.isEmpty() || newpw.isEmpty() || renewpw.isEmpty()) {
            Common.displayMessage(ChangePasswordActivity.this,"You must enter all field");
            setDefault();
            return false;
        }
        else {
            User user = helper.getUserByUsername(username);
            if(!oldpw.equals(user.getPassword())) {
                Common.displayMessage(ChangePasswordActivity.this,"Old password is wrong");
                return false;
            } else {
                if(newpw.equals(oldpw)) {
                    Common.displayMessage(ChangePasswordActivity.this,"New password must be different to old pass");
                    setDefault();
                    return false;
                }
                else {
                    if(!renewpw.equals(newpw)) {
                        Common.displayMessage(ChangePasswordActivity.this,"2 password must be same");
                        setDefault();
                        return false;
                    }
                    else {
                        helper.changeUserPassword(username,oldpw,newpw);
                        Common.displayMessage(ChangePasswordActivity.this,"Change password successfully");
                        return true;
                    }
                }
            }
        }
    }

}
