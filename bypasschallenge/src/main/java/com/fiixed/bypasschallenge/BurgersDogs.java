package com.fiixed.bypasschallenge;

/**
 * Created by abell on 1/16/14.
 */
public class BurgersDogs {
    private String title;
    private int quantity;
    private double price;


    public BurgersDogs(String title, int quantity, double price) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
