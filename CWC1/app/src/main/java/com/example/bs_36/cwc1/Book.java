package com.example.bs_36.cwc1;

/**
 * Created by IIT on 1/30/2015.
 */
public class Book {
    private String phone;
    private String email;
    private String contact_address;
    private String location;
    private String description;
    private String price;
    private String mobile;
    private String book_name;
    private String genre;
    private String author;
    private String image;

    public Book(String image, String book_name, String description, String genre, String author,  String price, String location, String contact_address, String phone,  String mobile,  String email) {
        this.phone = phone;
        this.email = email;
        this.contact_address = contact_address;
        this.location = location;
        this.description = description;
        this.price = price;
        this.mobile = mobile;
        this.book_name = book_name;
        this.genre = genre;
        this.author = author;
        this.image = image;
    }

    public String getImgUrl() {
        return image;
    }

    public String[] getImgUrls(){
        String[] img = new String[3];
        img[0] = image;
        img[1] = "http://api.androidhive.info/json/movies/1.jpg";
        img[2] = "http://api.androidhive.info/json/movies/2.jpg";

        return img;
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

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
