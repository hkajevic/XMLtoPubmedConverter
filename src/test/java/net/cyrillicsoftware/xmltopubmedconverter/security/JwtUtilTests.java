package net.cyrillicsoftware.xmltopubmedconverter.security;

import static org.assertj.core.api.Assertions.*;

import net.cyrillicsoftware.xmltopubmedconverter.services.MyUserDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

@SpringBootTest
public class JwtUtilTests {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @DisplayName("Test 1 - JWT not expired")
    @Test
    public void givenToken_whenVerified_thenValid(){

        UserDetails userDetails = myUserDetailsService.loadUserByUsername("cyrillicsoftware");
        String token = jwtUtil.generateToken(userDetails);

        boolean isExpired = jwtUtil.validateToken(token, userDetails);

        assertThat(isExpired).isEqualTo(true);
    }

    @DisplayName("Test 2 - JWT expired")
    @Test
    public void givenUserAndToken_whenVerified_thenReturnNotExpired(){
        UserDetails userDetails = myUserDetailsService.loadUserByUsername("cyrillicsoftware");
        String expiredToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjeXJpbGxpY3NvZnR3YXJlIiwiZXhwIjoxNjQ4NDc4MTkyLCJpYXQiOjE2NDg0NzgxMzJ9.-IUYVHasnzCuzQ8SACrRgYXqMdcBEYC7ilked0F4qlM";

        boolean notExpired = jwtUtil.validateToken(expiredToken, userDetails);

        assertThat(notExpired).isEqualTo(false);
    }

    @DisplayName("Test 3 - Valid username in token")
    @Test
    public void givenUserAndToken_whenCreatedToken_thenReturnValidUserInToken(){
        UserDetails userDetails = myUserDetailsService.loadUserByUsername("cyrillicsoftware");

        String generatedToken = jwtUtil.generateToken(userDetails);

        String username = jwtUtil.extractUsername(generatedToken);

        assertThat(username).isNotEmpty();
        assertThat(username).isEqualTo("cyrillicsoftware");
    }

    @DisplayName("Test 4 - Invalid username in token")
    @Test
    public void givenTokenAndUser_whenVerified_thenReturnInvalidUsername(){
        UserDetails userDetails = new User("wrong_username", "cyrillicsoftware_2022",new ArrayList<>());

        String token = jwtUtil.generateToken(userDetails);

        boolean validUsername = jwtUtil.validateToken(token, myUserDetailsService.loadUserByUsername("cyrillicsoftware"));

        assertThat(validUsername).isEqualTo(false);
    }
}
