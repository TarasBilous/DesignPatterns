package com.bilous.instagram.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.bilous.instagram.domain.FollowerFollowing;
import com.bilous.instagram.domain.*; // for static metamodels
import com.bilous.instagram.repository.FollowerFollowingRepository;
import com.bilous.instagram.service.dto.FollowerFollowingCriteria;

/**
 * Service for executing complex queries for {@link FollowerFollowing} entities in the database.
 * The main input is a {@link FollowerFollowingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FollowerFollowing} or a {@link Page} of {@link FollowerFollowing} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FollowerFollowingQueryService extends QueryService<FollowerFollowing> {

    private final Logger log = LoggerFactory.getLogger(FollowerFollowingQueryService.class);

    private final FollowerFollowingRepository followerFollowingRepository;

    public FollowerFollowingQueryService(FollowerFollowingRepository followerFollowingRepository) {
        this.followerFollowingRepository = followerFollowingRepository;
    }

    /**
     * Return a {@link List} of {@link FollowerFollowing} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FollowerFollowing> findByCriteria(FollowerFollowingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FollowerFollowing> specification = createSpecification(criteria);
        return followerFollowingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FollowerFollowing} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FollowerFollowing> findByCriteria(FollowerFollowingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FollowerFollowing> specification = createSpecification(criteria);
        return followerFollowingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FollowerFollowingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FollowerFollowing> specification = createSpecification(criteria);
        return followerFollowingRepository.count(specification);
    }

    /**
     * Function to convert {@link FollowerFollowingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FollowerFollowing> createSpecification(FollowerFollowingCriteria criteria) {
        Specification<FollowerFollowing> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FollowerFollowing_.id));
            }
            if (criteria.getCanFollow() != null) {
                specification = specification.and(buildSpecification(criteria.getCanFollow(), FollowerFollowing_.canFollow));
            }
            if (criteria.getFollowingId() != null) {
                specification = specification.and(buildSpecification(criteria.getFollowingId(),
                    root -> root.join(FollowerFollowing_.following, JoinType.LEFT).get(InstagramUser_.id)));
            }
            if (criteria.getFollowedById() != null) {
                specification = specification.and(buildSpecification(criteria.getFollowedById(),
                    root -> root.join(FollowerFollowing_.followedBy, JoinType.LEFT).get(InstagramUser_.id)));
            }
        }
        return specification;
    }
}
