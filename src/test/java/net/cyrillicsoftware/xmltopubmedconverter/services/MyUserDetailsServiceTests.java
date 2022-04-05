package net.cyrillicsoftware.xmltopubmedconverter.services;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
public class MyUserDetailsServiceTests {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Test
    @DisplayName("Test 1 - Successful user loading")
    public void givenUserDetails_whenLoadUser_thenReturnExistingUser(){

        UserDetails userDetails = myUserDetailsService.loadUserByUsername("");

        assertThat(userDetails.getUsername()).isEqualTo("cyrillicsoftware");
        assertThat(userDetails.getPassword()).isEqualTo("cyrillicsoftware_2022");
        assertThat(userDetails.getAuthorities()).isEmpty();
    }

}
