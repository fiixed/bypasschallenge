package com.fiixed.bypasschallenge;

/**
 * Created by abell on 1/16/14.
 */
public class BurgersDogs {
    private String title;
    private double price;
    private int quantity;

    public BurgersDogs(String title, double price, int quantity) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}