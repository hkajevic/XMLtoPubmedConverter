package net.cyrillicsoftware.xmltopubmedconverter.security;

import net.cyrillicsoftware.xmltopubmedconverter.services.MyUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationFilterTests {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private MyUserDetailsService myUserDetailsService;

    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private FilterChain filterChain;

    @BeforeEach
    public void setUp(){
        httpServletRequest = Mockito.mock(HttpServletRequest.class);
        httpServletResponse = Mockito.mock(HttpServletResponse.class);
        filterChain = Mockito.mock(FilterChain.class);
    }

    @Test
    public void givenRequestAndResponseAndChain_whenSentRequest_thenAuthenticate(){

        BDDMockito.given(httpServletRequest.getHeader("Authorization"))
                .willReturn("token");
        //BDDMockito.given()
    }

}
