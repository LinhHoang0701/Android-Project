package com.example.androidproject.Entity;


import java.util.ArrayList;
import java.io.Serializable;
public class Product implements IEntity, Serializable{
    private int productId, ratingCount;
    private String name, description, category, provider, time;
    private float price, rating;
    private ArrayList<byte[]> listProductImages;

    public Product(int productId, int ratingCount, String name, String description, String category, String provider, String time, float price, float rating, ArrayList<byte[]> listProductImages) {
        this.productId = productId;
        this.ratingCount = ratingCount;
        this.name = name;
        this.description = description;
        this.category = category;
        this.provider = provider;
        this.time = time;
        this.price = price;
        this.rating = rating;
        this.listProductImages = listProductImages;
    }


    public Product() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }


    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public ArrayList<byte[]> getListProductImages() {
        return listProductImages;
    }

    public void setListProductImages(ArrayList<byte[]> listProductImages) {
        this.listProductImages = listProductImages;
    }
}
