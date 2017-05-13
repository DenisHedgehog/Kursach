package com.example.hedgehog.kursach.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hedgehog on 11.05.17.
 */

@Entity
public class Subscribtions {

    @Index(unique = true)
    private String subscribtionName;

    @NotNull
    private String description;

    private int price;

    @Generated(hash = 1628193260)
    public Subscribtions(String subscribtionName, @NotNull String description,
                         int price) {
        this.subscribtionName = subscribtionName;
        this.description = description;
        this.price = price;
    }

    @Generated(hash = 1000327185)
    public Subscribtions() {
    }

    public String getSubscribtionName() {
        return this.subscribtionName;
    }

    public void setSubscribtionName(String subscribtionName) {
        this.subscribtionName = subscribtionName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
