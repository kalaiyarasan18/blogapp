package com.kalai.App.entity;

import javax.persistence.*;
import java.util.Date;
@Entity
public class Comment {

    @Id
    @SequenceGenerator(
            name="comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "comment_sequence")
    private long commentId;
    private String commenterName;
    private String commenterEmail;
    private String commentContent;
    private long commentedPostId;
    private Date commentCreatedAt;
    private Date commentUpdatedAt;

    public Comment() {
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getCommenterName() {
        return commenterName;
    }

    public void setCommenterName(String commenterName) {
        this.commenterName = commenterName;
    }

    public String getCommenterEmail() {
        return commenterEmail;
    }

    public void setCommenterEmail(String commenterEmail) {
        this.commenterEmail = commenterEmail;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public long getCommentedPostId() {
        return commentedPostId;
    }

    public void setCommentedPostId(long commentedPostId) {
        this.commentedPostId = commentedPostId;
    }

    public Date getCommentCreatedAt() {
        return commentCreatedAt;
    }

    public void setCommentCreatedAt(Date commentCreatedAt) {
        this.commentCreatedAt = commentCreatedAt;
    }

    public Comment(String name,String email,String comment, long postId, Date createdAt, Date updatedAt){
        this.commenterName=name;
        this.commenterEmail=email;
        this.commentContent=comment;
        this.commentedPostId=postId;
        this.commentCreatedAt=createdAt;
        this.commentUpdatedAt=updatedAt;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "commentId=" + commentId +
                ", commenterName='" + commenterName + '\'' +
                ", commenterEmail='" + commenterEmail + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", commentedPostId=" + commentedPostId +
                ", commentCreatedAt=" + commentCreatedAt +
                ", commentUpdatedAt=" + commentUpdatedAt +
                '}';
    }

    public Date getCommentUpdatedAt() {
        return commentUpdatedAt;
    }

    public void setCommentUpdatedAt(Date commentUpdatedAt) {
        this.commentUpdatedAt = commentUpdatedAt;
    }


}
