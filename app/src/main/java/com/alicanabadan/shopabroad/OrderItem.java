package com.alicanabadan.shopabroad;


import java.sql.Blob;

/**
 * Created by Alican on 3/19/2017.
 */

public class OrderItem {

    private int id;
    private String name;
    private String fromPort;
    private String toPort;
    private double price;
    private String description;
    private byte[] image;
    private String isGranted;
    private String grantedUser;

    public static String ID = "id";
    public static String NAME = "name";
    public static String FROMPORT = "fromPort";
    public static String TOPORT = "toPort";
    public static String PRICE = "price";
    public static String DESCRIPTION = "description";
    public static String IMAGE = "image";
    public static String IS_GRANTED = "isGranted";
    public static String GRANTED_USER = "grantedUser";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFromPort() {
        return fromPort;
    }

    public void setFromPort(String fromPort) {
        this.fromPort = fromPort;
    }

    public String getToPort() {
        return toPort;
    }

    public void setToPort(String toPort) {
        this.toPort = toPort;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsGranted() {
        return isGranted;
    }

    public void setIsGranted(String isGranted) {
        this.isGranted = isGranted;
    }

    public String getGrantedUser() {
        return grantedUser;
    }

    public void setGrantedUser(String grantedUser) {
        this.grantedUser = grantedUser;
    }
}
