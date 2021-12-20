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

public class EditInformationActivity extends MainActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private UserSQLiteHelper helper;
    private EditText txtFullname;
    private EditText txtEmail;
    private EditText txtPhone;
    private EditText txtAddress;
    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);
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
        helper = new UserSQLiteHelper(EditInformationActivity.this);
        txtFullname = findViewById(R.id.edtInfoFullname);
        txtPhone = findViewById(R.id.edtInfoPhone);
        txtEmail = findViewById(R.id.edtInfoEmail);
        txtAddress = findViewById(R.id.edtInfoAddress);
        btnSave = findViewById(R.id.btnSave);
        String username = Paper.book().read("username");
        User user = helper.getUserByUsername(username);
        txtFullname.setText(user.getFullname());
        txtAddress.setText(user.getAddress());
        txtPhone.setText(user.getPhone());
        txtEmail.setText(user.getEmail());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(EditInformationActivity.this,StoreHomePage.class);
                update();
                startActivity(intent);
            }
        });
    }

    private void update() {
        String username = Paper.book().read("username");
        String fullname = txtFullname.getText().toString();
        String phone = txtPhone.getText().toString();
        String email = txtEmail.getText().toString();
        String address = txtAddress.getText().toString();
        User user = helper.getUserByUsername(username);
        if(phone.length() < 10 || phone.length()>11) {
            Common.displayMessage(EditInformationActivity.this,"Phone must be 10 or 11 digits");
        } else {
            helper.updateUserInfo(new User(username,user.getPassword(),fullname,email,phone,address,"".getBytes()));
            Paper.book().write("fullname",fullname);
            Common.displayMessage(EditInformationActivity.this,"Update Successfully");
        }
    }
}
