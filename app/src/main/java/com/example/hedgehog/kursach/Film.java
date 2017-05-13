package com.example.hedgehog.kursach;

/**
 * Created by hedgehog on 18.04.17.
 */

public class Film {

    private int id;
    private String name;
    private String description = "meh";
    private int ageLimit;
    private int price;

    public Film(int id, String name, String description, int ageLimit, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ageLimit = ageLimit;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        if (description != null) {
            return description;
        } else {
            return "meh";
        }
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + ". \n" + description;
    }
}
