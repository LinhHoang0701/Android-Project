package com.example.androidproject.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import com.example.androidproject.Entity.Product;



import java.util.ArrayList;

public class ProductSQLiteHelper extends BaseSQLiteHelper<Product> {
    private static final String TAG = "DAL PRODUCT: ";
    public ProductSQLiteHelper(Context context) {
        super(context);
    }


    public Product getProductById(int productId) {

        Product product = null;
        String sqlGetProduct = "SELECT * FROM Product WHERE product_id = ?";
        Cursor cursor = getData(sqlGetProduct, new String[] {String.valueOf(productId)});
        if(cursor.moveToNext()) {
            product = new Product();
            product.setName(cursor.getString(cursor.getColumnIndex("name")));
            product.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            product.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            product.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
            product.setProductId(cursor.getInt(cursor.getColumnIndex("product_id")));
            product.setProvider(cursor.getString(cursor.getColumnIndex("provider")));
            product.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
            product.setRatingCount(cursor.getInt(cursor.getColumnIndex("rating_count")));
            product.setTime(cursor.getString(cursor.getColumnIndex("time")));
            //get list of product images
            ArrayList<byte[]> listProductImages = new ArrayList<>();
            String sqlGetProductImages = "SELECT * FROM ProductImage WHERE product_id = ?";
            Cursor cursor1 = getData(sqlGetProductImages, new String[]{String.valueOf(product.getProductId())});
            while (cursor1.moveToNext()) {
                listProductImages.add(cursor1.getBlob(cursor1.getColumnIndex("image")));
            }
            product.setListProductImages(listProductImages);
        }
        return product;
    }

    public ArrayList<String> getAllCategory() {
        ArrayList<String> categories = new ArrayList<>();
        Cursor cursor = getData("SELECT category FROM Product GROUP BY category", null);
        while (cursor.moveToNext()) {
            categories.add(cursor.getString(cursor.getColumnIndex("category")));
        }
        return categories;
    }


    //return specific search product result
    public ArrayList<Product> getProducts(@Nullable String keyword, @Nullable String filter, @Nullable String category) {
        //search condition
        String[] selectionArgs = null;
        String selection = null;
        if(keyword != null && category != null && !keyword.trim().isEmpty() && !category.trim().isEmpty()) {
            selection = "name LIKE ? AND category = ?";
            selectionArgs = new String[] {keyword.trim()+"%",category};
        } else if(keyword != null && !keyword.trim().isEmpty()) {
            selection = "name LIKE ?";
            selectionArgs = new String[] {keyword.trim()+"%"};
        } else if(category != null && !category.trim().isEmpty()) {
            selection = "category = ?";
            selectionArgs = new String[]{category};
        }
        //filter condtion
        if(filter != null) {

            switch (filter) {
                case "Latest": {
                    filter = "time DESC";
                    break;
                }
                case "Oldest": {
                    filter = "time ASC";
                    break;
                }
                case "Cheapest": {
                    filter = "price ASC";
                    break;
                }
                case "Highest rating": {
                    filter = "rating DESC";
                    break;
                }
                case "Lowest rating": {
                    filter = "rating ASC";
                    break;
                }
                default:
                    break;
            }
        }

        ArrayList<Product> searchResult = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("Product",
                new String[]{"product_id","name","price","description","category","provider","rating","rating_count","time"},
                selection,
                selectionArgs,
                null,
                null,
                filter);
        while (cursor.moveToNext()) {
            Product product = new Product();
            product.setName(cursor.getString(cursor.getColumnIndex("name")));
            product.setCategory(cursor.getString(cursor.getColumnIndex("category")));
            product.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            product.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
            product.setProductId(cursor.getInt(cursor.getColumnIndex("product_id")));
            product.setProvider(cursor.getString(cursor.getColumnIndex("provider")));
            product.setRating(cursor.getFloat(cursor.getColumnIndex("rating")));
            product.setRatingCount(cursor.getInt(cursor.getColumnIndex("rating_count")));
            product.setTime(cursor.getString(cursor.getColumnIndex("time")));
            //get list of product images
            ArrayList<byte[]> listProductImages = new ArrayList<>();
            String sqlGetProductImages = "SELECT * FROM ProductImage WHERE product_id = ?";
            Cursor cursor1 = getData(sqlGetProductImages, new String[]{String.valueOf(product.getProductId())});
            while (cursor1.moveToNext()) {
                listProductImages.add(cursor1.getBlob(cursor1.getColumnIndex("image")));
            }
            cursor1.close();
            product.setListProductImages(listProductImages);
            searchResult.add(product);
        }
        cursor.close();
        return searchResult;
    }



    public void insertProductImage(int productId,Drawable image) {
        String sql = "INSERT INTO ProductImage (product_id, image) VALUES (?,?)";
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, String.valueOf(productId));
        statement.bindBlob(2, imageViewToByte(image));
        statement.executeInsert();
        db.close();
    }
}




