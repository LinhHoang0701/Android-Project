package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.widget.Toolbar;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.Adapter.ProductAdapter;
import com.example.androidproject.DAL.ProductSQLiteHelper;
import com.example.androidproject.Entity.Product;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class StoreHomePage extends MainActivity
        implements  NavigationView.OnNavigationItemSelectedListener {

    private ListView productListView;
    private List<Product> products = new ArrayList<>();
    private Spinner spCategories;
    private Spinner spFilter;
    private String keyword = null;
    private String filter = null;
    private String categoriy = null;
    private String fullname;
    private String username;
    private TextView txtFullname;
    private TextView txtUsername;


    private void loadProductToListView(String keyword, String filter, String category){

        ProductSQLiteHelper productSQLiteHelper = new ProductSQLiteHelper(this);

        products = productSQLiteHelper.getProducts(keyword, filter, category);

        ProductAdapter adapter= new ProductAdapter(this, R.layout.store_home_layout, products);
        productListView.setAdapter(adapter);
    }

    private void loadAllCategories(){

        List<String> categories = new ArrayList<String>();
        ProductSQLiteHelper productSQLiteHelper = new ProductSQLiteHelper(this);
        categories = productSQLiteHelper.getAllCategory();
        categories.add("All Categories");
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        int pos = categoriesAdapter.getPosition("All Categories");
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategories.setAdapter(categoriesAdapter);
        spCategories.setSelection(pos);

        //OnItemSelectedListener
        spCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemFilterCategory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void loadAllFilter(){

        List<String> filters = new ArrayList<String>();
        filters.add("No filter");
        filters.add("Latest");
        filters.add("Oldest");
        filters.add("Cheapest");
        filters.add("Highest rating");
        filters.add("Lowest rating");
        ArrayAdapter<String> filtersAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, filters);
        filtersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFilter.setAdapter(filtersAdapter);

        //OnItemSelectedListener
        spFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemFilterCategory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void selectedItemFilterCategory(){
        String filter = spFilter.getSelectedItem().toString();
        String category = spCategories.getSelectedItem().toString();
        if (filter.equals("No filter")){
            filter = null;
        }

        if (category.equals("All Categories")){
            category = null;
        }
        loadProductToListView(keyword, filter, category);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_home_page);
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


        spCategories = findViewById(R.id.spCategories);
        spFilter = findViewById(R.id.spFilter);
        productListView = findViewById(R.id.listProduct);

        loadAllCategories();
        loadAllFilter();
        initEvent();

        Intent intent = getIntent();
        if (intent != null){
            keyword = intent.getStringExtra("text");
        }
        loadProductToListView(keyword, filter, categoriy);

        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StoreHomePage.this, DetailProduct.class);
                intent.putExtra("product", products.get(position).getProductId());
                startActivity(intent);
            }
        });
    }

}