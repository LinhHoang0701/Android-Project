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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.androidproject.DAL.PurchaseSQLiteHelper;
import com.example.androidproject.DAL.UserSQLiteHelper;
import com.example.androidproject.Entity.User;

import java.util.HashMap;

public class Payment extends MainActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText name;
    private EditText phone;
    private EditText address;
    private Button buy;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
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
        initEvent();

        name = findViewById(R.id.txtName);
        phone = findViewById(R.id.txtPhone);
        address = findViewById(R.id.txtAddress);
        buy = findViewById(R.id.btnBuy);
        radioGroup = findViewById(R.id.radioGroup);
        UserSQLiteHelper userSQLiteHelper = new UserSQLiteHelper(this);
        username = "duccmse06002";
        User user = userSQLiteHelper.getUserByUsername(username);
        name.setText(user.getUsername());
        phone.setText(user.getPhone());
        address.setText(user.getAddress());
        userSQLiteHelper.close();




        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoppingCart = readPaper();
                PurchaseSQLiteHelper purchaseSQLiteHelper = new PurchaseSQLiteHelper(getApplicationContext());
                int selectedRadioButtonID = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonID);
                String text = selectedRadioButton.getText().toString();
                purchaseSQLiteHelper.makePurchase(username, address.getText().toString(), shoppingCart, text);
                purchaseSQLiteHelper.close();
                Toast.makeText(getApplicationContext(), "Order success", Toast.LENGTH_LONG).show();
                shoppingCart = new HashMap<>();
                writePaper();
                Intent intent = new Intent(getApplicationContext(), StoreHomePage.class);
                startActivity(intent);
            }
        });
    }

}
