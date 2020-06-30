package com.abrarlohia.dressmaterialcatalog;

public class Products {
    private int p_id;
    //product id = p_id
    private String title, shortDesc;
    private double price;
    private int MimageRes;

    public Products(int img, int pro_id, String tit, String desc, Double pr) {
        MimageRes = img;
        p_id = pro_id;
        title = tit;
        shortDesc = desc;
        price = pr;
    }

    public int getP_id() {
        return p_id;
    }

    public String getTitle() {
        return title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public double getPrice() {
        return price;
    }

    public int getMimageRes() {
        return MimageRes;
    }
}
