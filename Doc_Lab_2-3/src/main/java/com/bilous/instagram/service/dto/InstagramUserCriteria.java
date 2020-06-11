package com.bilous.instagram.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.bilous.instagram.domain.InstagramUser} entity. This class is used
 * in {@link com.bilous.instagram.web.rest.InstagramUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /instagram-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InstagramUserCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter username;

    private StringFilter email;

    private StringFilter password;

    private IntegerFilter age;

    private StringFilter sex;

    private LongFilter likesId;

    private LongFilter postsId;

    private LongFilter followersId;

    private LongFilter followingId;

    public InstagramUserCriteria() {
    }

    public InstagramUserCriteria(InstagramUserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.username = other.username == null ? null : other.username.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.password = other.password == null ? null : other.password.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.sex = other.sex == null ? null : other.sex.copy();
        this.likesId = other.likesId == null ? null : other.likesId.copy();
        this.postsId = other.postsId == null ? null : other.postsId.copy();
        this.followersId = other.followersId == null ? null : other.followersId.copy();
        this.followingId = other.followingId == null ? null : other.followingId.copy();
    }

    @Override
    public InstagramUserCriteria copy() {
        return new InstagramUserCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUsername() {
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPassword() {
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public IntegerFilter getAge() {
        return age;
    }

    public void setAge(IntegerFilter age) {
        this.age = age;
    }

    public StringFilter getSex() {
        return sex;
    }

    public void setSex(StringFilter sex) {
        this.sex = sex;
    }

    public LongFilter getLikesId() {
        return likesId;
    }

    public void setLikesId(LongFilter likesId) {
        this.likesId = likesId;
    }

    public LongFilter getPostsId() {
        return postsId;
    }

    public void setPostsId(LongFilter postsId) {
        this.postsId = postsId;
    }

    public LongFilter getFollowersId() {
        return followersId;
    }

    public void setFollowersId(LongFilter followersId) {
        this.followersId = followersId;
    }

    public LongFilter getFollowingId() {
        return followingId;
    }

    public void setFollowingId(LongFilter followingId) {
        this.followingId = followingId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InstagramUserCriteria that = (InstagramUserCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(username, that.username) &&
            Objects.equals(email, that.email) &&
            Objects.equals(password, that.password) &&
            Objects.equals(age, that.age) &&
            Objects.equals(sex, that.sex) &&
            Objects.equals(likesId, that.likesId) &&
            Objects.equals(postsId, that.postsId) &&
            Objects.equals(followersId, that.followersId) &&
            Objects.equals(followingId, that.followingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        username,
        email,
        password,
        age,
        sex,
        likesId,
        postsId,
        followersId,
        followingId
        );
    }

    @Override
    public String toString() {
        return "InstagramUserCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (username != null ? "username=" + username + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (password != null ? "password=" + password + ", " : "") +
                (age != null ? "age=" + age + ", " : "") +
                (sex != null ? "sex=" + sex + ", " : "") +
                (likesId != null ? "likesId=" + likesId + ", " : "") +
                (postsId != null ? "postsId=" + postsId + ", " : "") +
                (followersId != null ? "followersId=" + followersId + ", " : "") +
                (followingId != null ? "followingId=" + followingId + ", " : "") +
            "}";
    }

}
