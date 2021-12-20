package com.example.androidproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.Adapter.PurchaseDetailAdapter;
import com.example.androidproject.DAL.PurchaseSQLiteHelper;
import com.example.androidproject.DAL.UserSQLiteHelper;
import com.example.androidproject.Entity.PurchaseDetail;
import com.example.androidproject.Entity.PurchaseMaster;
import com.example.androidproject.Entity.User;

import java.util.ArrayList;

public class PurchaseDetailActivity extends AppCompatActivity {
    private ListView listView;
    private Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_detail);
        Intent intent = getIntent();
        String masterId = intent.getStringExtra("masterId");
        final PurchaseSQLiteHelper helper = new PurchaseSQLiteHelper(this);
        final PurchaseMaster purchaseMaster = helper.getPurchaseMasterByMasterId(masterId);
        helper.close();
        UserSQLiteHelper helper1 = new UserSQLiteHelper(this);
        User user = helper1.getUserByUsername(purchaseMaster.getUsername());
        helper1.close();
        listView = findViewById(R.id.listProduct2);
        TextView v = findViewById(R.id.lblMasterId2);
        v.setText("Purchase code: " + purchaseMaster.getMasterId());
        v = findViewById(R.id.lblDate2);
        v.setText("Purchase date: " + purchaseMaster.getPurchaseTime());
        v = findViewById(R.id.lblStatus2);
        v.setText("Purchase status " + purchaseMaster.getStatus());
        v = findViewById(R.id.lblName2);
        v.setText("" + user.getFullname());
        v = findViewById(R.id.lblPhone2);
        v.setText("" + user.getPhone());
        v = findViewById(R.id.lblDestination2);
        v.setText("" + purchaseMaster.getDestination());
        v = findViewById(R.id.lblTotal2);
        v.setText("Total: $" + purchaseMaster.getTotal());
        v = findViewById(R.id.lblMethod2);
        v.setText(purchaseMaster.getPaymentMethod());
        ArrayList<PurchaseDetail> purchaseDetails = purchaseMaster.getPurchaseList();
        PurchaseDetailAdapter purchaseDetailAdapter = new PurchaseDetailAdapter(purchaseDetails, R.layout.purchase_detail_layout, this);
        listView.setAdapter(purchaseDetailAdapter);
        setListViewHeightBasedOnChildren(listView);
        cancel = findViewById(R.id.btnCancel2);
        if(!purchaseMaster.getStatus().equals("Pending")) {
            ViewGroup layout = (ViewGroup) cancel.getParent();
            if(null!=layout) //for safety only  as you are doing onClick
                layout.removeView(cancel);
        } else {
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlert(purchaseMaster.getMasterId());
                }
            });
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();if (listAdapter == null) return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
            View.MeasureSpec.UNSPECIFIED);int totalHeight = 0;
        View view = null;for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);if (i == 0) view.setLayoutParams(new
                    ViewGroup.LayoutParams(desiredWidth,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void showAlert(final String masterId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setMessage("You are going to cancel this order!");
        builder.setPositiveButton("Yes, Cancel it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PurchaseSQLiteHelper helper2 = new PurchaseSQLiteHelper(PurchaseDetailActivity.this);
                helper2.cancelPurchase(masterId);
                helper2.close();
                setResult(200);
                Toast.makeText(PurchaseDetailActivity.this, "Order Canceled",Toast.LENGTH_LONG).show();
                finish();
            }
        });
        builder.setNegativeButton("No, Go back", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
}
