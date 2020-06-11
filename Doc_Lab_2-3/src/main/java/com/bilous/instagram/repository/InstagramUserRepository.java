package com.bilous.instagram.repository;

import com.bilous.instagram.domain.InstagramUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InstagramUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstagramUserRepository extends JpaRepository<InstagramUser, Long>, JpaSpecificationExecutor<InstagramUser> {
}
