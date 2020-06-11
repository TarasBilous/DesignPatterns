package com.bilous.instagram.service;

import com.bilous.instagram.domain.FollowerFollowing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link FollowerFollowing}.
 */
public interface FollowerFollowingService {

    /**
     * Save a followerFollowing.
     *
     * @param followerFollowing the entity to save.
     * @return the persisted entity.
     */
    FollowerFollowing save(FollowerFollowing followerFollowing);

    /**
     * Get all the followerFollowings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FollowerFollowing> findAll(Pageable pageable);

    /**
     * Get the "id" followerFollowing.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FollowerFollowing> findOne(Long id);

    /**
     * Delete the "id" followerFollowing.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
