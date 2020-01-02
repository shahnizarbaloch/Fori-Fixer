package com.shahnizarbaloch.forifixer.model;

public class AddToCart {
    private String heading;
    private String subHeading;
    private String price;
    private long id;

    public AddToCart(String heading, String subHeading, String price) {
        this.heading = heading;
        this.subHeading = subHeading;
        this.price = price;
    }


    public AddToCart(String heading, String price) {
        this.heading = heading;
        this.price = price;
    }


    public AddToCart(long id,String heading, String price) {
        this.id=id;
        this.heading = heading;
        this.price = price;
    }

    public String getHeading() {
        return heading;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public long getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }
}
