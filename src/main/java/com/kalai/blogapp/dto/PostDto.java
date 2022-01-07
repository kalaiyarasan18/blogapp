package com.kalai.blogapp.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class PostDto implements Serializable {
    private final long id;
    private final String title;
    private final String excerpt;
    private final String content;
    private final String author;
    private final Date publishedAt;
    private final boolean isPublished;
    private final Date createdAt;
    private final Date updatedAt;
    private final Set<TagDto> tags;

    public PostDto(long id, String title, String excerpt, String content, String author, Date publishedAt, boolean isPublished, Date createdAt, Date updatedAt, Set<TagDto> tags) {
        this.id = id;
        this.title = title;
        this.excerpt = excerpt;
        this.content = content;
        this.author = author;
        this.publishedAt = publishedAt;
        this.isPublished = isPublished;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public boolean getIsPublished() {
        return isPublished;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Set<TagDto> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostDto entity = (PostDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.title, entity.title) &&
                Objects.equals(this.excerpt, entity.excerpt) &&
                Objects.equals(this.content, entity.content) &&
                Objects.equals(this.author, entity.author) &&
                Objects.equals(this.publishedAt, entity.publishedAt) &&
                Objects.equals(this.isPublished, entity.isPublished) &&
                Objects.equals(this.createdAt, entity.createdAt) &&
                Objects.equals(this.updatedAt, entity.updatedAt) &&
                Objects.equals(this.tags, entity.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, excerpt, content, author, publishedAt, isPublished, createdAt, updatedAt, tags);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "title = " + title + ", " +
                "excerpt = " + excerpt + ", " +
                "content = " + content + ", " +
                "author = " + author + ", " +
                "publishedAt = " + publishedAt + ", " +
                "isPublished = " + isPublished + ", " +
                "createdAt = " + createdAt + ", " +
                "updatedAt = " + updatedAt + ", " +
                "tags = " + tags + ")";
    }
}
