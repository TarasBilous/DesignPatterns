package com.bilous.instagram.web.rest;

import com.bilous.instagram.InstagramApp;
import com.bilous.instagram.domain.InstagramUser;
import com.bilous.instagram.domain.Like;
import com.bilous.instagram.domain.Post;
import com.bilous.instagram.domain.FollowerFollowing;
import com.bilous.instagram.repository.InstagramUserRepository;
import com.bilous.instagram.service.InstagramUserService;
import com.bilous.instagram.service.dto.InstagramUserCriteria;
import com.bilous.instagram.service.InstagramUserQueryService;

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
 * Integration tests for the {@link InstagramUserResource} REST controller.
 */
@SpringBootTest(classes = InstagramApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class InstagramUserResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;
    private static final Integer SMALLER_AGE = 1 - 1;

    private static final String DEFAULT_SEX = "AAAAAAAAAA";
    private static final String UPDATED_SEX = "BBBBBBBBBB";

    @Autowired
    private InstagramUserRepository instagramUserRepository;

    @Autowired
    private InstagramUserService instagramUserService;

    @Autowired
    private InstagramUserQueryService instagramUserQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInstagramUserMockMvc;

    private InstagramUser instagramUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstagramUser createEntity(EntityManager em) {
        InstagramUser instagramUser = new InstagramUser()
            .username(DEFAULT_USERNAME)
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD)
            .age(DEFAULT_AGE)
            .sex(DEFAULT_SEX);
        return instagramUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InstagramUser createUpdatedEntity(EntityManager em) {
        InstagramUser instagramUser = new InstagramUser()
            .username(UPDATED_USERNAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .age(UPDATED_AGE)
            .sex(UPDATED_SEX);
        return instagramUser;
    }

    @BeforeEach
    public void initTest() {
        instagramUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstagramUser() throws Exception {
        int databaseSizeBeforeCreate = instagramUserRepository.findAll().size();

        // Create the InstagramUser
        restInstagramUserMockMvc.perform(post("/api/instagram-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instagramUser)))
            .andExpect(status().isCreated());

        // Validate the InstagramUser in the database
        List<InstagramUser> instagramUserList = instagramUserRepository.findAll();
        assertThat(instagramUserList).hasSize(databaseSizeBeforeCreate + 1);
        InstagramUser testInstagramUser = instagramUserList.get(instagramUserList.size() - 1);
        assertThat(testInstagramUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testInstagramUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInstagramUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testInstagramUser.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testInstagramUser.getSex()).isEqualTo(DEFAULT_SEX);
    }

    @Test
    @Transactional
    public void createInstagramUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = instagramUserRepository.findAll().size();

        // Create the InstagramUser with an existing ID
        instagramUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstagramUserMockMvc.perform(post("/api/instagram-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instagramUser)))
            .andExpect(status().isBadRequest());

        // Validate the InstagramUser in the database
        List<InstagramUser> instagramUserList = instagramUserRepository.findAll();
        assertThat(instagramUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInstagramUsers() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList
        restInstagramUserMockMvc.perform(get("/api/instagram-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instagramUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX)));
    }
    
    @Test
    @Transactional
    public void getInstagramUser() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get the instagramUser
        restInstagramUserMockMvc.perform(get("/api/instagram-users/{id}", instagramUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(instagramUser.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX));
    }


    @Test
    @Transactional
    public void getInstagramUsersByIdFiltering() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        Long id = instagramUser.getId();

        defaultInstagramUserShouldBeFound("id.equals=" + id);
        defaultInstagramUserShouldNotBeFound("id.notEquals=" + id);

        defaultInstagramUserShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInstagramUserShouldNotBeFound("id.greaterThan=" + id);

        defaultInstagramUserShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInstagramUserShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInstagramUsersByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where username equals to DEFAULT_USERNAME
        defaultInstagramUserShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the instagramUserList where username equals to UPDATED_USERNAME
        defaultInstagramUserShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByUsernameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where username not equals to DEFAULT_USERNAME
        defaultInstagramUserShouldNotBeFound("username.notEquals=" + DEFAULT_USERNAME);

        // Get all the instagramUserList where username not equals to UPDATED_USERNAME
        defaultInstagramUserShouldBeFound("username.notEquals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultInstagramUserShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the instagramUserList where username equals to UPDATED_USERNAME
        defaultInstagramUserShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where username is not null
        defaultInstagramUserShouldBeFound("username.specified=true");

        // Get all the instagramUserList where username is null
        defaultInstagramUserShouldNotBeFound("username.specified=false");
    }
                @Test
    @Transactional
    public void getAllInstagramUsersByUsernameContainsSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where username contains DEFAULT_USERNAME
        defaultInstagramUserShouldBeFound("username.contains=" + DEFAULT_USERNAME);

        // Get all the instagramUserList where username contains UPDATED_USERNAME
        defaultInstagramUserShouldNotBeFound("username.contains=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByUsernameNotContainsSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where username does not contain DEFAULT_USERNAME
        defaultInstagramUserShouldNotBeFound("username.doesNotContain=" + DEFAULT_USERNAME);

        // Get all the instagramUserList where username does not contain UPDATED_USERNAME
        defaultInstagramUserShouldBeFound("username.doesNotContain=" + UPDATED_USERNAME);
    }


    @Test
    @Transactional
    public void getAllInstagramUsersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where email equals to DEFAULT_EMAIL
        defaultInstagramUserShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the instagramUserList where email equals to UPDATED_EMAIL
        defaultInstagramUserShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where email not equals to DEFAULT_EMAIL
        defaultInstagramUserShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the instagramUserList where email not equals to UPDATED_EMAIL
        defaultInstagramUserShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultInstagramUserShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the instagramUserList where email equals to UPDATED_EMAIL
        defaultInstagramUserShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where email is not null
        defaultInstagramUserShouldBeFound("email.specified=true");

        // Get all the instagramUserList where email is null
        defaultInstagramUserShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllInstagramUsersByEmailContainsSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where email contains DEFAULT_EMAIL
        defaultInstagramUserShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the instagramUserList where email contains UPDATED_EMAIL
        defaultInstagramUserShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where email does not contain DEFAULT_EMAIL
        defaultInstagramUserShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the instagramUserList where email does not contain UPDATED_EMAIL
        defaultInstagramUserShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllInstagramUsersByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where password equals to DEFAULT_PASSWORD
        defaultInstagramUserShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the instagramUserList where password equals to UPDATED_PASSWORD
        defaultInstagramUserShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByPasswordIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where password not equals to DEFAULT_PASSWORD
        defaultInstagramUserShouldNotBeFound("password.notEquals=" + DEFAULT_PASSWORD);

        // Get all the instagramUserList where password not equals to UPDATED_PASSWORD
        defaultInstagramUserShouldBeFound("password.notEquals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultInstagramUserShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the instagramUserList where password equals to UPDATED_PASSWORD
        defaultInstagramUserShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where password is not null
        defaultInstagramUserShouldBeFound("password.specified=true");

        // Get all the instagramUserList where password is null
        defaultInstagramUserShouldNotBeFound("password.specified=false");
    }
                @Test
    @Transactional
    public void getAllInstagramUsersByPasswordContainsSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where password contains DEFAULT_PASSWORD
        defaultInstagramUserShouldBeFound("password.contains=" + DEFAULT_PASSWORD);

        // Get all the instagramUserList where password contains UPDATED_PASSWORD
        defaultInstagramUserShouldNotBeFound("password.contains=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where password does not contain DEFAULT_PASSWORD
        defaultInstagramUserShouldNotBeFound("password.doesNotContain=" + DEFAULT_PASSWORD);

        // Get all the instagramUserList where password does not contain UPDATED_PASSWORD
        defaultInstagramUserShouldBeFound("password.doesNotContain=" + UPDATED_PASSWORD);
    }


    @Test
    @Transactional
    public void getAllInstagramUsersByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where age equals to DEFAULT_AGE
        defaultInstagramUserShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the instagramUserList where age equals to UPDATED_AGE
        defaultInstagramUserShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByAgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where age not equals to DEFAULT_AGE
        defaultInstagramUserShouldNotBeFound("age.notEquals=" + DEFAULT_AGE);

        // Get all the instagramUserList where age not equals to UPDATED_AGE
        defaultInstagramUserShouldBeFound("age.notEquals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where age in DEFAULT_AGE or UPDATED_AGE
        defaultInstagramUserShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the instagramUserList where age equals to UPDATED_AGE
        defaultInstagramUserShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where age is not null
        defaultInstagramUserShouldBeFound("age.specified=true");

        // Get all the instagramUserList where age is null
        defaultInstagramUserShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByAgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where age is greater than or equal to DEFAULT_AGE
        defaultInstagramUserShouldBeFound("age.greaterThanOrEqual=" + DEFAULT_AGE);

        // Get all the instagramUserList where age is greater than or equal to UPDATED_AGE
        defaultInstagramUserShouldNotBeFound("age.greaterThanOrEqual=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByAgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where age is less than or equal to DEFAULT_AGE
        defaultInstagramUserShouldBeFound("age.lessThanOrEqual=" + DEFAULT_AGE);

        // Get all the instagramUserList where age is less than or equal to SMALLER_AGE
        defaultInstagramUserShouldNotBeFound("age.lessThanOrEqual=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByAgeIsLessThanSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where age is less than DEFAULT_AGE
        defaultInstagramUserShouldNotBeFound("age.lessThan=" + DEFAULT_AGE);

        // Get all the instagramUserList where age is less than UPDATED_AGE
        defaultInstagramUserShouldBeFound("age.lessThan=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersByAgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where age is greater than DEFAULT_AGE
        defaultInstagramUserShouldNotBeFound("age.greaterThan=" + DEFAULT_AGE);

        // Get all the instagramUserList where age is greater than SMALLER_AGE
        defaultInstagramUserShouldBeFound("age.greaterThan=" + SMALLER_AGE);
    }


    @Test
    @Transactional
    public void getAllInstagramUsersBySexIsEqualToSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where sex equals to DEFAULT_SEX
        defaultInstagramUserShouldBeFound("sex.equals=" + DEFAULT_SEX);

        // Get all the instagramUserList where sex equals to UPDATED_SEX
        defaultInstagramUserShouldNotBeFound("sex.equals=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersBySexIsNotEqualToSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where sex not equals to DEFAULT_SEX
        defaultInstagramUserShouldNotBeFound("sex.notEquals=" + DEFAULT_SEX);

        // Get all the instagramUserList where sex not equals to UPDATED_SEX
        defaultInstagramUserShouldBeFound("sex.notEquals=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersBySexIsInShouldWork() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where sex in DEFAULT_SEX or UPDATED_SEX
        defaultInstagramUserShouldBeFound("sex.in=" + DEFAULT_SEX + "," + UPDATED_SEX);

        // Get all the instagramUserList where sex equals to UPDATED_SEX
        defaultInstagramUserShouldNotBeFound("sex.in=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersBySexIsNullOrNotNull() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where sex is not null
        defaultInstagramUserShouldBeFound("sex.specified=true");

        // Get all the instagramUserList where sex is null
        defaultInstagramUserShouldNotBeFound("sex.specified=false");
    }
                @Test
    @Transactional
    public void getAllInstagramUsersBySexContainsSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where sex contains DEFAULT_SEX
        defaultInstagramUserShouldBeFound("sex.contains=" + DEFAULT_SEX);

        // Get all the instagramUserList where sex contains UPDATED_SEX
        defaultInstagramUserShouldNotBeFound("sex.contains=" + UPDATED_SEX);
    }

    @Test
    @Transactional
    public void getAllInstagramUsersBySexNotContainsSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);

        // Get all the instagramUserList where sex does not contain DEFAULT_SEX
        defaultInstagramUserShouldNotBeFound("sex.doesNotContain=" + DEFAULT_SEX);

        // Get all the instagramUserList where sex does not contain UPDATED_SEX
        defaultInstagramUserShouldBeFound("sex.doesNotContain=" + UPDATED_SEX);
    }


    @Test
    @Transactional
    public void getAllInstagramUsersByLikesIsEqualToSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);
        Like likes = LikeResourceIT.createEntity(em);
        em.persist(likes);
        em.flush();
        instagramUser.addLikes(likes);
        instagramUserRepository.saveAndFlush(instagramUser);
        Long likesId = likes.getId();

        // Get all the instagramUserList where likes equals to likesId
        defaultInstagramUserShouldBeFound("likesId.equals=" + likesId);

        // Get all the instagramUserList where likes equals to likesId + 1
        defaultInstagramUserShouldNotBeFound("likesId.equals=" + (likesId + 1));
    }


    @Test
    @Transactional
    public void getAllInstagramUsersByPostsIsEqualToSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);
        Post posts = PostResourceIT.createEntity(em);
        em.persist(posts);
        em.flush();
        instagramUser.addPosts(posts);
        instagramUserRepository.saveAndFlush(instagramUser);
        Long postsId = posts.getId();

        // Get all the instagramUserList where posts equals to postsId
        defaultInstagramUserShouldBeFound("postsId.equals=" + postsId);

        // Get all the instagramUserList where posts equals to postsId + 1
        defaultInstagramUserShouldNotBeFound("postsId.equals=" + (postsId + 1));
    }


    @Test
    @Transactional
    public void getAllInstagramUsersByFollowersIsEqualToSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);
        FollowerFollowing followers = FollowerFollowingResourceIT.createEntity(em);
        em.persist(followers);
        em.flush();
        instagramUser.addFollowers(followers);
        instagramUserRepository.saveAndFlush(instagramUser);
        Long followersId = followers.getId();

        // Get all the instagramUserList where followers equals to followersId
        defaultInstagramUserShouldBeFound("followersId.equals=" + followersId);

        // Get all the instagramUserList where followers equals to followersId + 1
        defaultInstagramUserShouldNotBeFound("followersId.equals=" + (followersId + 1));
    }


    @Test
    @Transactional
    public void getAllInstagramUsersByFollowingIsEqualToSomething() throws Exception {
        // Initialize the database
        instagramUserRepository.saveAndFlush(instagramUser);
        FollowerFollowing following = FollowerFollowingResourceIT.createEntity(em);
        em.persist(following);
        em.flush();
        instagramUser.addFollowing(following);
        instagramUserRepository.saveAndFlush(instagramUser);
        Long followingId = following.getId();

        // Get all the instagramUserList where following equals to followingId
        defaultInstagramUserShouldBeFound("followingId.equals=" + followingId);

        // Get all the instagramUserList where following equals to followingId + 1
        defaultInstagramUserShouldNotBeFound("followingId.equals=" + (followingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInstagramUserShouldBeFound(String filter) throws Exception {
        restInstagramUserMockMvc.perform(get("/api/instagram-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(instagramUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX)));

        // Check, that the count call also returns 1
        restInstagramUserMockMvc.perform(get("/api/instagram-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInstagramUserShouldNotBeFound(String filter) throws Exception {
        restInstagramUserMockMvc.perform(get("/api/instagram-users?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInstagramUserMockMvc.perform(get("/api/instagram-users/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingInstagramUser() throws Exception {
        // Get the instagramUser
        restInstagramUserMockMvc.perform(get("/api/instagram-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstagramUser() throws Exception {
        // Initialize the database
        instagramUserService.save(instagramUser);

        int databaseSizeBeforeUpdate = instagramUserRepository.findAll().size();

        // Update the instagramUser
        InstagramUser updatedInstagramUser = instagramUserRepository.findById(instagramUser.getId()).get();
        // Disconnect from session so that the updates on updatedInstagramUser are not directly saved in db
        em.detach(updatedInstagramUser);
        updatedInstagramUser
            .username(UPDATED_USERNAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD)
            .age(UPDATED_AGE)
            .sex(UPDATED_SEX);

        restInstagramUserMockMvc.perform(put("/api/instagram-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInstagramUser)))
            .andExpect(status().isOk());

        // Validate the InstagramUser in the database
        List<InstagramUser> instagramUserList = instagramUserRepository.findAll();
        assertThat(instagramUserList).hasSize(databaseSizeBeforeUpdate);
        InstagramUser testInstagramUser = instagramUserList.get(instagramUserList.size() - 1);
        assertThat(testInstagramUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testInstagramUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInstagramUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testInstagramUser.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testInstagramUser.getSex()).isEqualTo(UPDATED_SEX);
    }

    @Test
    @Transactional
    public void updateNonExistingInstagramUser() throws Exception {
        int databaseSizeBeforeUpdate = instagramUserRepository.findAll().size();

        // Create the InstagramUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstagramUserMockMvc.perform(put("/api/instagram-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(instagramUser)))
            .andExpect(status().isBadRequest());

        // Validate the InstagramUser in the database
        List<InstagramUser> instagramUserList = instagramUserRepository.findAll();
        assertThat(instagramUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInstagramUser() throws Exception {
        // Initialize the database
        instagramUserService.save(instagramUser);

        int databaseSizeBeforeDelete = instagramUserRepository.findAll().size();

        // Delete the instagramUser
        restInstagramUserMockMvc.perform(delete("/api/instagram-users/{id}", instagramUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InstagramUser> instagramUserList = instagramUserRepository.findAll();
        assertThat(instagramUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
