package com.bilous.instagram.repository;

import com.bilous.instagram.domain.FollowerFollowing;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FollowerFollowing entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FollowerFollowingRepository extends JpaRepository<FollowerFollowing, Long>, JpaSpecificationExecutor<FollowerFollowing> {
}
