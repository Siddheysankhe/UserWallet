package com.example.UserWallet.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity<PK extends Serializable> implements GenericEntity<PK>, Cloneable {

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public long getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public long getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(long updatedBy) {
        this.updatedBy = updatedBy;
    }


    @Column (name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;

    @Column (name = "created_by")
    private long createdBy;

    @Column (name = "updated_by")
    private long updatedBy;

    @Column(name = "deleted")
    private int deleted;

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}
