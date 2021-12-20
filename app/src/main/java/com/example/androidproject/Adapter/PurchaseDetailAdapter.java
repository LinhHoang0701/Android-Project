package com.example.androidproject.Adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidproject.Entity.PurchaseDetail;
import com.example.androidproject.Entity.PurchaseMaster;
import com.example.androidproject.R;

import java.util.ArrayList;

public class PurchaseDetailAdapter extends BaseAdapter {

    private ArrayList<PurchaseDetail> listProduct;
    private int layout;
    private Activity activity;

    public PurchaseDetailAdapter(ArrayList<PurchaseDetail> listProduct, int layout, Activity activity) {
        this.listProduct = listProduct;
        this.layout = layout;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return listProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView name, provider, id, sub;
        ImageView imgProduct;
        if(convertView == null) {
            convertView = activity.getLayoutInflater().inflate(layout, null);
            name = convertView.findViewById(R.id.lblProductName3);
            provider = convertView.findViewById(R.id.lblProvider3);
            id = convertView.findViewById(R.id.lblProductId3);
            sub = convertView.findViewById(R.id.lblSubPrice3);
            imgProduct = convertView.findViewById(R.id.imgProduct3);

            convertView.setTag(R.id.lblProductName3, name);
            convertView.setTag(R.id.lblProvider3, provider);
            convertView.setTag(R.id.lblProductId3, id);
            convertView.setTag(R.id.lblSubPrice3, sub);
            convertView.setTag(R.id.imgProduct3, imgProduct);

        } else {
            name = (TextView)convertView.getTag(R.id.lblProductName3);
            provider = (TextView)convertView.getTag(R.id.lblProvider3);
            id = (TextView)convertView.getTag(R.id.lblProductId3);
            sub = (TextView)convertView.getTag(R.id.lblSubPrice3);
            imgProduct = (ImageView) convertView.getTag(R.id.imgProduct3);
        }
        final PurchaseDetail purchaseDetail = listProduct.get(position);
        name.setText(purchaseDetail.getProduct().getName() + "");
        provider.setText("Provided by " + purchaseDetail.getProduct().getProvider());
        id.setText("SKU: " + purchaseDetail.getProduct().getProductId());
        sub.setText("$" + purchaseDetail.getProduct().getPrice() + " x " + purchaseDetail.getPurchaseQuantity());
        imgProduct.setImageBitmap(BitmapFactory.decodeByteArray(purchaseDetail.getProduct().getListProductImages().get(0), 0, purchaseDetail.getProduct().getListProductImages().get(0).length));
        return convertView;
    }
}
