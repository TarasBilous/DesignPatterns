package com.bilous.instagram.service;

import com.bilous.instagram.domain.InstagramUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link InstagramUser}.
 */
public interface InstagramUserService {

    /**
     * Save a instagramUser.
     *
     * @param instagramUser the entity to save.
     * @return the persisted entity.
     */
    InstagramUser save(InstagramUser instagramUser);

    /**
     * Get all the instagramUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InstagramUser> findAll(Pageable pageable);

    /**
     * Get the "id" instagramUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InstagramUser> findOne(Long id);

    /**
     * Delete the "id" instagramUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
