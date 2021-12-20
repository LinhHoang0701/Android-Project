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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.DAL.UserSQLiteHelper;
import com.example.androidproject.Entity.User;

import io.paperdb.Paper;

public class LoginActivity extends MainActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private UserSQLiteHelper helper;
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;
    private Button btnCancel;
    private TextView txtRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new UserSQLiteHelper(LoginActivity.this);
        setContentView(R.layout.activity_login);
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
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnCancel = findViewById(R.id.btnCancel);
        txtRegister = findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,StoreHomePage.class);
                startActivity(intent);
            }
        });
        Paper.init(LoginActivity.this);
    }

    public void login() {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        User user = helper.getUserByUsername(username);
        if(user == null) {
            Toast.makeText(getApplicationContext(),"Username or password is wrong",Toast.LENGTH_LONG).show();
            txtUsername.setText("");
            txtPassword.setText("");
        }
        else {
            if (!password.equals(user.getPassword())) {
                Toast.makeText(getApplicationContext(), "Username or password is wrong", Toast.LENGTH_LONG).show();
                txtPassword.setText("");
            }
            else {
                Intent intent = new Intent(LoginActivity.this, StoreHomePage.class);
                Paper.book().write("username",username);
                Paper.book().write("fullname",user.getFullname());
                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        }
    }
}
