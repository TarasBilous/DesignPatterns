package com.bilous.instagram.web.rest;

import com.bilous.instagram.domain.FollowerFollowing;
import com.bilous.instagram.service.FollowerFollowingService;
import com.bilous.instagram.web.rest.errors.BadRequestAlertException;
import com.bilous.instagram.service.dto.FollowerFollowingCriteria;
import com.bilous.instagram.service.FollowerFollowingQueryService;

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
 * REST controller for managing {@link com.bilous.instagram.domain.FollowerFollowing}.
 */
@RestController
@RequestMapping("/api")
public class FollowerFollowingResource {

    private final Logger log = LoggerFactory.getLogger(FollowerFollowingResource.class);

    private static final String ENTITY_NAME = "followerFollowing";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FollowerFollowingService followerFollowingService;

    private final FollowerFollowingQueryService followerFollowingQueryService;

    public FollowerFollowingResource(FollowerFollowingService followerFollowingService, FollowerFollowingQueryService followerFollowingQueryService) {
        this.followerFollowingService = followerFollowingService;
        this.followerFollowingQueryService = followerFollowingQueryService;
    }

    /**
     * {@code POST  /follower-followings} : Create a new followerFollowing.
     *
     * @param followerFollowing the followerFollowing to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new followerFollowing, or with status {@code 400 (Bad Request)} if the followerFollowing has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/follower-followings")
    public ResponseEntity<FollowerFollowing> createFollowerFollowing(@RequestBody FollowerFollowing followerFollowing) throws URISyntaxException {
        log.debug("REST request to save FollowerFollowing : {}", followerFollowing);
        if (followerFollowing.getId() != null) {
            throw new BadRequestAlertException("A new followerFollowing cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FollowerFollowing result = followerFollowingService.save(followerFollowing);
        return ResponseEntity.created(new URI("/api/follower-followings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /follower-followings} : Updates an existing followerFollowing.
     *
     * @param followerFollowing the followerFollowing to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated followerFollowing,
     * or with status {@code 400 (Bad Request)} if the followerFollowing is not valid,
     * or with status {@code 500 (Internal Server Error)} if the followerFollowing couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/follower-followings")
    public ResponseEntity<FollowerFollowing> updateFollowerFollowing(@RequestBody FollowerFollowing followerFollowing) throws URISyntaxException {
        log.debug("REST request to update FollowerFollowing : {}", followerFollowing);
        if (followerFollowing.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FollowerFollowing result = followerFollowingService.save(followerFollowing);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, followerFollowing.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /follower-followings} : get all the followerFollowings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of followerFollowings in body.
     */
    @GetMapping("/follower-followings")
    public ResponseEntity<List<FollowerFollowing>> getAllFollowerFollowings(FollowerFollowingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get FollowerFollowings by criteria: {}", criteria);
        Page<FollowerFollowing> page = followerFollowingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /follower-followings/count} : count all the followerFollowings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/follower-followings/count")
    public ResponseEntity<Long> countFollowerFollowings(FollowerFollowingCriteria criteria) {
        log.debug("REST request to count FollowerFollowings by criteria: {}", criteria);
        return ResponseEntity.ok().body(followerFollowingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /follower-followings/:id} : get the "id" followerFollowing.
     *
     * @param id the id of the followerFollowing to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the followerFollowing, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/follower-followings/{id}")
    public ResponseEntity<FollowerFollowing> getFollowerFollowing(@PathVariable Long id) {
        log.debug("REST request to get FollowerFollowing : {}", id);
        Optional<FollowerFollowing> followerFollowing = followerFollowingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(followerFollowing);
    }

    /**
     * {@code DELETE  /follower-followings/:id} : delete the "id" followerFollowing.
     *
     * @param id the id of the followerFollowing to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/follower-followings/{id}")
    public ResponseEntity<Void> deleteFollowerFollowing(@PathVariable Long id) {
        log.debug("REST request to delete FollowerFollowing : {}", id);
        followerFollowingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
