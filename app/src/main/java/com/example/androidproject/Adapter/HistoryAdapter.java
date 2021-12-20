package com.example.androidproject.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.Entity.PurchaseMaster;
import com.example.androidproject.R;

import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {

    private ArrayList<PurchaseMaster> listHistory;
    private int layout;
    private Activity activity;

    public HistoryAdapter(ArrayList<PurchaseMaster> listHistory, int layout, Activity activity) {
        this.listHistory = listHistory;
        this.layout = layout;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listHistory.size();
    }

    @Override
    public Object getItem(int position) {
        return listHistory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView title, masterId, date, status;
        ImageView imgStatus;
        if(convertView == null) {
            convertView = activity.getLayoutInflater().inflate(layout, null);
            title = convertView.findViewById(R.id.lblTitle1);
            masterId = convertView.findViewById(R.id.lblMasterId1);
            date = convertView.findViewById(R.id.lblDate1);
            status = convertView.findViewById(R.id.lblStatus1);
            imgStatus = convertView.findViewById(R.id.imgStatus);

            convertView.setTag(R.id.lblTitle1, title);
            convertView.setTag(R.id.lblMasterId1, masterId);
            convertView.setTag(R.id.lblDate1, date);
            convertView.setTag(R.id.lblStatus1, status);
            convertView.setTag(R.id.imgStatus, imgStatus);
        } else {
            title = (TextView)convertView.getTag(R.id.lblTitle1);
            masterId = (TextView)convertView.getTag(R.id.lblMasterId1);
            date = (TextView)convertView.getTag(R.id.lblDate1);
            status = (TextView)convertView.getTag(R.id.lblStatus1);
            imgStatus = (ImageView)convertView.getTag(R.id.imgStatus);
        }
        final PurchaseMaster purchaseMaster = listHistory.get(position);
        title.setText(purchaseMaster.getPurchaseList().get(0).getProduct().getName() + " and " + (purchaseMaster.getPurchaseList().size()-1) + " other product(s)");
        masterId.setText("Code: " + purchaseMaster.getMasterId());
        date.setText("Date: " + purchaseMaster.getPurchaseTime());
        status.setText("Status: " + purchaseMaster.getStatus());
        if(purchaseMaster.getStatus().equals("Delivered")) {
            imgStatus.setImageResource(R.drawable.ic_check_black_24dp);
        } else if(purchaseMaster.getStatus().equals("Cancelled") || purchaseMaster.getStatus().equals("Canceled By User")){
            imgStatus.setImageResource(R.drawable.ic_clear_black_24dp);
        } else if(purchaseMaster.getStatus().equals("Delivering")) {
            imgStatus.setImageResource(R.drawable.ic_airport_shuttle_black_24dp);
        } else if(purchaseMaster.getStatus().equals("Pending")) {
            imgStatus.setImageResource(R.drawable.ic_subdirectory_arrow_left_black_24dp);
        }
        return convertView;
    }

}
