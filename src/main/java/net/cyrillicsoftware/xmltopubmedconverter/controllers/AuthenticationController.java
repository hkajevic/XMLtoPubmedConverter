package net.cyrillicsoftware.xmltopubmedconverter.controllers;

import net.cyrillicsoftware.xmltopubmedconverter.payload.AuthenticationRequest;
import net.cyrillicsoftware.xmltopubmedconverter.payload.AuthenticationResponse;
import net.cyrillicsoftware.xmltopubmedconverter.security.JwtUtil;
import net.cyrillicsoftware.xmltopubmedconverter.services.MyUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final JwtUtil jwtUtil;

    private final MyUserDetailsService userDetailsService;

    //in order to authenticate user we need this manager
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(JwtUtil jwtUtil,
                                    MyUserDetailsService userDetailsService,
                                    AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));

        }catch(BadCredentialsException e){
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
