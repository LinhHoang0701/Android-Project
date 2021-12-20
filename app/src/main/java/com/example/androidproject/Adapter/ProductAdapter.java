package com.example.androidproject.Adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.content.res.AppCompatResources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.Entity.Product;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private Activity activity;
    private List<Product> list;
    private int layout;

    public ProductAdapter(Activity activity, int layout , List<Product> list) {
        this.activity = activity;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView productName;
        TextView currentPrize;
        TextView rateNumber;
        ImageView imgProduct;
        RatingBar ratingBar;

        if (convertView == null){
            convertView = activity.getLayoutInflater().inflate(layout, null);
            productName = convertView.findViewById(R.id.txtProductName);
            currentPrize = convertView.findViewById(R.id.txtCurrentPrize);
            rateNumber = convertView.findViewById(R.id.txtRateNumber);
            imgProduct = convertView.findViewById(R.id.imgProduct);
            ratingBar = convertView.findViewById(R.id.rtbProductRating);
            convertView.setTag(R.id.txtProductName, productName);
            convertView.setTag(R.id.txtCurrentPrize, currentPrize);
            convertView.setTag(R.id.txtRateNumber, rateNumber);
            convertView.setTag(R.id.imgProduct, imgProduct);
            convertView.setTag(R.id.rtbProductRating, ratingBar);
        }
        else {
            productName = (TextView) convertView.getTag(R.id.txtProductName);
            currentPrize = (TextView) convertView.getTag(R.id.txtCurrentPrize);
            rateNumber = (TextView) convertView.getTag(R.id.txtRateNumber);
            imgProduct = (ImageView) convertView.getTag(R.id.imgProduct);
            ratingBar = (RatingBar) convertView.getTag(R.id.rtbProductRating);
        }

        final Product product = list.get(position);
        productName.setText(product.getName());
        currentPrize.setText(product.getPrice()+ " $");
        rateNumber.setText("(" + String.valueOf(product.getRatingCount()) + ")");
        ratingBar.setRating((float) product.getRating());

        ArrayList<byte[]> listImg = product.getListProductImages();
        byte[] firstImg = listImg.get(0);
        Bitmap bmp = BitmapFactory.decodeByteArray(firstImg,0, firstImg.length);
        imgProduct.setImageBitmap(bmp);

        return convertView;
    }
}
