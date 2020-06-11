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
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.bilous.instagram.domain.Post} entity. This class is used
 * in {@link com.bilous.instagram.web.rest.PostResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /posts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PostCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter photoUrl;

    private ZonedDateTimeFilter date;

    private StringFilter location;

    private LongFilter commentsId;

    private LongFilter likedById;

    private LongFilter hashtagsId;

    private LongFilter usersId;

    public PostCriteria() {
    }

    public PostCriteria(PostCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.photoUrl = other.photoUrl == null ? null : other.photoUrl.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.location = other.location == null ? null : other.location.copy();
        this.commentsId = other.commentsId == null ? null : other.commentsId.copy();
        this.likedById = other.likedById == null ? null : other.likedById.copy();
        this.hashtagsId = other.hashtagsId == null ? null : other.hashtagsId.copy();
        this.usersId = other.usersId == null ? null : other.usersId.copy();
    }

    @Override
    public PostCriteria copy() {
        return new PostCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(StringFilter photoUrl) {
        this.photoUrl = photoUrl;
    }

    public ZonedDateTimeFilter getDate() {
        return date;
    }

    public void setDate(ZonedDateTimeFilter date) {
        this.date = date;
    }

    public StringFilter getLocation() {
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
    }

    public LongFilter getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(LongFilter commentsId) {
        this.commentsId = commentsId;
    }

    public LongFilter getLikedById() {
        return likedById;
    }

    public void setLikedById(LongFilter likedById) {
        this.likedById = likedById;
    }

    public LongFilter getHashtagsId() {
        return hashtagsId;
    }

    public void setHashtagsId(LongFilter hashtagsId) {
        this.hashtagsId = hashtagsId;
    }

    public LongFilter getUsersId() {
        return usersId;
    }

    public void setUsersId(LongFilter usersId) {
        this.usersId = usersId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PostCriteria that = (PostCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(photoUrl, that.photoUrl) &&
            Objects.equals(date, that.date) &&
            Objects.equals(location, that.location) &&
            Objects.equals(commentsId, that.commentsId) &&
            Objects.equals(likedById, that.likedById) &&
            Objects.equals(hashtagsId, that.hashtagsId) &&
            Objects.equals(usersId, that.usersId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        photoUrl,
        date,
        location,
        commentsId,
        likedById,
        hashtagsId,
        usersId
        );
    }

    @Override
    public String toString() {
        return "PostCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (photoUrl != null ? "photoUrl=" + photoUrl + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (location != null ? "location=" + location + ", " : "") +
                (commentsId != null ? "commentsId=" + commentsId + ", " : "") +
                (likedById != null ? "likedById=" + likedById + ", " : "") +
                (hashtagsId != null ? "hashtagsId=" + hashtagsId + ", " : "") +
                (usersId != null ? "usersId=" + usersId + ", " : "") +
            "}";
    }

}
