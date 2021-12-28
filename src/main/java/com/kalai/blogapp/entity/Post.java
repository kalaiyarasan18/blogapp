package com.kalai.blogapp.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Post implements Comparable<Post> {
    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_sequence")
    private long id;
    private String postTitle;
    @Lob
    private String postExcerpt;
    @Lob
    private String postContent;
    private String postAuthor;
    @Temporal(TemporalType.DATE)
    private Date postPublishedAt;
    private boolean postIsPublished;
    @Temporal(TemporalType.DATE)
    private Date postCreatedAt;
    @Temporal(TemporalType.DATE)
    private Date postUpdatedAt;

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name="post_tags",joinColumns = {@JoinColumn(name="postId")},
    inverseJoinColumns = {@JoinColumn(name="tagId")}
    )
    private Set<Tag> tags=new HashSet<>();


    public Post() {
    }

    public Post(String title, String excerpt, String content, String author, Date date) {
    }

    public Post(String title, String excerpt, String content, String author) {
        postTitle = title;
        postExcerpt = excerpt;
        postContent = content;
        postAuthor = author;
        java.util.Date date = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        java.sql.Timestamp sqlTime = new java.sql.Timestamp(date.getTime());
        postPublishedAt = sqlTime;
        postIsPublished = true;
        postCreatedAt = sqlTime;
        postUpdatedAt = sqlTime;
    }

    @Override
    public String toString() {
        return "Posts [postId=" + id + ", postTitle=" + postTitle + ", postExcerpt=" + postExcerpt
                + ", postContent=" + postContent + ", postAuthor=" + postAuthor + ", postPublishedAt=" + postPublishedAt
                + ", postIsPublished=" + postIsPublished + ", postCreatedAt=" + postCreatedAt + ", postUpdatedAt="
                + postUpdatedAt + "]";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostExcerpt() {
        return postExcerpt;
    }

    public void setPostExcerpt(String postExcerpt) {
        this.postExcerpt = postExcerpt;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(String postAuthor) {
        this.postAuthor = postAuthor;
    }

    public Date getPostPublishedAt() {
        return postPublishedAt;
    }

    public void setPostPublishedAt(Date postPublishedAt) {
        this.postPublishedAt = postPublishedAt;
    }

    public boolean isPostIsPublished() {
        return postIsPublished;
    }

    public void setPostIsPublished(boolean postIsPublished) {
        this.postIsPublished = postIsPublished;
    }

    public Date getPostCreatedAt() {
        return postCreatedAt;
    }

    public void setPostCreatedAt(Date postCreatedAt) {
        this.postCreatedAt = postCreatedAt;
    }

    public Date getPostUpdatedAt() {
        return postUpdatedAt;
    }

    public void setPostUpdatedAt(Date postUpdatedAt) {
        this.postUpdatedAt = postUpdatedAt;
    }


    @Override
    public int compareTo(Post o) {
        return this.getPostPublishedAt().compareTo(o.getPostCreatedAt());
    }
}
