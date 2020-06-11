package com.bilous.instagram.service;

import com.bilous.instagram.domain.Like;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Like}.
 */
public interface LikeService {

    /**
     * Save a like.
     *
     * @param like the entity to save.
     * @return the persisted entity.
     */
    Like save(Like like);

    /**
     * Get all the likes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Like> findAll(Pageable pageable);

    /**
     * Get the "id" like.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Like> findOne(Long id);

    /**
     * Delete the "id" like.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
