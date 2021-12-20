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
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.DAL.UserSQLiteHelper;
import com.example.androidproject.Entity.Common;
import com.example.androidproject.Entity.User;

public class SignUpActivity extends MainActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtRepassword;
    private EditText txtFullname;
    private EditText txtPhone;
    private EditText txtAddress;
    private EditText txtEmail;
    private TextView txtLogin;
    private Button btnSignup;
    UserSQLiteHelper helper = new UserSQLiteHelper(SignUpActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        helper = new UserSQLiteHelper(SignUpActivity.this);
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
        txtUsername = findViewById(R.id.edtUsername);
        txtPassword = findViewById(R.id.edtPassword);
        txtRepassword = findViewById(R.id.edtConfirmPassword);
        txtFullname = findViewById(R.id.edtFullName);
        txtAddress = findViewById(R.id.edtAddress);
        txtPhone = findViewById(R.id.edtPhone);
        txtEmail = findViewById(R.id.edtEmail);
        txtLogin = findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btnSignup = findViewById(R.id.btnSignUp);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
                setDefaultInput();
            }
        });
    }

    private void setDefaultInput() {
        txtPassword.setText("");
        txtRepassword.setText("");
    }

    public void register() {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String repassword = txtRepassword.getText().toString();
        String email = txtEmail.getText().toString();
        String phone = txtPhone.getText().toString();
        String fullname = txtFullname.getText().toString();
        String address = txtAddress.getText().toString();
        if(username.isEmpty() && password.isEmpty()) {
            Common.displayMessage(SignUpActivity.this,"Username/password field empty");
        }
        if(helper.getUserByUsername(username)!= null) {
            Common.displayMessage(SignUpActivity.this,"Username is existed");
        }
        else {
            if(!repassword.equals(password)) {
                Common.displayMessage(SignUpActivity.this,"Repassword is different to password");
            }
            else if(phone.length()<10 ||phone.length()>11) {
                Common.displayMessage(SignUpActivity.this,"Phone must be 10 or 11 digits");
            }
            else{
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                helper.registerNewUser(new User(username,password,fullname,email,phone,address,"".getBytes()));
                Common.displayMessage(SignUpActivity.this,"Register successfully");
                startActivity(intent);
            }
        }
    }
}
