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

import com.bilous.instagram.domain.InstagramUser;
import com.bilous.instagram.domain.*; // for static metamodels
import com.bilous.instagram.repository.InstagramUserRepository;
import com.bilous.instagram.service.dto.InstagramUserCriteria;

/**
 * Service for executing complex queries for {@link InstagramUser} entities in the database.
 * The main input is a {@link InstagramUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InstagramUser} or a {@link Page} of {@link InstagramUser} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InstagramUserQueryService extends QueryService<InstagramUser> {

    private final Logger log = LoggerFactory.getLogger(InstagramUserQueryService.class);

    private final InstagramUserRepository instagramUserRepository;

    public InstagramUserQueryService(InstagramUserRepository instagramUserRepository) {
        this.instagramUserRepository = instagramUserRepository;
    }

    /**
     * Return a {@link List} of {@link InstagramUser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InstagramUser> findByCriteria(InstagramUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InstagramUser> specification = createSpecification(criteria);
        return instagramUserRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link InstagramUser} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InstagramUser> findByCriteria(InstagramUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InstagramUser> specification = createSpecification(criteria);
        return instagramUserRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InstagramUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InstagramUser> specification = createSpecification(criteria);
        return instagramUserRepository.count(specification);
    }

    /**
     * Function to convert {@link InstagramUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InstagramUser> createSpecification(InstagramUserCriteria criteria) {
        Specification<InstagramUser> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InstagramUser_.id));
            }
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), InstagramUser_.username));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), InstagramUser_.email));
            }
            if (criteria.getPassword() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassword(), InstagramUser_.password));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAge(), InstagramUser_.age));
            }
            if (criteria.getSex() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSex(), InstagramUser_.sex));
            }
            if (criteria.getLikesId() != null) {
                specification = specification.and(buildSpecification(criteria.getLikesId(),
                    root -> root.join(InstagramUser_.likes, JoinType.LEFT).get(Like_.id)));
            }
            if (criteria.getPostsId() != null) {
                specification = specification.and(buildSpecification(criteria.getPostsId(),
                    root -> root.join(InstagramUser_.posts, JoinType.LEFT).get(Post_.id)));
            }
            if (criteria.getFollowersId() != null) {
                specification = specification.and(buildSpecification(criteria.getFollowersId(),
                    root -> root.join(InstagramUser_.followers, JoinType.LEFT).get(FollowerFollowing_.id)));
            }
            if (criteria.getFollowingId() != null) {
                specification = specification.and(buildSpecification(criteria.getFollowingId(),
                    root -> root.join(InstagramUser_.followings, JoinType.LEFT).get(FollowerFollowing_.id)));
            }
        }
        return specification;
    }
}
