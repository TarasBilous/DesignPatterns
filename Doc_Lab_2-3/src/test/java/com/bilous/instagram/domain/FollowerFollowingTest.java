package com.bilous.instagram.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bilous.instagram.web.rest.TestUtil;

public class FollowerFollowingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FollowerFollowing.class);
        FollowerFollowing followerFollowing1 = new FollowerFollowing();
        followerFollowing1.setId(1L);
        FollowerFollowing followerFollowing2 = new FollowerFollowing();
        followerFollowing2.setId(followerFollowing1.getId());
        assertThat(followerFollowing1).isEqualTo(followerFollowing2);
        followerFollowing2.setId(2L);
        assertThat(followerFollowing1).isNotEqualTo(followerFollowing2);
        followerFollowing1.setId(null);
        assertThat(followerFollowing1).isNotEqualTo(followerFollowing2);
    }
}
