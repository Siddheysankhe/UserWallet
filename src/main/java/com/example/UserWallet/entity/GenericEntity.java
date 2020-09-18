package com.example.UserWallet.entity;

import java.io.Serializable;
import java.util.Date;

public interface GenericEntity<PK extends Serializable> extends Serializable {

    PK getId();

    void setId(PK id);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    Date getUpdatedAt();

    void setUpdatedAt(Date updatedAt);

    long getCreatedBy();

    void setCreatedBy(long createdBy);

    long getUpdatedBy();

    void setUpdatedBy(long updatedBy);

}
