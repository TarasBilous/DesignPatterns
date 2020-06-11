package com.bilous.instagram.web.rest;

import com.bilous.instagram.InstagramApp;
import com.bilous.instagram.domain.FollowerFollowing;
import com.bilous.instagram.domain.InstagramUser;
import com.bilous.instagram.repository.FollowerFollowingRepository;
import com.bilous.instagram.service.FollowerFollowingService;
import com.bilous.instagram.service.dto.FollowerFollowingCriteria;
import com.bilous.instagram.service.FollowerFollowingQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FollowerFollowingResource} REST controller.
 */
@SpringBootTest(classes = InstagramApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class FollowerFollowingResourceIT {

    private static final Boolean DEFAULT_CAN_FOLLOW = false;
    private static final Boolean UPDATED_CAN_FOLLOW = true;

    @Autowired
    private FollowerFollowingRepository followerFollowingRepository;

    @Autowired
    private FollowerFollowingService followerFollowingService;

    @Autowired
    private FollowerFollowingQueryService followerFollowingQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFollowerFollowingMockMvc;

    private FollowerFollowing followerFollowing;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FollowerFollowing createEntity(EntityManager em) {
        FollowerFollowing followerFollowing = new FollowerFollowing()
            .canFollow(DEFAULT_CAN_FOLLOW);
        return followerFollowing;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FollowerFollowing createUpdatedEntity(EntityManager em) {
        FollowerFollowing followerFollowing = new FollowerFollowing()
            .canFollow(UPDATED_CAN_FOLLOW);
        return followerFollowing;
    }

    @BeforeEach
    public void initTest() {
        followerFollowing = createEntity(em);
    }

    @Test
    @Transactional
    public void createFollowerFollowing() throws Exception {
        int databaseSizeBeforeCreate = followerFollowingRepository.findAll().size();

        // Create the FollowerFollowing
        restFollowerFollowingMockMvc.perform(post("/api/follower-followings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(followerFollowing)))
            .andExpect(status().isCreated());

        // Validate the FollowerFollowing in the database
        List<FollowerFollowing> followerFollowingList = followerFollowingRepository.findAll();
        assertThat(followerFollowingList).hasSize(databaseSizeBeforeCreate + 1);
        FollowerFollowing testFollowerFollowing = followerFollowingList.get(followerFollowingList.size() - 1);
        assertThat(testFollowerFollowing.isCanFollow()).isEqualTo(DEFAULT_CAN_FOLLOW);
    }

    @Test
    @Transactional
    public void createFollowerFollowingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = followerFollowingRepository.findAll().size();

        // Create the FollowerFollowing with an existing ID
        followerFollowing.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFollowerFollowingMockMvc.perform(post("/api/follower-followings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(followerFollowing)))
            .andExpect(status().isBadRequest());

        // Validate the FollowerFollowing in the database
        List<FollowerFollowing> followerFollowingList = followerFollowingRepository.findAll();
        assertThat(followerFollowingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFollowerFollowings() throws Exception {
        // Initialize the database
        followerFollowingRepository.saveAndFlush(followerFollowing);

        // Get all the followerFollowingList
        restFollowerFollowingMockMvc.perform(get("/api/follower-followings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(followerFollowing.getId().intValue())))
            .andExpect(jsonPath("$.[*].canFollow").value(hasItem(DEFAULT_CAN_FOLLOW.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getFollowerFollowing() throws Exception {
        // Initialize the database
        followerFollowingRepository.saveAndFlush(followerFollowing);

        // Get the followerFollowing
        restFollowerFollowingMockMvc.perform(get("/api/follower-followings/{id}", followerFollowing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(followerFollowing.getId().intValue()))
            .andExpect(jsonPath("$.canFollow").value(DEFAULT_CAN_FOLLOW.booleanValue()));
    }


    @Test
    @Transactional
    public void getFollowerFollowingsByIdFiltering() throws Exception {
        // Initialize the database
        followerFollowingRepository.saveAndFlush(followerFollowing);

        Long id = followerFollowing.getId();

        defaultFollowerFollowingShouldBeFound("id.equals=" + id);
        defaultFollowerFollowingShouldNotBeFound("id.notEquals=" + id);

        defaultFollowerFollowingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFollowerFollowingShouldNotBeFound("id.greaterThan=" + id);

        defaultFollowerFollowingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFollowerFollowingShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFollowerFollowingsByCanFollowIsEqualToSomething() throws Exception {
        // Initialize the database
        followerFollowingRepository.saveAndFlush(followerFollowing);

        // Get all the followerFollowingList where canFollow equals to DEFAULT_CAN_FOLLOW
        defaultFollowerFollowingShouldBeFound("canFollow.equals=" + DEFAULT_CAN_FOLLOW);

        // Get all the followerFollowingList where canFollow equals to UPDATED_CAN_FOLLOW
        defaultFollowerFollowingShouldNotBeFound("canFollow.equals=" + UPDATED_CAN_FOLLOW);
    }

    @Test
    @Transactional
    public void getAllFollowerFollowingsByCanFollowIsNotEqualToSomething() throws Exception {
        // Initialize the database
        followerFollowingRepository.saveAndFlush(followerFollowing);

        // Get all the followerFollowingList where canFollow not equals to DEFAULT_CAN_FOLLOW
        defaultFollowerFollowingShouldNotBeFound("canFollow.notEquals=" + DEFAULT_CAN_FOLLOW);

        // Get all the followerFollowingList where canFollow not equals to UPDATED_CAN_FOLLOW
        defaultFollowerFollowingShouldBeFound("canFollow.notEquals=" + UPDATED_CAN_FOLLOW);
    }

    @Test
    @Transactional
    public void getAllFollowerFollowingsByCanFollowIsInShouldWork() throws Exception {
        // Initialize the database
        followerFollowingRepository.saveAndFlush(followerFollowing);

        // Get all the followerFollowingList where canFollow in DEFAULT_CAN_FOLLOW or UPDATED_CAN_FOLLOW
        defaultFollowerFollowingShouldBeFound("canFollow.in=" + DEFAULT_CAN_FOLLOW + "," + UPDATED_CAN_FOLLOW);

        // Get all the followerFollowingList where canFollow equals to UPDATED_CAN_FOLLOW
        defaultFollowerFollowingShouldNotBeFound("canFollow.in=" + UPDATED_CAN_FOLLOW);
    }

    @Test
    @Transactional
    public void getAllFollowerFollowingsByCanFollowIsNullOrNotNull() throws Exception {
        // Initialize the database
        followerFollowingRepository.saveAndFlush(followerFollowing);

        // Get all the followerFollowingList where canFollow is not null
        defaultFollowerFollowingShouldBeFound("canFollow.specified=true");

        // Get all the followerFollowingList where canFollow is null
        defaultFollowerFollowingShouldNotBeFound("canFollow.specified=false");
    }

    @Test
    @Transactional
    public void getAllFollowerFollowingsByFollowingIsEqualToSomething() throws Exception {
        // Initialize the database
        followerFollowingRepository.saveAndFlush(followerFollowing);
        InstagramUser following = InstagramUserResourceIT.createEntity(em);
        em.persist(following);
        em.flush();
        followerFollowing.setFollowing(following);
        followerFollowingRepository.saveAndFlush(followerFollowing);
        Long followingId = following.getId();

        // Get all the followerFollowingList where following equals to followingId
        defaultFollowerFollowingShouldBeFound("followingId.equals=" + followingId);

        // Get all the followerFollowingList where following equals to followingId + 1
        defaultFollowerFollowingShouldNotBeFound("followingId.equals=" + (followingId + 1));
    }


    @Test
    @Transactional
    public void getAllFollowerFollowingsByFollowedByIsEqualToSomething() throws Exception {
        // Initialize the database
        followerFollowingRepository.saveAndFlush(followerFollowing);
        InstagramUser followedBy = InstagramUserResourceIT.createEntity(em);
        em.persist(followedBy);
        em.flush();
        followerFollowing.setFollowedBy(followedBy);
        followerFollowingRepository.saveAndFlush(followerFollowing);
        Long followedById = followedBy.getId();

        // Get all the followerFollowingList where followedBy equals to followedById
        defaultFollowerFollowingShouldBeFound("followedById.equals=" + followedById);

        // Get all the followerFollowingList where followedBy equals to followedById + 1
        defaultFollowerFollowingShouldNotBeFound("followedById.equals=" + (followedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFollowerFollowingShouldBeFound(String filter) throws Exception {
        restFollowerFollowingMockMvc.perform(get("/api/follower-followings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(followerFollowing.getId().intValue())))
            .andExpect(jsonPath("$.[*].canFollow").value(hasItem(DEFAULT_CAN_FOLLOW.booleanValue())));

        // Check, that the count call also returns 1
        restFollowerFollowingMockMvc.perform(get("/api/follower-followings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFollowerFollowingShouldNotBeFound(String filter) throws Exception {
        restFollowerFollowingMockMvc.perform(get("/api/follower-followings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFollowerFollowingMockMvc.perform(get("/api/follower-followings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFollowerFollowing() throws Exception {
        // Get the followerFollowing
        restFollowerFollowingMockMvc.perform(get("/api/follower-followings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFollowerFollowing() throws Exception {
        // Initialize the database
        followerFollowingService.save(followerFollowing);

        int databaseSizeBeforeUpdate = followerFollowingRepository.findAll().size();

        // Update the followerFollowing
        FollowerFollowing updatedFollowerFollowing = followerFollowingRepository.findById(followerFollowing.getId()).get();
        // Disconnect from session so that the updates on updatedFollowerFollowing are not directly saved in db
        em.detach(updatedFollowerFollowing);
        updatedFollowerFollowing
            .canFollow(UPDATED_CAN_FOLLOW);

        restFollowerFollowingMockMvc.perform(put("/api/follower-followings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFollowerFollowing)))
            .andExpect(status().isOk());

        // Validate the FollowerFollowing in the database
        List<FollowerFollowing> followerFollowingList = followerFollowingRepository.findAll();
        assertThat(followerFollowingList).hasSize(databaseSizeBeforeUpdate);
        FollowerFollowing testFollowerFollowing = followerFollowingList.get(followerFollowingList.size() - 1);
        assertThat(testFollowerFollowing.isCanFollow()).isEqualTo(UPDATED_CAN_FOLLOW);
    }

    @Test
    @Transactional
    public void updateNonExistingFollowerFollowing() throws Exception {
        int databaseSizeBeforeUpdate = followerFollowingRepository.findAll().size();

        // Create the FollowerFollowing

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFollowerFollowingMockMvc.perform(put("/api/follower-followings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(followerFollowing)))
            .andExpect(status().isBadRequest());

        // Validate the FollowerFollowing in the database
        List<FollowerFollowing> followerFollowingList = followerFollowingRepository.findAll();
        assertThat(followerFollowingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFollowerFollowing() throws Exception {
        // Initialize the database
        followerFollowingService.save(followerFollowing);

        int databaseSizeBeforeDelete = followerFollowingRepository.findAll().size();

        // Delete the followerFollowing
        restFollowerFollowingMockMvc.perform(delete("/api/follower-followings/{id}", followerFollowing.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FollowerFollowing> followerFollowingList = followerFollowingRepository.findAll();
        assertThat(followerFollowingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
