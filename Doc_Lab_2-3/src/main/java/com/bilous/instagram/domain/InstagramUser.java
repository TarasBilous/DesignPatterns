package com.bilous.instagram.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A InstagramUser.
 */
@Entity
@Table(name = "instagram_user")
public class InstagramUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private Integer age;

    @Column(name = "sex")
    private String sex;

    @OneToMany(mappedBy = "user")
    private Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "users")
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "following")
    private Set<FollowerFollowing> followers = new HashSet<>();

    @OneToMany(mappedBy = "followedBy")
    private Set<FollowerFollowing> followings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public InstagramUser username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public InstagramUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public InstagramUser password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public InstagramUser age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public InstagramUser sex(String sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public InstagramUser likes(Set<Like> likes) {
        this.likes = likes;
        return this;
    }

    public InstagramUser addLikes(Like like) {
        this.likes.add(like);
        like.setUser(this);
        return this;
    }

    public InstagramUser removeLikes(Like like) {
        this.likes.remove(like);
        like.setUser(null);
        return this;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public InstagramUser posts(Set<Post> posts) {
        this.posts = posts;
        return this;
    }

    public InstagramUser addPosts(Post post) {
        this.posts.add(post);
        post.setUsers(this);
        return this;
    }

    public InstagramUser removePosts(Post post) {
        this.posts.remove(post);
        post.setUsers(null);
        return this;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Set<FollowerFollowing> getFollowers() {
        return followers;
    }

    public InstagramUser followers(Set<FollowerFollowing> followerFollowings) {
        this.followers = followerFollowings;
        return this;
    }

    public InstagramUser addFollowers(FollowerFollowing followerFollowing) {
        this.followers.add(followerFollowing);
        followerFollowing.setFollowing(this);
        return this;
    }

    public InstagramUser removeFollowers(FollowerFollowing followerFollowing) {
        this.followers.remove(followerFollowing);
        followerFollowing.setFollowing(null);
        return this;
    }

    public void setFollowers(Set<FollowerFollowing> followerFollowings) {
        this.followers = followerFollowings;
    }

    public Set<FollowerFollowing> getFollowings() {
        return followings;
    }

    public InstagramUser followings(Set<FollowerFollowing> followerFollowings) {
        this.followings = followerFollowings;
        return this;
    }

    public InstagramUser addFollowing(FollowerFollowing followerFollowing) {
        this.followings.add(followerFollowing);
        followerFollowing.setFollowedBy(this);
        return this;
    }

    public InstagramUser removeFollowing(FollowerFollowing followerFollowing) {
        this.followings.remove(followerFollowing);
        followerFollowing.setFollowedBy(null);
        return this;
    }

    public void setFollowings(Set<FollowerFollowing> followerFollowings) {
        this.followings = followerFollowings;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InstagramUser)) {
            return false;
        }
        return id != null && id.equals(((InstagramUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InstagramUser{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", age=" + getAge() +
            ", sex='" + getSex() + "'" +
            "}";
    }
}
