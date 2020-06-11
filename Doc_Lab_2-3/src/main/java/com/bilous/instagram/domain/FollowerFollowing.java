package com.bilous.instagram.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A FollowerFollowing.
 */
@Entity
@Table(name = "follower_following")
public class FollowerFollowing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "can_follow")
    private Boolean canFollow;

    @ManyToOne
    @JsonIgnoreProperties("followers")
    private InstagramUser following;

    @ManyToOne
    @JsonIgnoreProperties("followings")
    private InstagramUser followedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isCanFollow() {
        return canFollow;
    }

    public FollowerFollowing canFollow(Boolean canFollow) {
        this.canFollow = canFollow;
        return this;
    }

    public void setCanFollow(Boolean canFollow) {
        this.canFollow = canFollow;
    }

    public InstagramUser getFollowing() {
        return following;
    }

    public FollowerFollowing following(InstagramUser instagramUser) {
        this.following = instagramUser;
        return this;
    }

    public void setFollowing(InstagramUser instagramUser) {
        this.following = instagramUser;
    }

    public InstagramUser getFollowedBy() {
        return followedBy;
    }

    public FollowerFollowing followedBy(InstagramUser instagramUser) {
        this.followedBy = instagramUser;
        return this;
    }

    public void setFollowedBy(InstagramUser instagramUser) {
        this.followedBy = instagramUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FollowerFollowing)) {
            return false;
        }
        return id != null && id.equals(((FollowerFollowing) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FollowerFollowing{" +
            "id=" + getId() +
            ", canFollow='" + isCanFollow() + "'" +
            "}";
    }
}
