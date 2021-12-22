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
    private Date tagUpdatedAt;


}
