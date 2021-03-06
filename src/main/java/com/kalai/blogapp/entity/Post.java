package com.kalai.blogapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.*;

@Entity
public class Post  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String title;
    @Lob
    private String excerpt;
    @Lob
    private String content;
    private String author;
    @Temporal(TemporalType.DATE)
    private Date publishedAt;
    private boolean isPublished;
    @Temporal(TemporalType.DATE)
    private Date createdAt;
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
    @ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinTable(name = "post_tags", joinColumns = {@JoinColumn(name = "postId")},
            inverseJoinColumns = {@JoinColumn(name = "tagId")}
   )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(cascade=CascadeType.ALL,mappedBy = "post")
    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Post() {
    }

    public Post(String title, String excerpt, String content, String author, Date date) {
    }

    public Post(String title, String excerpt, String content, String author) {
        this.title = title;
        this.excerpt = excerpt;
        this.content = content;
        this.author = author;
        publishedAt = new Date();
        isPublished = true;
        createdAt = new Date();
        updatedAt = new Date();
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        this.isPublished = published;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Posts [postId=" + id + " , postTitle=" + title + ", postExcerpt=" + excerpt
                + ", postContent=" + content + ", postAuthor=" + author + ", postPublishedAt=" + publishedAt
                + ", postIsPublished=" + isPublished + ", postCreatedAt=" + createdAt + ", postUpdatedAt="
                + updatedAt + "]" + "\n";
    }

    public void addComment(Comment comment){
        if(comments==null){
            comments=new ArrayList<>();
        }
        comments.add(comment);
        comment.setPost(this);
    }
}
