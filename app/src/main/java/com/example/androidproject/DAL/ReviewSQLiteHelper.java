package com.example.androidproject.DAL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.Nullable;

import com.example.androidproject.Entity.Review;

import java.util.ArrayList;

public class ReviewSQLiteHelper extends BaseSQLiteHelper<Review> {
    public ReviewSQLiteHelper(Context context) {
        super(context);
    }

    public ArrayList<Review> getAllReviewOfProduct(int productId, @Nullable String filter) {
        if(filter == null || filter.trim().isEmpty()) {
            filter = "rating";
        } else {
            if(filter.equals("Top Reviews")) {
                filter = "rating";
            } else if(filter.equals("Most Recent")) {
                filter = "time";
            }
        }
        ArrayList<Review> reviewList = new ArrayList<>();
        String sql = "SELECT * FROM Review WHERE product_id = ? ORDER BY ? DESC";
        Cursor cursor = getData(sql, new String[]{String.valueOf(productId),filter});
        while (cursor.moveToNext()) {
            Review review = new Review();
            review.setComment(cursor.getString(cursor.getColumnIndex("comment")));
            review.setProductId(cursor.getInt(cursor.getColumnIndex("product_id")));
            review.setRating(cursor.getInt(cursor.getColumnIndex("rating")));
            review.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            reviewList.add(review);
        }
        return reviewList;
    }

    public boolean isOwned(int productId, String username) {
        //verify user has owned the product
        Cursor cursor = getData("SELECT count(*) as Owned FROM PurchaseDetail WHERE product_id = ? AND master_id IN \n" +
                "(SELECT master_id FROM PurchaseMaster WHERE username = ? AND status = 'delivered' COLLATE NOCASE)", new String[]{String.valueOf(productId),username});
        if(cursor.moveToNext())  {
            int owned = cursor.getInt(cursor.getColumnIndex("Owned"));
            if(owned == 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Review getReviewByUsernameAndProductId(String username, int productId) {
        Review review = null;
        Cursor cursor = getData("SELECT * FROM Review WHERE username = ? AND product_id = ?", new String[]{username, String.valueOf(productId)});
        if(cursor.moveToNext()) {
            review = new Review();
            review.setUsername(username);
            review.setProductId(productId);
            review.setRating(cursor.getInt(cursor.getColumnIndex("rating")));
            review.setComment(cursor.getString(cursor.getColumnIndex("comment")));
        }
        return review;
    }

    public void updateReview(int productId, String username, String comment, int rating) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE Review SET rating = ?, comment = ? WHERE username = ? AND product_id = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindLong(1, rating);
        statement.bindString(2, comment);
        statement.bindString(3, username);
        statement.bindLong(4, productId);
        statement.executeUpdateDelete();
        statement.close();
        db.close();
    }

    public void insertReview(int productId, String username, String comment, int rating) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO Review (username, product_id, rating, comment) VALUES (?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, username);
        statement.bindLong(2, productId);
        statement.bindLong(3, rating);
        statement.bindString(4, comment);
        statement.executeInsert();
        statement.close();
        db.close();
    }

    public void deleteReview(int productId, String username) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM Review WHERE username = ? AND product_id = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, username);
        statement.bindLong(2, productId);
        statement.executeUpdateDelete();
        statement.close();
        db.close();
    }

    public ArrayList<Review> getAllUserReview(String username) {
        ArrayList<Review> reviewList = new ArrayList<>();
        String sql = "SELECT * FROM Review WHERE username = ? ORDER BY time DESC";
        Cursor cursor = getData(sql, new String[]{username});
        while (cursor.moveToNext()) {
            Review review = new Review();
            review.setComment(cursor.getString(cursor.getColumnIndex("comment")));
            review.setProductId(cursor.getInt(cursor.getColumnIndex("product_id")));
            review.setRating(cursor.getInt(cursor.getColumnIndex("rating")));
            review.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            reviewList.add(review);
        }
        return reviewList;
    }
}
