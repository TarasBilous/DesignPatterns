package com.bilous.instagram.service;

import com.bilous.instagram.domain.Hashtag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Hashtag}.
 */
public interface HashtagService {

    /**
     * Save a hashtag.
     *
     * @param hashtag the entity to save.
     * @return the persisted entity.
     */
    Hashtag save(Hashtag hashtag);

    /**
     * Get all the hashtags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Hashtag> findAll(Pageable pageable);

    /**
     * Get the "id" hashtag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Hashtag> findOne(Long id);

    /**
     * Delete the "id" hashtag.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
