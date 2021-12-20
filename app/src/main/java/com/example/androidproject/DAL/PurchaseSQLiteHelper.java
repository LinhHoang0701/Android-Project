package com.example.androidproject.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.Nullable;

import com.example.androidproject.Entity.Product;
import com.example.androidproject.Entity.PurchaseDetail;
import com.example.androidproject.Entity.PurchaseMaster;
import com.example.androidproject.Entity.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PurchaseSQLiteHelper extends BaseSQLiteHelper {
    private static final String TAG = "DAL ORDER";

    public PurchaseSQLiteHelper(Context context) {
        super(context);
    }

    public ArrayList<PurchaseDetail> getAllPurchaseDetailByMasterId(String masterId) {
        ArrayList<PurchaseDetail> purchaseDetailList = new ArrayList<>();
        Cursor cursor = getData("SELECT * FROM PurchaseDetail WHERE master_id = ?", new String[]{masterId});
        ProductSQLiteHelper productSQLiteHelper = new ProductSQLiteHelper(context);
        while (cursor.moveToNext()) {
            PurchaseDetail purchaseDetail = new PurchaseDetail();
            Product product = productSQLiteHelper.getProductById(cursor.getInt(cursor.getColumnIndex("product_id")));
            purchaseDetail.setPurchaseQuantity(cursor.getInt(cursor.getColumnIndex("purchase_quantity")));
            purchaseDetail.setProduct(product);
            purchaseDetailList.add(purchaseDetail);
        }
        return purchaseDetailList;
    }

    public ArrayList<PurchaseMaster> getAllPurchaseMasterByUsername (String username) {
        ArrayList<PurchaseMaster> purchaseMasterList = new ArrayList<>();
        Cursor cursor = getData("SELECT * FROM PurchaseMaster WHERE username = ? ORDER BY purchase_time",new String[]{username});
        while (cursor.moveToNext()) {
            PurchaseMaster purchaseMaster = new PurchaseMaster();
            purchaseMaster.setDestination(cursor.getString(cursor.getColumnIndex("destination")));
            String masterId = cursor.getString(cursor.getColumnIndex("master_id"));
            purchaseMaster.setMasterId(masterId);
            purchaseMaster.setPurchaseTime(cursor.getString(cursor.getColumnIndex("purchase_time")));
            purchaseMaster.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            purchaseMaster.setPurchaseList(getAllPurchaseDetailByMasterId(masterId));
            purchaseMaster.setUsername(username);
            purchaseMaster.setPaymentMethod(cursor.getString(cursor.getColumnIndex("payment_method")));
            purchaseMasterList.add(purchaseMaster);
        }
        return purchaseMasterList;
    }

    public PurchaseMaster getPurchaseMasterByMasterId (String masterId) {
        Cursor cursor = getData("SELECT * FROM PurchaseMaster WHERE master_id = ?",new String[]{masterId});
        if (cursor.moveToNext()) {
            PurchaseMaster purchaseMaster = new PurchaseMaster();
            purchaseMaster.setDestination(cursor.getString(cursor.getColumnIndex("destination")));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            purchaseMaster.setMasterId(masterId);
            purchaseMaster.setPurchaseTime(cursor.getString(cursor.getColumnIndex("purchase_time")));
            purchaseMaster.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            purchaseMaster.setPurchaseList(getAllPurchaseDetailByMasterId(masterId));
            purchaseMaster.setPaymentMethod(cursor.getString(cursor.getColumnIndex("payment_method")));
            purchaseMaster.setUsername(username);
            return purchaseMaster;
        }
        return new PurchaseMaster();
    }

    public void makePurchase(String username,@Nullable String destination, HashMap<Integer, Integer> shoppingCart, String paymentMethod) {
        if(!shoppingCart.isEmpty()) {
            User user = new UserSQLiteHelper(context).getUserByUsername(username);
            if(destination == null || destination.trim().isEmpty()) {
                destination = user.getAddress();
            }
            String masterId = username + String.valueOf(new Date().getTime());
            SQLiteDatabase db = getWritableDatabase();
            String masterSql = "INSERT INTO PurchaseMaster (master_id, username, destination, payment_method) VALUES (?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(masterSql);
            statement.clearBindings();
            statement.bindString(1, masterId);
            statement.bindString(2, username);
            statement.bindString(3, destination);
            statement.bindString(4, paymentMethod);
            statement.executeInsert();
            statement.close();
            for(Integer productId: shoppingCart.keySet()) {
                String detailSql = "INSERT INTO PurchaseDetail (master_id, product_id, purchase_quantity) VALUES (?, ?, ?)";
                statement = db.compileStatement(detailSql);
                statement.clearBindings();
                statement.bindString(1, masterId);
                statement.bindLong(2, productId);
                statement.bindLong(3, shoppingCart.get(productId));
                statement.executeInsert();
                statement.close();
            }
            db.close();
        }
    }
    public void cancelPurchase(String masterId) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE PurchaseMaster SET status = 'Canceled By User' WHERE master_id = ? AND status = 'Pending' COLLATE NOCASE";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, masterId);
        statement.executeUpdateDelete();
        statement.close();
        db.close();
    }

}
