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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidproject.Adapter.CartAdapter;

public class ShoppingCart extends MainActivity
        implements  NavigationView.OnNavigationItemSelectedListener {

    private ListView listProduct;
    private Button next;
    private TextView totalPrice;
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        listProduct = findViewById(R.id.listProduct);
        getSession(navigationView);
        initEvent();

        shoppingCart = readPaper();
        adapter = new CartAdapter(this, R.layout.list_product_shopping_cart, shoppingCart);
        listProduct.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        next = findViewById(R.id.btnNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Payment.class);
                startActivity(intent);
            }
        });

        totalPrice = findViewById(R.id.txtPrice);

    }

    public void setTextTotalPrice(String text){
        totalPrice.setText(text + "$");
    }
}
