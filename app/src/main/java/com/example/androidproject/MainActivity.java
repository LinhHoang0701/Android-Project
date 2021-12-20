package com.example.androidproject;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidproject.DAL.ProductSQLiteHelper;
import com.example.androidproject.DAL.UserSQLiteHelper;
import com.example.androidproject.Entity.Product;

import com.example.androidproject.Entity.PurchaseDetail;
import com.example.androidproject.Entity.PurchaseMaster;
import com.example.androidproject.Entity.User;

import io.paperdb.Paper;

import java.util.ArrayList;
import java.util.HashMap;

    public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TextView searchBar;
    protected ImageButton search;
    protected ImageButton cart;

    protected String username;
    protected String fullname;
    protected Paper paper;
    protected HashMap<Integer, Integer> shoppingCart = new HashMap<>();
    protected PurchaseDetail purchaseDetail;
    protected ArrayList<PurchaseDetail> detailList = new ArrayList<>();
    protected PurchaseMaster purchaseMaster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //get session
        getSession(navigationView);

        ProductSQLiteHelper sqLiteHelper = new ProductSQLiteHelper(getApplicationContext());
        ArrayList<Product> a = sqLiteHelper.getProducts(null,null,"Watch");
        ArrayList<String> b = sqLiteHelper.getAllCategory();
        sqLiteHelper.insertProductImage(1, AppCompatResources.getDrawable(getApplicationContext(), R.drawable.avatar));
        Product c = sqLiteHelper.getProductById(1);

        cart = findViewById(R.id.btnCart);
//        cart.setOnClickListener(this);
//        search = findViewById(R.id.btnSearch);
//        search.setOnClickListener(this);

//        ProductSQLiteHelper sqLiteHelper = new ProductSQLiteHelper(getApplicationContext());
//        ArrayList<Product> a = sqLiteHelper.getProducts(null,null,"Watch");
//        ArrayList<String> b = sqLiteHelper.getAllCategory();
//        sqLiteHelper.insertProductImage(1, AppCompatResources.getDrawable(getApplicationContext(), R.drawable.avatar));
//        Product c = sqLiteHelper.getProductById(1);
        //PurchaseSQLiteHelper purchaseSQLiteHelper = new PurchaseSQLiteHelper(getApplicationContext());
//        HashMap<Integer, Integer> shoppingCart = new HashMap<>();
//        shoppingCart.put(1, 1);
//        shoppingCart.put(2, 6);
//        purchaseSQLiteHelper.makePurchase("duccmse06002", null, shoppingCart);
//        ArrayList<PurchaseMaster> a = purchaseSQLiteHelper.getAllPurchaseMasterByUsername("duccmse06002");
//        float total = a.get(1).getTotal();
        //purchaseSQLiteHelper.cancelPurchase("2s");
    }

    protected void getSession(NavigationView navigationView) {
        Paper.init(this);
        username = Paper.book().read("username",null);
        if(username == null) {
            navigationView.getMenu().findItem(R.id.logged).setVisible(false);

        } else {
            navigationView.getMenu().findItem(R.id.not_logged).setVisible(false);
            UserSQLiteHelper helper = new UserSQLiteHelper(this);
            User user = helper.getUserByUsername(username);
            helper.close();
            View hView =  navigationView.getHeaderView(0);
            TextView name1 = hView.findViewById(R.id.nav_view_fullname);
            name1.setText(user.getFullname());
            name1 = hView.findViewById(R.id.nav_view_username);
            name1.setText(user.getUsername());
            ImageView imageView = hView.findViewById(R.id.imgAvatar1);
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(user.getAvatar(), 0, user.getAvatar().length));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Intent intent = new Intent(this,StoreHomePage   .class);
            startActivity(intent);

        } else if (id == R.id.nav_sign_up) {
            Intent intent = new Intent(this,SignUpActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_login) {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(this,InformationActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(this, PurchaseActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this,StoreHomePage.class);
            Paper.init(this);
            Paper.book().destroy();
            finish();
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void writePaper(){
        paper.book().write("shoppingCart", shoppingCart);
    }


    public HashMap<Integer, Integer> readPaper(){
        shoppingCart = paper.book().read("shoppingCart", new HashMap<Integer, Integer>());
        return shoppingCart;
    }

    public void initEvent(){
        paper.init(this);
        cart = findViewById(R.id.btnCart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username == null){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), ShoppingCart.class);
                    startActivity(intent);
                }
            }
        });
        searchBar = findViewById(R.id.txtSearch);
        search = findViewById(R.id.btnSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = searchBar.getText().toString();
                Intent intent = new Intent(getApplicationContext(), StoreHomePage.class);
                intent.putExtra("text", text);
                startActivity(intent);
            }
        });
    }
}