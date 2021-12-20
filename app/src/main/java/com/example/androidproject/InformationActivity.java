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
import com.example.androidproject.Entity.User;

import io.paperdb.Paper;

public class InformationActivity extends MainActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText txtUsername;
    private EditText txtFullname;
    private EditText txtPhone;
    private EditText txtAddress;
    private EditText txtEmail;
    private Button btnEdit;
    private Button btnChangepw;
    private UserSQLiteHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Paper.init(InformationActivity.this);
//        String username = Paper.book().read("username");
        getSession(navigationView);
        Toast.makeText(this, username, Toast.LENGTH_LONG).show();

        helper = new UserSQLiteHelper(InformationActivity.this);
        txtUsername = findViewById(R.id.username);
        txtFullname = findViewById(R.id.fullname);
        txtAddress = findViewById(R.id.Address);
        txtEmail = findViewById(R.id.email);
        txtPhone = findViewById(R.id.phone);
        btnEdit = findViewById(R.id.btnEdtInformation);
        btnChangepw = findViewById(R.id.btnChangePw);
        txtUsername.setText(username);
        User user = helper.getUserByUsername(username);
        txtFullname.setText(user.getFullname());
        txtPhone.setText(user.getPhone());
        txtEmail.setText(user.getEmail());
        txtAddress.setText(user.getAddress());
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationActivity.this,EditInformationActivity.class);
                startActivity(intent);
            }
        });

        btnChangepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InformationActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
    }

}
