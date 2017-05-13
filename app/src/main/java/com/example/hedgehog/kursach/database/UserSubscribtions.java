package com.example.hedgehog.kursach.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.ToMany;

/**
 * Created by hedgehog on 11.05.17.
 */

@Entity
public class UserSubscribtions {

    @ToMany(referencedJoinProperty = "userId")
    private Users userId;

    @ToMany(referencedJoinProperty = "subscribtionName")
    private Subscribtions subscribtionName;

}
