package com.bilous.instagram.service.impl;

import com.bilous.instagram.service.FollowerFollowingService;
import com.bilous.instagram.domain.FollowerFollowing;
import com.bilous.instagram.repository.FollowerFollowingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FollowerFollowing}.
 */
@Service
@Transactional
public class FollowerFollowingServiceImpl implements FollowerFollowingService {

    private final Logger log = LoggerFactory.getLogger(FollowerFollowingServiceImpl.class);

    private final FollowerFollowingRepository followerFollowingRepository;

    public FollowerFollowingServiceImpl(FollowerFollowingRepository followerFollowingRepository) {
        this.followerFollowingRepository = followerFollowingRepository;
    }

    /**
     * Save a followerFollowing.
     *
     * @param followerFollowing the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FollowerFollowing save(FollowerFollowing followerFollowing) {
        log.debug("Request to save FollowerFollowing : {}", followerFollowing);
        return followerFollowingRepository.save(followerFollowing);
    }

    /**
     * Get all the followerFollowings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FollowerFollowing> findAll(Pageable pageable) {
        log.debug("Request to get all FollowerFollowings");
        return followerFollowingRepository.findAll(pageable);
    }

    /**
     * Get one followerFollowing by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FollowerFollowing> findOne(Long id) {
        log.debug("Request to get FollowerFollowing : {}", id);
        return followerFollowingRepository.findById(id);
    }

    /**
     * Delete the followerFollowing by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FollowerFollowing : {}", id);
        followerFollowingRepository.deleteById(id);
    }
}
