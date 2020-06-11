package com.bilous.instagram.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.bilous.instagram.web.rest.TestUtil;

public class InstagramUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstagramUser.class);
        InstagramUser instagramUser1 = new InstagramUser();
        instagramUser1.setId(1L);
        InstagramUser instagramUser2 = new InstagramUser();
        instagramUser2.setId(instagramUser1.getId());
        assertThat(instagramUser1).isEqualTo(instagramUser2);
        instagramUser2.setId(2L);
        assertThat(instagramUser1).isNotEqualTo(instagramUser2);
        instagramUser1.setId(null);
        assertThat(instagramUser1).isNotEqualTo(instagramUser2);
    }
}
