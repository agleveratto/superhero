package com.agleveratto.superhero.infrastructure.controllers;

import com.agleveratto.superhero.infrastructure.config.JwtUtils;
import com.agleveratto.superhero.infrastructure.dto.AuthorizationRequest;
import com.agleveratto.superhero.infrastructure.utils.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    UserUtils userUtils;

    @MockBean
    JwtUtils jwtUtils;

    static AuthorizationRequest adminRequest, userRequest;
    static UserDetails userDetails;

    @BeforeAll
    static void setup(){
        adminRequest = new AuthorizationRequest();
        adminRequest.setEmail("agleveratto@gmail.com");
        adminRequest.setPassword("password");

        userRequest = new AuthorizationRequest("enzo.guido@mindata.es","mindata");

        userDetails = new User(adminRequest.getEmail(), adminRequest.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void authenticate__() throws Exception {
        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(adminRequest)))
                .andExpect(status().isForbidden()) // TODO revisar
        ;
        verify(authenticationManager, never()).authenticate(new UsernamePasswordAuthenticationToken(adminRequest.getEmail(), adminRequest.getPassword()));
        verify(userUtils, never()).findUserByEmail(adminRequest.getEmail());
    }


}
