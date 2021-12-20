package com.example.androidproject.Entity;

import java.io.Serializable;

public class PurchaseDetail implements IEntity, Serializable {
    private int purchaseQuantity;
    private Product product;

    public PurchaseDetail() {

    }

    public PurchaseDetail(Product product, int purchaseQuantity){
        this.product = product;
        this.purchaseQuantity = purchaseQuantity;
    }

    public float getSubTotal() {
        return this.product.getPrice() * this.purchaseQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }
}
