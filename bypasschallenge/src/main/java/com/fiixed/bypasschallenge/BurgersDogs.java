package com.fiixed.bypasschallenge;

import java.util.UUID;

/**
 * Created by abell on 1/16/14.
 */
public class BurgersDogs {
    private String title;
    private int quantity;
    private double price;
    private UUID mId;  //NEW

    public BurgersDogs() {
        mId = UUID.randomUUID();  //NEW
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void addQuantity() {
        quantity++;
    }

    public void removeQuantity() {
        quantity = 0;
    }
}
