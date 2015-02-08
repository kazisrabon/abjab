package com.example.bs_36.cwc1;

/**
 * Created by IIT on 1/30/2015.
 */
public class Favorite {
    private String productName;
    private String image;
    private String price;
    private String location;
    private String contactAddress;
    private String mobile;

    public Favorite(String productName, String image, String price, String location, String contactAddress, String mobile) {
        this.productName = productName;
        this.image = image;
        this.price = price;
        this.location = location;
        this.contactAddress = contactAddress;
        this.mobile = mobile;
    }

    public Favorite(){

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
