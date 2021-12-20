package com.kalai.App.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import java.util.Date;
@Entity
public class Posts {
    @Id
    @SequenceGenerator(
    		name="post_sequence",
    		sequenceName = "post_sequence",
    		allocationSize = 1
    		)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "post_sequence")
    private long postId;
    private String postTitle;
    private String postExcerpt;
    private String postContent;
    private  String postAuthor;
    private  Date postPublishedAt;
    private  boolean postIsPublished;
    private Date postCreatedAt;
    private Date postUpdatedAt;

    public Posts() {

    }

    @Override
	public String toString() {
		return "Posts [postId=" + postId + ", postTitle=" + postTitle + ", postExcerpt=" + postExcerpt
				+ ", postContent=" + postContent + ", postAuthor=" + postAuthor + ", postPublishedAt=" + postPublishedAt
				+ ", postIsPublished=" + postIsPublished + ", postCreatedAt=" + postCreatedAt + ", postUpdatedAt="
				+ postUpdatedAt + "]";
	}
    public Posts(String title,String excerpt,String content,String author) {
    		postTitle=title;
    		postExcerpt=excerpt;
    		postContent=content;
    		postAuthor=author;
    		java.util.Date date=new java.util.Date();
    		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
    		java.sql.Timestamp sqlTime=new java.sql.Timestamp(date.getTime());
    		postPublishedAt=sqlTime;
    		postIsPublished=true;
    		postCreatedAt=sqlTime;
    		postUpdatedAt=sqlTime;
    }
	public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
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

    
}
