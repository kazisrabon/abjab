package com.example.bs_36.cwc1;

/**
 * Created by IIT on 1/30/2015.
 */
public class Cars {
    private String phone;
    private String email;
    private String contact_address;
    private String location;
    private String description;
    private String price;
    private String mobile;
    private String car_name;
    private String image;

    public Cars(String image, String car_name, String description,  String price, String location, String contact_address, String phone,  String mobile,  String email) {
        this.phone = phone;
        this.email = email;
        this.contact_address = contact_address;
        this.location = location;
        this.description = description;
        this.price = price;
        this.mobile = mobile;
        this.car_name = car_name;
        this.image = image;
    }

    public String getImgUrl() {
        return image;
    }

    public void setImgUrl(String imgUrl) {
        this.image = imgUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_address() {
        return contact_address;
    }

    public void setContact_address(String contact_address) {
        this.contact_address = contact_address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }
}
