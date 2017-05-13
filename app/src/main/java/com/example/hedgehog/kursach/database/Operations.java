package com.example.hedgehog.kursach.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by hedgehog on 11.05.17.
 */

@Entity
public class Operations {

    @Id(autoincrement = true)
    private Long operationId;

    @NotNull
    private String description;

    @Generated(hash = 916891119)
    public Operations(Long operationId, @NotNull String description) {
        this.operationId = operationId;
        this.description = description;
    }

    @Generated(hash = 1027427964)
    public Operations() {
    }

    public Long getOperationId() {
        return this.operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
