package com.initex.canoe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.initex.canoe.domain.Competitor;
import com.initex.canoe.dto.CompetitorDTO;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.services.CompetitorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.initex.canoe.services.utils.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CompetitorController.class)
class CompetitorControllerTest {

    private static final String URL_TEMPLATE = "/api/v1/competitors";
    private final String errorResponseMessage = "{\"message\":\"%s\"}";
    private String requestBody;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CompetitorService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        requestBody = mapper.writeValueAsString(createCompetitorDTO());
    }

    @Test
    void getCompetitors() throws Exception {
        when(service.getCompetitors()).thenReturn(Collections.singletonList(createCompetitor()));
        mockMvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(Collections.singletonList(createCompetitor())))));
    }

    @Test
    void getCompetitors_ReturnErrorMessage() throws Exception {
        when(service.getCompetitors()).thenThrow(new CanoeException(DATABASE_READ_ERROR));
        mockMvc.perform(get(URL_TEMPLATE))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_READ_ERROR)));
    }

    @Test
    void addCompetitor() throws Exception {
        when(service.addCompetitor(any(CompetitorDTO.class))).thenReturn(createCompetitorDTO());
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(createCompetitorDTO()))));
    }

    @Test
    void addCompetitor_GetDuplicateError() throws Exception {
        when(service.addCompetitor(any(CompetitorDTO.class))).thenThrow(new CanoeException(DUPLICATE_DATA_EXISTS));
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format(errorResponseMessage, DUPLICATE_DATA_EXISTS)));
    }

    @Test
    void addCompetitor_SaveError() throws Exception {
        when(service.addCompetitor(any(CompetitorDTO.class))).thenThrow(new CanoeException(DATABASE_SAVE_ERROR));
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_SAVE_ERROR)));
    }

    @Test
    void updateCompetitor() throws Exception {
        when(service.updateCompetitor(anyString(), any(CompetitorDTO.class))).thenReturn(createCompetitorDTO());
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(createCompetitorDTO()))));
    }

    @Test
    void updateCompetitor_UpdateError_NoSuchRecord() throws Exception {
        when(service.updateCompetitor(anyString(), any(CompetitorDTO.class))).thenThrow(new CanoeException(NO_SUCH_RECORD_ERROR));
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format(errorResponseMessage, NO_SUCH_RECORD_ERROR)));
    }

    @Test
    void updateCompetitor_UpdateError() throws Exception {
        when(service.updateCompetitor(anyString(), any(CompetitorDTO.class))).thenThrow(new CanoeException(DATABASE_UPDATE_ERROR));
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_UPDATE_ERROR)));
    }

    private Competitor createCompetitor() {
        Competitor competitor = new Competitor();
        competitor.setId(1L);
        competitor.setCompetitorName("Janka");
        competitor.setBirthYear(2000);
        competitor.setCountry("LVA");
        return competitor;
    }

    private CompetitorDTO createCompetitorDTO() {
        CompetitorDTO competitorDTO = new CompetitorDTO();
        competitorDTO.setId(1L);
        competitorDTO.setCompetitorName("Janka");
        competitorDTO.setBirthYear(2000);
        competitorDTO.setCountry("LVA");
        return competitorDTO;
    }

}