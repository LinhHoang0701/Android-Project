package com.example.androidproject;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidproject.Adapter.HistoryAdapter;
import com.example.androidproject.DAL.PurchaseSQLiteHelper;
import com.example.androidproject.Entity.PurchaseMaster;

import java.util.ArrayList;
import java.util.HashMap;

public class PurchaseActivity extends AppCompatActivity {

    private ArrayList<PurchaseMaster> listHistory;
    private String username;
    private ListView listViewHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        username = "duccmse06002";
//        PurchaseSQLiteHelper helper = new PurchaseSQLiteHelper(this);
//        HashMap<Integer,Integer> cart = new HashMap<>();
//        cart.put(1, 2);
//        cart.put(2, 1);
//        cart.put(5, 13);
//        helper.makePurchase("duccmse06002","acdc",cart);
//        helper.close();
        loadHistoryToView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadHistoryToView() {
        listViewHistory = findViewById(R.id.listviewHistory);
        listHistory = new ArrayList<>();
        PurchaseSQLiteHelper helper = new PurchaseSQLiteHelper(this);
        listHistory = helper.getAllPurchaseMasterByUsername(username);
        helper.close();
        HistoryAdapter historyAdapter = new HistoryAdapter(listHistory, R.layout.history_layout, this);

        listViewHistory.setAdapter(historyAdapter);
        listViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PurchaseActivity.this,PurchaseDetailActivity.class);
                intent.putExtra("masterId", listHistory.get(position).getMasterId());
                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == 200) {
            finish();
            startActivity(getIntent());
        }
    }
}
