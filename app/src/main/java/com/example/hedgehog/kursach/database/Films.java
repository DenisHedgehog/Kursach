package com.example.hedgehog.kursach.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hedgehog on 11.05.17.
 */

@Entity
public class Films {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String descriprion;

    private int duration;

    @NotNull
    private int ageLimit;

    private int price;

    @Generated(hash = 1306674478)
    public Films(Long id, @NotNull String name, @NotNull String descriprion,
                 int duration, int ageLimit, int price) {
        this.id = id;
        this.name = name;
        this.descriprion = descriprion;
        this.duration = duration;
        this.ageLimit = ageLimit;
        this.price = price;
    }

    @Generated(hash = 1270431773)
    public Films() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriprion() {
        return this.descriprion;
    }

    public void setDescriprion(String descriprion) {
        this.descriprion = descriprion;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAgeLimit() {
        return this.ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
