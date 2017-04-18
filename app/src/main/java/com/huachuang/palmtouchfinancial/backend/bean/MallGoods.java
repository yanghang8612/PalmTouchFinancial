package com.huachuang.palmtouchfinancial.backend.bean;

/**
 * Created by Asuka on 2017/3/31.
 */

public class MallGoods {

    private String name;
    private int price;
    private String imagePath;
    private String description;

    public MallGoods(String name, int price, String imagePath, String description) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
