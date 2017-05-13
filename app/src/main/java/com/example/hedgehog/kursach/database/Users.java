package com.example.hedgehog.kursach.database;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hedgehog on 10.05.17.
 */

@Entity
public class Users {

    @Id(autoincrement = true)
    private Long userId;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private int age;

    @Generated(hash = 1179483470)
    public Users(Long userId, @NotNull String email, @NotNull String password,
                 int age) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    @Generated(hash = 2146996206)
    public Users() {
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
