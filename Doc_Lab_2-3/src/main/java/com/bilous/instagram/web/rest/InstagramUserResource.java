package com.bilous.instagram.web.rest;

import com.bilous.instagram.domain.InstagramUser;
import com.bilous.instagram.service.InstagramUserService;
import com.bilous.instagram.web.rest.errors.BadRequestAlertException;
import com.bilous.instagram.service.dto.InstagramUserCriteria;
import com.bilous.instagram.service.InstagramUserQueryService;

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
 * REST controller for managing {@link com.bilous.instagram.domain.InstagramUser}.
 */
@RestController
@RequestMapping("/api")
public class InstagramUserResource {

    private final Logger log = LoggerFactory.getLogger(InstagramUserResource.class);

    private static final String ENTITY_NAME = "instagramUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstagramUserService instagramUserService;

    private final InstagramUserQueryService instagramUserQueryService;

    public InstagramUserResource(InstagramUserService instagramUserService, InstagramUserQueryService instagramUserQueryService) {
        this.instagramUserService = instagramUserService;
        this.instagramUserQueryService = instagramUserQueryService;
    }

    /**
     * {@code POST  /instagram-users} : Create a new instagramUser.
     *
     * @param instagramUser the instagramUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new instagramUser, or with status {@code 400 (Bad Request)} if the instagramUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/instagram-users")
    public ResponseEntity<InstagramUser> createInstagramUser(@RequestBody InstagramUser instagramUser) throws URISyntaxException {
        log.debug("REST request to save InstagramUser : {}", instagramUser);
        if (instagramUser.getId() != null) {
            throw new BadRequestAlertException("A new instagramUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstagramUser result = instagramUserService.save(instagramUser);
        return ResponseEntity.created(new URI("/api/instagram-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /instagram-users} : Updates an existing instagramUser.
     *
     * @param instagramUser the instagramUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated instagramUser,
     * or with status {@code 400 (Bad Request)} if the instagramUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the instagramUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/instagram-users")
    public ResponseEntity<InstagramUser> updateInstagramUser(@RequestBody InstagramUser instagramUser) throws URISyntaxException {
        log.debug("REST request to update InstagramUser : {}", instagramUser);
        if (instagramUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InstagramUser result = instagramUserService.save(instagramUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, instagramUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /instagram-users} : get all the instagramUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of instagramUsers in body.
     */
    @GetMapping("/instagram-users")
    public ResponseEntity<List<InstagramUser>> getAllInstagramUsers(InstagramUserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get InstagramUsers by criteria: {}", criteria);
        Page<InstagramUser> page = instagramUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /instagram-users/count} : count all the instagramUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/instagram-users/count")
    public ResponseEntity<Long> countInstagramUsers(InstagramUserCriteria criteria) {
        log.debug("REST request to count InstagramUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(instagramUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /instagram-users/:id} : get the "id" instagramUser.
     *
     * @param id the id of the instagramUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the instagramUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/instagram-users/{id}")
    public ResponseEntity<InstagramUser> getInstagramUser(@PathVariable Long id) {
        log.debug("REST request to get InstagramUser : {}", id);
        Optional<InstagramUser> instagramUser = instagramUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(instagramUser);
    }

    /**
     * {@code DELETE  /instagram-users/:id} : delete the "id" instagramUser.
     *
     * @param id the id of the instagramUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/instagram-users/{id}")
    public ResponseEntity<Void> deleteInstagramUser(@PathVariable Long id) {
        log.debug("REST request to delete InstagramUser : {}", id);
        instagramUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
