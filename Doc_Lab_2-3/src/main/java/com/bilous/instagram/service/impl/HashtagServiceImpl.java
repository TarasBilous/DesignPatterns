package com.bilous.instagram.service.impl;

import com.bilous.instagram.service.HashtagService;
import com.bilous.instagram.domain.Hashtag;
import com.bilous.instagram.repository.HashtagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Hashtag}.
 */
@Service
@Transactional
public class HashtagServiceImpl implements HashtagService {

    private final Logger log = LoggerFactory.getLogger(HashtagServiceImpl.class);

    private final HashtagRepository hashtagRepository;

    public HashtagServiceImpl(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    /**
     * Save a hashtag.
     *
     * @param hashtag the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Hashtag save(Hashtag hashtag) {
        log.debug("Request to save Hashtag : {}", hashtag);
        return hashtagRepository.save(hashtag);
    }

    /**
     * Get all the hashtags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Hashtag> findAll(Pageable pageable) {
        log.debug("Request to get all Hashtags");
        return hashtagRepository.findAll(pageable);
    }

    /**
     * Get one hashtag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Hashtag> findOne(Long id) {
        log.debug("Request to get Hashtag : {}", id);
        return hashtagRepository.findById(id);
    }

    /**
     * Delete the hashtag by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hashtag : {}", id);
        hashtagRepository.deleteById(id);
    }
}
