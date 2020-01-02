package com.shahnizarbaloch.forifixer.model;

public class getOrderBy {

    private String id,Name,Email,Number,Location,Total;
    public getOrderBy(String id, String Name, String Email, String Number, String Location, String Total){
        this.id=id;
        this.Name=Name;
        this.Email=Email;
        this.Number=Number;
        this.Location=Location;
        this.Total=Total;
    }
    public getOrderBy(){

    }



    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getNumber() {
        return Number;
    }

    public String getLocation() {
        return Location;
    }

    public String getTotal() {
        return Total;
    }

    public String getId() {
        return id;
    }
}
