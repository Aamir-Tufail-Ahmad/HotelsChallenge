package com.example.mmthotels;

public class HotelsClass {

    private String name;

    private double rating;

    private boolean favourite;

    private String imageurl;

    private String location;

    private String price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public HotelsClass(String name,double rating,boolean fav,String imgurl,String location,String price){
        this.name=name;
        this.rating=rating;
        this.favourite=fav;
        this.imageurl=imgurl;
        this.location=location;
        this.price=price;
    }
}
