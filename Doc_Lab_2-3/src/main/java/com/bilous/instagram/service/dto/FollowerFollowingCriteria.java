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
 * Criteria class for the {@link com.bilous.instagram.domain.FollowerFollowing} entity. This class is used
 * in {@link com.bilous.instagram.web.rest.FollowerFollowingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /follower-followings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FollowerFollowingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter canFollow;

    private LongFilter followingId;

    private LongFilter followedById;

    public FollowerFollowingCriteria() {
    }

    public FollowerFollowingCriteria(FollowerFollowingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.canFollow = other.canFollow == null ? null : other.canFollow.copy();
        this.followingId = other.followingId == null ? null : other.followingId.copy();
        this.followedById = other.followedById == null ? null : other.followedById.copy();
    }

    @Override
    public FollowerFollowingCriteria copy() {
        return new FollowerFollowingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getCanFollow() {
        return canFollow;
    }

    public void setCanFollow(BooleanFilter canFollow) {
        this.canFollow = canFollow;
    }

    public LongFilter getFollowingId() {
        return followingId;
    }

    public void setFollowingId(LongFilter followingId) {
        this.followingId = followingId;
    }

    public LongFilter getFollowedById() {
        return followedById;
    }

    public void setFollowedById(LongFilter followedById) {
        this.followedById = followedById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FollowerFollowingCriteria that = (FollowerFollowingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(canFollow, that.canFollow) &&
            Objects.equals(followingId, that.followingId) &&
            Objects.equals(followedById, that.followedById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        canFollow,
        followingId,
        followedById
        );
    }

    @Override
    public String toString() {
        return "FollowerFollowingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (canFollow != null ? "canFollow=" + canFollow + ", " : "") +
                (followingId != null ? "followingId=" + followingId + ", " : "") +
                (followedById != null ? "followedById=" + followedById + ", " : "") +
            "}";
    }

}
