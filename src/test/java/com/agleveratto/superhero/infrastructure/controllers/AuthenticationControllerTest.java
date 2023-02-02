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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

    static AuthorizationRequest adminRequest, userWithoutRoles;
    static UserDetails userDetails;

    @BeforeAll
    static void setup(){
        adminRequest = new AuthorizationRequest();
        adminRequest.setEmail("agleveratto@gmail.com");
        adminRequest.setPassword("password");

        userWithoutRoles = new AuthorizationRequest("enzo.guido@gmail.com","123123");

        userDetails = new User(adminRequest.getEmail(), adminRequest.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    @WithUserDetails
    void authenticate_givenValidUser_thenReturn200() throws Exception {
        when(userUtils.findUserByEmail(adminRequest.getEmail())).thenReturn(userDetails);
        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(adminRequest))
                        .with(csrf()))
                .andExpect(status().isOk());
        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(adminRequest.getEmail(), adminRequest.getPassword()));
        verify(userUtils).findUserByEmail(adminRequest.getEmail());
        verify(jwtUtils).generateToken(userDetails);
    }

    @Test
    @WithUserDetails
    void authenticate_givenInvalidUser_thenReturn400() throws Exception {
        when(userUtils.findUserByEmail(adminRequest.getEmail())).thenReturn(null);
        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userWithoutRoles))
                        .with(csrf()))
                .andExpect(status().isBadRequest());
        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(userWithoutRoles.getEmail(), userWithoutRoles.getPassword()));
        verify(userUtils).findUserByEmail(userWithoutRoles.getEmail());
        verify(jwtUtils, never()).generateToken(userDetails);
    }

}
