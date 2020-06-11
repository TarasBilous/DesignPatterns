package com.bilous.instagram.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "post")
    private Set<Like> likedBies = new HashSet<>();

    @OneToMany(mappedBy = "post")
    private Set<Hashtag> hashtags = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("posts")
    private InstagramUser users;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Post photoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Post date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public Post location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Post comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Post addComments(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
        return this;
    }

    public Post removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setPost(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Like> getLikedBies() {
        return likedBies;
    }

    public Post likedBies(Set<Like> likes) {
        this.likedBies = likes;
        return this;
    }

    public Post addLikedBy(Like like) {
        this.likedBies.add(like);
        like.setPost(this);
        return this;
    }

    public Post removeLikedBy(Like like) {
        this.likedBies.remove(like);
        like.setPost(null);
        return this;
    }

    public void setLikedBies(Set<Like> likes) {
        this.likedBies = likes;
    }

    public Set<Hashtag> getHashtags() {
        return hashtags;
    }

    public Post hashtags(Set<Hashtag> hashtags) {
        this.hashtags = hashtags;
        return this;
    }

    public Post addHashtags(Hashtag hashtag) {
        this.hashtags.add(hashtag);
        hashtag.setPost(this);
        return this;
    }

    public Post removeHashtags(Hashtag hashtag) {
        this.hashtags.remove(hashtag);
        hashtag.setPost(null);
        return this;
    }

    public void setHashtags(Set<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public InstagramUser getUsers() {
        return users;
    }

    public Post users(InstagramUser instagramUser) {
        this.users = instagramUser;
        return this;
    }

    public void setUsers(InstagramUser instagramUser) {
        this.users = instagramUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return id != null && id.equals(((Post) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", photoUrl='" + getPhotoUrl() + "'" +
            ", date='" + getDate() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
