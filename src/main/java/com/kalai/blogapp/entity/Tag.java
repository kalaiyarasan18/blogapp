package com.kalai.blogapp.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tag {
    @Id
    @SequenceGenerator(
            name = "tagSequence",
            sequenceName = "tagSequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tagSequence")
    private long id;
    @Column(unique = true)
    private String tagName;
    @Temporal(TemporalType.DATE)
    private Date tagCreatedAt;
    @Temporal(TemporalType.DATE)
    private Date tagUpdatedAt;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tags")
    private Set<Post> posts = new HashSet<>();

    public Tag() {
    }

    public Tag(long id, String tagName, Date tagCreatedAt, Date tagUpdatedAt) {
        this.id = id;
        this.tagName = tagName;
        this.tagCreatedAt = tagCreatedAt;
        this.tagUpdatedAt = tagUpdatedAt;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + id +
                ", tagName='" + tagName + '\'' +
                ", tagCreatedAt=" + tagCreatedAt +
                ", tagUpdatedAt=" + tagUpdatedAt +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
