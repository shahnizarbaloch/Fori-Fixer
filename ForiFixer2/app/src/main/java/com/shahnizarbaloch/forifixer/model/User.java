package com.shahnizarbaloch.forifixer.model;

public class User {

    public String name;
    public String number;

        // Default constructor required for calls to
        // DataSnapshot.getValue(User.class)
        public User() {
        }

        public User(String name, String number) {
            this.name = name;
            this.number = number;
        }
    }

