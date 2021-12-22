package com.kalai.App.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
@Entity
public class Tag {
    @Id
    private long tagId;
    private String tagName;
    private Date tagCreatedAt;

    public Tag() {
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                ", tagCreatedAt=" + tagCreatedAt +
                ", tagUpdatedAt=" + tagUpdatedAt +
                '}';
    }

    public Tag(long tagId, String tagName, Date tagCreatedAt, Date tagUpdatedAt) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.tagCreatedAt = tagCreatedAt;
        this.tagUpdatedAt = tagUpdatedAt;
    }

    private Date tagUpdatedAt;


    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Date getTagCreatedAt() {
        return tagCreatedAt;
    }

    public void setTagCreatedAt(Date tagCreatedAt) {
        this.tagCreatedAt = tagCreatedAt;
    }

    public Date getTagUpdatedAt() {
        return tagUpdatedAt;
    }

    public void setTagUpdatedAt(Date tagUpdatedAt) {
        this.tagUpdatedAt = tagUpdatedAt;
    }



}
