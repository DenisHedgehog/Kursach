package com.example.hedgehog.kursach.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

/**
 * Created by hedgehog on 11.05.17.
 */

@Entity
public class UserOperations {

    @ToMany(referencedJoinProperty = "userId")
    private Users userId;

    @ToMany(referencedJoinProperty = "operationId")
    private Operations operationId;

}
