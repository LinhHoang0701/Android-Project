package com.example.androidproject.Entity;

import java.io.Serializable;

public class Review implements IEntity, Serializable {
    private int productId, rating;
    private String username,comment;

    public Review(int productId, int rating, String username, String comment) {
        this.productId = productId;
        this.rating = rating;
        this.username = username;
        this.comment = comment;
    }

    public Review() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
