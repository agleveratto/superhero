package com.agleveratto.superhero.infrastructure.controllers;

import com.agleveratto.superhero.application.services.SuperheroService;
import com.agleveratto.superhero.infrastructure.config.JwtUtils;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import com.agleveratto.superhero.infrastructure.utils.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SuperheroController.class)
public class SuperheroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    SuperheroService superheroService;

    @MockBean
    UserUtils userUtils;

    @MockBean
    JwtUtils jwtUtils;

    private static Superhero superhero;

    @BeforeAll
    static void setup(){
        superhero = new Superhero();
        superhero.setId(1L);
        superhero.setName("SUPERMAN");

    }

    @Test
    void findAll_withoutToken_thenReturn401() throws Exception {
        mockMvc.perform(get("/api/v1/"))
                .andExpect(status().isUnauthorized());
        verify(superheroService, never()).findAll();
    }

    @Test
    @WithMockUser(username = "agleveratto@gmail.com")
    void findAll_withValidMockUser_thenReturn200() throws Exception {
        when(superheroService.findAll()).thenReturn(List.of(superhero));
        mockMvc.perform(get("/api/v1/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(superhero.getId()))
                .andExpect(jsonPath("$[0].name").value(superhero.getName()));
        verify(superheroService).findAll();
    }

    @Test
    void findById_withoutToken_thenReturn401() throws Exception {
        mockMvc.perform(get("/api/v1/{id}", superhero.getId()))
                .andExpect(status().isUnauthorized());
        verify(superheroService, never()).findById(superhero.getId());
    }

    @Test
    @WithMockUser(username = "agleveratto@gmail.com")
    void findById_withValidMockUser_thenReturn200() throws Exception {
        when(superheroService.findById(superhero.getId())).thenReturn(superhero);
        mockMvc.perform(get("/api/v1/{id}", superhero.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(superhero.getId()))
                .andExpect(jsonPath("$.name").value(superhero.getName()));
        verify(superheroService).findById(superhero.getId());
    }

    @Test
    void findByContains_withoutToken_thenReturn401() throws Exception {
        mockMvc.perform(get("/api/v1/name/{nameContains}", "man"))
                .andExpect(status().isUnauthorized());
        verify(superheroService, never()).findByContains("man");
    }

    @Test
    @WithMockUser(username = "agleveratto@gmail.com")
    void findByContains_withValidMockUser_thenReturn200() throws Exception {
        when(superheroService.findByContains("man")).thenReturn(List.of(superhero));
        mockMvc.perform(get("/api/v1/name/{nameContains}", "man"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(superhero.getId()))
                .andExpect(jsonPath("$[0].name").value(superhero.getName()));
        verify(superheroService).findByContains("man");
    }

    @Test
    void update_withoutToken_thenReturn403() throws Exception {
        mockMvc.perform(put("/api/v1/")
                        .content(new ObjectMapper().writeValueAsString(superhero)))
                .andExpect(status().isForbidden());
        verify(superheroService, never()).update(superhero);
    }

    @Test
    void delete_withoutToken_thenReturn403() throws Exception {
        mockMvc.perform(delete("/api/v1/{id}", superhero.getId()))
                .andExpect(status().isForbidden());
        verify(superheroService, never()).delete(superhero.getId());
    }

}
