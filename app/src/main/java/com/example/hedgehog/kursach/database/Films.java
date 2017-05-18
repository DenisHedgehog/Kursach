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
    private Long filmId;

    @NotNull
    private String name;

    private String genres;

    @NotNull
    private String description;

    private int year;

    @NotNull
    private int ageLimit;

    private int price;

    private String imageUrl;

    @Generated(hash = 563905788)
    public Films(Long filmId, @NotNull String name, String genres,
                 @NotNull String description, int year, int ageLimit, int price,
                 String imageUrl) {
        this.filmId = filmId;
        this.name = name;
        this.genres = genres;
        this.description = description;
        this.year = year;
        this.ageLimit = ageLimit;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    @Generated(hash = 1270431773)
    public Films() {
    }

    public Long getId() {
        return this.filmId;
    }

    public void setId(Long id) {
        this.filmId = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescriprion(String description) {
        this.description = description;
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

    public Long getFilmId() {
        return this.filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenres() {
        return this.genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
