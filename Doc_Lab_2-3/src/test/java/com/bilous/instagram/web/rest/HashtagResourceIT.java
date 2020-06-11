package com.bilous.instagram.web.rest;

import com.bilous.instagram.InstagramApp;
import com.bilous.instagram.domain.Hashtag;
import com.bilous.instagram.domain.Post;
import com.bilous.instagram.domain.Comment;
import com.bilous.instagram.repository.HashtagRepository;
import com.bilous.instagram.service.HashtagService;
import com.bilous.instagram.service.dto.HashtagCriteria;
import com.bilous.instagram.service.HashtagQueryService;

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
 * Integration tests for the {@link HashtagResource} REST controller.
 */
@SpringBootTest(classes = InstagramApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class HashtagResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private HashtagRepository hashtagRepository;

    @Autowired
    private HashtagService hashtagService;

    @Autowired
    private HashtagQueryService hashtagQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHashtagMockMvc;

    private Hashtag hashtag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hashtag createEntity(EntityManager em) {
        Hashtag hashtag = new Hashtag()
            .name(DEFAULT_NAME);
        return hashtag;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hashtag createUpdatedEntity(EntityManager em) {
        Hashtag hashtag = new Hashtag()
            .name(UPDATED_NAME);
        return hashtag;
    }

    @BeforeEach
    public void initTest() {
        hashtag = createEntity(em);
    }

    @Test
    @Transactional
    public void createHashtag() throws Exception {
        int databaseSizeBeforeCreate = hashtagRepository.findAll().size();

        // Create the Hashtag
        restHashtagMockMvc.perform(post("/api/hashtags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hashtag)))
            .andExpect(status().isCreated());

        // Validate the Hashtag in the database
        List<Hashtag> hashtagList = hashtagRepository.findAll();
        assertThat(hashtagList).hasSize(databaseSizeBeforeCreate + 1);
        Hashtag testHashtag = hashtagList.get(hashtagList.size() - 1);
        assertThat(testHashtag.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createHashtagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hashtagRepository.findAll().size();

        // Create the Hashtag with an existing ID
        hashtag.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHashtagMockMvc.perform(post("/api/hashtags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hashtag)))
            .andExpect(status().isBadRequest());

        // Validate the Hashtag in the database
        List<Hashtag> hashtagList = hashtagRepository.findAll();
        assertThat(hashtagList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHashtags() throws Exception {
        // Initialize the database
        hashtagRepository.saveAndFlush(hashtag);

        // Get all the hashtagList
        restHashtagMockMvc.perform(get("/api/hashtags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hashtag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getHashtag() throws Exception {
        // Initialize the database
        hashtagRepository.saveAndFlush(hashtag);

        // Get the hashtag
        restHashtagMockMvc.perform(get("/api/hashtags/{id}", hashtag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hashtag.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }


    @Test
    @Transactional
    public void getHashtagsByIdFiltering() throws Exception {
        // Initialize the database
        hashtagRepository.saveAndFlush(hashtag);

        Long id = hashtag.getId();

        defaultHashtagShouldBeFound("id.equals=" + id);
        defaultHashtagShouldNotBeFound("id.notEquals=" + id);

        defaultHashtagShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHashtagShouldNotBeFound("id.greaterThan=" + id);

        defaultHashtagShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHashtagShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllHashtagsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        hashtagRepository.saveAndFlush(hashtag);

        // Get all the hashtagList where name equals to DEFAULT_NAME
        defaultHashtagShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the hashtagList where name equals to UPDATED_NAME
        defaultHashtagShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllHashtagsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hashtagRepository.saveAndFlush(hashtag);

        // Get all the hashtagList where name not equals to DEFAULT_NAME
        defaultHashtagShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the hashtagList where name not equals to UPDATED_NAME
        defaultHashtagShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllHashtagsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        hashtagRepository.saveAndFlush(hashtag);

        // Get all the hashtagList where name in DEFAULT_NAME or UPDATED_NAME
        defaultHashtagShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the hashtagList where name equals to UPDATED_NAME
        defaultHashtagShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllHashtagsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        hashtagRepository.saveAndFlush(hashtag);

        // Get all the hashtagList where name is not null
        defaultHashtagShouldBeFound("name.specified=true");

        // Get all the hashtagList where name is null
        defaultHashtagShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllHashtagsByNameContainsSomething() throws Exception {
        // Initialize the database
        hashtagRepository.saveAndFlush(hashtag);

        // Get all the hashtagList where name contains DEFAULT_NAME
        defaultHashtagShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the hashtagList where name contains UPDATED_NAME
        defaultHashtagShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllHashtagsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        hashtagRepository.saveAndFlush(hashtag);

        // Get all the hashtagList where name does not contain DEFAULT_NAME
        defaultHashtagShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the hashtagList where name does not contain UPDATED_NAME
        defaultHashtagShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllHashtagsByPostIsEqualToSomething() throws Exception {
        // Initialize the database
        hashtagRepository.saveAndFlush(hashtag);
        Post post = PostResourceIT.createEntity(em);
        em.persist(post);
        em.flush();
        hashtag.setPost(post);
        hashtagRepository.saveAndFlush(hashtag);
        Long postId = post.getId();

        // Get all the hashtagList where post equals to postId
        defaultHashtagShouldBeFound("postId.equals=" + postId);

        // Get all the hashtagList where post equals to postId + 1
        defaultHashtagShouldNotBeFound("postId.equals=" + (postId + 1));
    }


    @Test
    @Transactional
    public void getAllHashtagsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        hashtagRepository.saveAndFlush(hashtag);
        Comment comment = CommentResourceIT.createEntity(em);
        em.persist(comment);
        em.flush();
        hashtag.setComment(comment);
        hashtagRepository.saveAndFlush(hashtag);
        Long commentId = comment.getId();

        // Get all the hashtagList where comment equals to commentId
        defaultHashtagShouldBeFound("commentId.equals=" + commentId);

        // Get all the hashtagList where comment equals to commentId + 1
        defaultHashtagShouldNotBeFound("commentId.equals=" + (commentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHashtagShouldBeFound(String filter) throws Exception {
        restHashtagMockMvc.perform(get("/api/hashtags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hashtag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restHashtagMockMvc.perform(get("/api/hashtags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHashtagShouldNotBeFound(String filter) throws Exception {
        restHashtagMockMvc.perform(get("/api/hashtags?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHashtagMockMvc.perform(get("/api/hashtags/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingHashtag() throws Exception {
        // Get the hashtag
        restHashtagMockMvc.perform(get("/api/hashtags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHashtag() throws Exception {
        // Initialize the database
        hashtagService.save(hashtag);

        int databaseSizeBeforeUpdate = hashtagRepository.findAll().size();

        // Update the hashtag
        Hashtag updatedHashtag = hashtagRepository.findById(hashtag.getId()).get();
        // Disconnect from session so that the updates on updatedHashtag are not directly saved in db
        em.detach(updatedHashtag);
        updatedHashtag
            .name(UPDATED_NAME);

        restHashtagMockMvc.perform(put("/api/hashtags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedHashtag)))
            .andExpect(status().isOk());

        // Validate the Hashtag in the database
        List<Hashtag> hashtagList = hashtagRepository.findAll();
        assertThat(hashtagList).hasSize(databaseSizeBeforeUpdate);
        Hashtag testHashtag = hashtagList.get(hashtagList.size() - 1);
        assertThat(testHashtag.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingHashtag() throws Exception {
        int databaseSizeBeforeUpdate = hashtagRepository.findAll().size();

        // Create the Hashtag

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHashtagMockMvc.perform(put("/api/hashtags")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(hashtag)))
            .andExpect(status().isBadRequest());

        // Validate the Hashtag in the database
        List<Hashtag> hashtagList = hashtagRepository.findAll();
        assertThat(hashtagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHashtag() throws Exception {
        // Initialize the database
        hashtagService.save(hashtag);

        int databaseSizeBeforeDelete = hashtagRepository.findAll().size();

        // Delete the hashtag
        restHashtagMockMvc.perform(delete("/api/hashtags/{id}", hashtag.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hashtag> hashtagList = hashtagRepository.findAll();
        assertThat(hashtagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
