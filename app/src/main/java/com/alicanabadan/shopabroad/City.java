package com.alicanabadan.shopabroad;

/**
 * Created by Alican on 3/15/2017.
 */

public class City {

    private String name;
    private String code;

    public City() {
    }

    public City(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
