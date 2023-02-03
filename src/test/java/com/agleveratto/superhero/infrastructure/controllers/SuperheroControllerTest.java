package com.agleveratto.superhero.infrastructure.controllers;

import com.agleveratto.superhero.application.exceptions.NotFoundException;
import com.agleveratto.superhero.application.services.SuperheroService;
import com.agleveratto.superhero.infrastructure.config.JwtUtils;
import com.agleveratto.superhero.infrastructure.entities.Superhero;
import com.agleveratto.superhero.infrastructure.utils.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

    public static final String SUPERHERO_MODIFIED = "superhero modified";
    public static final String SUPERHERO_DELETED = "superhero deleted";

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
    @WithMockUser(username = "agleveratto@gmail.com")
    void findAll_withValidMockUser_thenReturn404() throws Exception {
        when(superheroService.findAll()).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/api/v1/"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
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
    @WithMockUser(username = "agleveratto@gmail.com")
    void findById_withValidMockUser_thenReturn404() throws Exception {
        when(superheroService.findById(2L)).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/api/v1/{id}", 2L))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
        verify(superheroService).findById(2L);
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
    @WithMockUser(username = "agleveratto@gmail.com")
    void findByContains_withValidMockUser_thenReturn404() throws Exception {
        when(superheroService.findByContains("men")).thenThrow(NotFoundException.class);
        mockMvc.perform(get("/api/v1/name/{nameContains}", "men"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
        verify(superheroService).findByContains("men");
    }

    @Test
    void update_withoutUserDetails_thenReturn401() throws Exception {
        mockMvc.perform(put("/api/v1/")
                    .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails
    void update_withUserDetails_thenReturn200() throws Exception {
        ArgumentCaptor<Superhero> superheroArgumentCaptor = ArgumentCaptor.forClass(Superhero.class);
        when(superheroService.update(superhero)).thenReturn(SUPERHERO_MODIFIED);
        mockMvc.perform(put("/api/v1/")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(superhero)))
                .andExpect(status().isOk());
        verify(superheroService).update(superheroArgumentCaptor.capture());
        Superhero superheroUpdated = superheroArgumentCaptor.getValue();
        assertThat(superheroUpdated.getId()).isEqualTo(superhero.getId());
        assertThat(superheroUpdated.getName()).isEqualTo(superhero.getName());
    }

    @Test
    void delete_withoutUserDetails_thenReturn401() throws Exception {
        mockMvc.perform(delete("/api/v1/{id}", superhero.getId())
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails
    void delete_withUserDetails_thenReturn200() throws Exception {
        when(superheroService.delete(superhero.getId())).thenReturn(SUPERHERO_DELETED);
        mockMvc.perform(delete("/api/v1/{id}", superhero.getId())
                        .with(csrf()))
                .andExpect(status().isOk());
        verify(superheroService).delete(superhero.getId());
    }
}
