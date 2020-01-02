package com.shahnizarbaloch.forifixer.model;

public class itemDetail {
    private String itemName,itemPrice;
    public itemDetail(String itemName, String itemPrice){
        this.itemName=itemName;
        this.itemPrice=itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }
}
