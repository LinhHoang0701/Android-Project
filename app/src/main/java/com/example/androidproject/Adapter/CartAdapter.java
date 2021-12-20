package com.example.androidproject.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidproject.DAL.ProductSQLiteHelper;
import com.example.androidproject.Entity.PurchaseDetail;
import com.example.androidproject.R;
import com.example.androidproject.Entity.Product;
import com.example.androidproject.ShoppingCart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.paperdb.Paper;

public class CartAdapter extends BaseAdapter {

    private ShoppingCart activity;
    private HashMap<Integer, Integer> map;
    private int layout;
    private Integer[] id;
    ProductSQLiteHelper productSQLiteHelper;
    private final ArrayList data = new ArrayList();
    public float total = 0;
    protected PurchaseDetail purchaseDetail;
    protected List<PurchaseDetail> detailList = new ArrayList<>();

    public CartAdapter(ShoppingCart activity, int layout, HashMap<Integer, Integer> map) {
        this.activity = activity;
        this.map = map;
        this.layout = layout;
        data.addAll(map.entrySet());
    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Map.Entry<Integer, Integer> getItem(int position) {
        return (Map.Entry) data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ImageView imgProduct;
        TextView name;
        TextView price;
        ImageButton minus;
        ImageButton add;
        final TextView quantity;
        final TextView totalPrice;
        Button remove;
        productSQLiteHelper = new ProductSQLiteHelper(activity.getApplicationContext());
        Paper.init(activity.getApplicationContext());

        if (convertView == null){
            convertView = activity.getLayoutInflater().inflate(layout, null);
            imgProduct = convertView.findViewById(R.id.imgProduct);
            name = convertView.findViewById(R.id.txtProductName);
            price = convertView.findViewById(R.id.txtCurrentPrize);
            minus = convertView.findViewById(R.id.btnMinus);
            add = convertView.findViewById(R.id.btnAdd);
            quantity = convertView.findViewById(R.id.txtQuantity);
            totalPrice = convertView.findViewById(R.id.txtTotalPrice);
            remove = convertView.findViewById(R.id.btnRemove);

            convertView.setTag(R.id.imgProduct, imgProduct);
            convertView.setTag(R.id.txtProductName, name);
            convertView.setTag(R.id.txtCurrentPrize, price);
            convertView.setTag(R.id.btnMinus, minus);
            convertView.setTag(R.id.btnAdd, add);
            convertView.setTag(R.id.txtQuantity, quantity);
            convertView.setTag(R.id.txtTotalPrice, totalPrice);
            convertView.setTag(R.id.btnRemove, remove);
        }
        else {
            imgProduct = (ImageView) convertView.getTag(R.id.imgProduct);
            name = (TextView) convertView.getTag(R.id.txtProductName);
            price = (TextView) convertView.getTag(R.id.txtCurrentPrize);
            minus = (ImageButton) convertView.getTag(R.id.btnMinus);
            add = (ImageButton) convertView.getTag(R.id.btnAdd);
            quantity = (TextView) convertView.getTag(R.id.txtQuantity);
            totalPrice = (TextView) convertView.getTag(R.id.txtTotalPrice);
            remove = (Button) convertView.getTag(R.id.btnRemove);
        }

        Map.Entry<Integer, Integer> item = (Map.Entry<Integer, Integer>) data.get(position);
        final Product product = productSQLiteHelper.getProductById(item.getKey());
        final int numberOfProduct = item.getValue();
        productSQLiteHelper.close();
        purchaseDetail = new PurchaseDetail(product, numberOfProduct);

        name.setText(product.getName());
        price.setText(String.valueOf(product.getPrice())+ " $");
        totalPrice.setText(String.valueOf(purchaseDetail.getSubTotal()) + "$");
        quantity.setText(String.valueOf(numberOfProduct));

        ArrayList<byte[]> listImg = product.getListProductImages();
        byte[] firstImg = listImg.get(0);
        Bitmap bmp = BitmapFactory.decodeByteArray(firstImg,0, firstImg.length);
        imgProduct.setImageBitmap(bmp);

        detailList.add(purchaseDetail);
        total += product.getPrice() * numberOfProduct;
        activity.setTextTotalPrice(String.valueOf(total));
        if (position == map.size() - 1){
            total = 0;
        }

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = numberOfProduct;
                if (number > 1){
                    number -= 1;
                    quantity.setText(String.valueOf(number));
                    totalPrice.setText(String.valueOf(product.getPrice() * number));
                    map.put(product.getProductId(), number);
                    Paper.book().write("shoppingCart", map);
                    CartAdapter.this.notifyDataSetChanged();
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = numberOfProduct;
                if (number < 10){
                    number += 1;
                    quantity.setText(String.valueOf(number));
                    totalPrice.setText(String.valueOf(product.getPrice() * number));
                    map.put(product.getProductId(), number);
                    Paper.book().write("shoppingCart", map);
                    CartAdapter.this.notifyDataSetChanged();
                }
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.remove(product.getProductId());
                Paper.book().write("shoppingCart", map);
                map = Paper.book().read("shoppingCart");
                CartAdapter.this.notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public float getTotalPrice(){
        map = Paper.book().read("shoppingCart");
        total = 0;
        for (Map.Entry<Integer, Integer> entry :
                map.entrySet()) {
            productSQLiteHelper = new ProductSQLiteHelper(activity.getApplicationContext());
            Product product = productSQLiteHelper.getProductById(entry.getKey());
            productSQLiteHelper.close();
            total += (product.getPrice() * entry.getValue());
        }
        return total;
    }

}
