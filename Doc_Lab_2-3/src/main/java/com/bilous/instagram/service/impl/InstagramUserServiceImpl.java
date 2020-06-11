package com.bilous.instagram.service.impl;

import com.bilous.instagram.service.InstagramUserService;
import com.bilous.instagram.domain.InstagramUser;
import com.bilous.instagram.repository.InstagramUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link InstagramUser}.
 */
@Service
@Transactional
public class InstagramUserServiceImpl implements InstagramUserService {

    private final Logger log = LoggerFactory.getLogger(InstagramUserServiceImpl.class);

    private final InstagramUserRepository instagramUserRepository;

    public InstagramUserServiceImpl(InstagramUserRepository instagramUserRepository) {
        this.instagramUserRepository = instagramUserRepository;
    }

    /**
     * Save a instagramUser.
     *
     * @param instagramUser the entity to save.
     * @return the persisted entity.
     */
    @Override
    public InstagramUser save(InstagramUser instagramUser) {
        log.debug("Request to save InstagramUser : {}", instagramUser);
        return instagramUserRepository.save(instagramUser);
    }

    /**
     * Get all the instagramUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InstagramUser> findAll(Pageable pageable) {
        log.debug("Request to get all InstagramUsers");
        return instagramUserRepository.findAll(pageable);
    }

    /**
     * Get one instagramUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InstagramUser> findOne(Long id) {
        log.debug("Request to get InstagramUser : {}", id);
        return instagramUserRepository.findById(id);
    }

    /**
     * Delete the instagramUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InstagramUser : {}", id);
        instagramUserRepository.deleteById(id);
    }
}
