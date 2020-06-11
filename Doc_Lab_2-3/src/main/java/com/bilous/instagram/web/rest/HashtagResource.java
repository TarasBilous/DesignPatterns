package com.bilous.instagram.web.rest;

import com.bilous.instagram.domain.Hashtag;
import com.bilous.instagram.service.HashtagService;
import com.bilous.instagram.web.rest.errors.BadRequestAlertException;
import com.bilous.instagram.service.dto.HashtagCriteria;
import com.bilous.instagram.service.HashtagQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.bilous.instagram.domain.Hashtag}.
 */
@RestController
@RequestMapping("/api")
public class HashtagResource {

    private final Logger log = LoggerFactory.getLogger(HashtagResource.class);

    private static final String ENTITY_NAME = "hashtag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HashtagService hashtagService;

    private final HashtagQueryService hashtagQueryService;

    public HashtagResource(HashtagService hashtagService, HashtagQueryService hashtagQueryService) {
        this.hashtagService = hashtagService;
        this.hashtagQueryService = hashtagQueryService;
    }

    /**
     * {@code POST  /hashtags} : Create a new hashtag.
     *
     * @param hashtag the hashtag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hashtag, or with status {@code 400 (Bad Request)} if the hashtag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hashtags")
    public ResponseEntity<Hashtag> createHashtag(@RequestBody Hashtag hashtag) throws URISyntaxException {
        log.debug("REST request to save Hashtag : {}", hashtag);
        if (hashtag.getId() != null) {
            throw new BadRequestAlertException("A new hashtag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Hashtag result = hashtagService.save(hashtag);
        return ResponseEntity.created(new URI("/api/hashtags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hashtags} : Updates an existing hashtag.
     *
     * @param hashtag the hashtag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hashtag,
     * or with status {@code 400 (Bad Request)} if the hashtag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hashtag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hashtags")
    public ResponseEntity<Hashtag> updateHashtag(@RequestBody Hashtag hashtag) throws URISyntaxException {
        log.debug("REST request to update Hashtag : {}", hashtag);
        if (hashtag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Hashtag result = hashtagService.save(hashtag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, hashtag.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /hashtags} : get all the hashtags.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hashtags in body.
     */
    @GetMapping("/hashtags")
    public ResponseEntity<List<Hashtag>> getAllHashtags(HashtagCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Hashtags by criteria: {}", criteria);
        Page<Hashtag> page = hashtagQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hashtags/count} : count all the hashtags.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/hashtags/count")
    public ResponseEntity<Long> countHashtags(HashtagCriteria criteria) {
        log.debug("REST request to count Hashtags by criteria: {}", criteria);
        return ResponseEntity.ok().body(hashtagQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /hashtags/:id} : get the "id" hashtag.
     *
     * @param id the id of the hashtag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hashtag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hashtags/{id}")
    public ResponseEntity<Hashtag> getHashtag(@PathVariable Long id) {
        log.debug("REST request to get Hashtag : {}", id);
        Optional<Hashtag> hashtag = hashtagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hashtag);
    }

    /**
     * {@code DELETE  /hashtags/:id} : delete the "id" hashtag.
     *
     * @param id the id of the hashtag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hashtags/{id}")
    public ResponseEntity<Void> deleteHashtag(@PathVariable Long id) {
        log.debug("REST request to delete Hashtag : {}", id);
        hashtagService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
