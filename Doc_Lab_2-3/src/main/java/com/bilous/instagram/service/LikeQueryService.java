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

import com.bilous.instagram.domain.Like;
import com.bilous.instagram.domain.*; // for static metamodels
import com.bilous.instagram.repository.LikeRepository;
import com.bilous.instagram.service.dto.LikeCriteria;

/**
 * Service for executing complex queries for {@link Like} entities in the database.
 * The main input is a {@link LikeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Like} or a {@link Page} of {@link Like} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LikeQueryService extends QueryService<Like> {

    private final Logger log = LoggerFactory.getLogger(LikeQueryService.class);

    private final LikeRepository likeRepository;

    public LikeQueryService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    /**
     * Return a {@link List} of {@link Like} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Like> findByCriteria(LikeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Like> specification = createSpecification(criteria);
        return likeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Like} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Like> findByCriteria(LikeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Like> specification = createSpecification(criteria);
        return likeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LikeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Like> specification = createSpecification(criteria);
        return likeRepository.count(specification);
    }

    /**
     * Function to convert {@link LikeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Like> createSpecification(LikeCriteria criteria) {
        Specification<Like> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Like_.id));
            }
            if (criteria.getPostId() != null) {
                specification = specification.and(buildSpecification(criteria.getPostId(),
                    root -> root.join(Like_.post, JoinType.LEFT).get(Post_.id)));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Like_.user, JoinType.LEFT).get(InstagramUser_.id)));
            }
        }
        return specification;
    }
}
