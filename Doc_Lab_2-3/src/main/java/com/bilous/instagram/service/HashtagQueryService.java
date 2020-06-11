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

import com.bilous.instagram.domain.Hashtag;
import com.bilous.instagram.domain.*; // for static metamodels
import com.bilous.instagram.repository.HashtagRepository;
import com.bilous.instagram.service.dto.HashtagCriteria;

/**
 * Service for executing complex queries for {@link Hashtag} entities in the database.
 * The main input is a {@link HashtagCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Hashtag} or a {@link Page} of {@link Hashtag} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HashtagQueryService extends QueryService<Hashtag> {

    private final Logger log = LoggerFactory.getLogger(HashtagQueryService.class);

    private final HashtagRepository hashtagRepository;

    public HashtagQueryService(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    /**
     * Return a {@link List} of {@link Hashtag} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Hashtag> findByCriteria(HashtagCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Hashtag> specification = createSpecification(criteria);
        return hashtagRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Hashtag} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Hashtag> findByCriteria(HashtagCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Hashtag> specification = createSpecification(criteria);
        return hashtagRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HashtagCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Hashtag> specification = createSpecification(criteria);
        return hashtagRepository.count(specification);
    }

    /**
     * Function to convert {@link HashtagCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Hashtag> createSpecification(HashtagCriteria criteria) {
        Specification<Hashtag> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Hashtag_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Hashtag_.name));
            }
            if (criteria.getPostId() != null) {
                specification = specification.and(buildSpecification(criteria.getPostId(),
                    root -> root.join(Hashtag_.post, JoinType.LEFT).get(Post_.id)));
            }
            if (criteria.getCommentId() != null) {
                specification = specification.and(buildSpecification(criteria.getCommentId(),
                    root -> root.join(Hashtag_.comment, JoinType.LEFT).get(Comment_.id)));
            }
        }
        return specification;
    }
}
