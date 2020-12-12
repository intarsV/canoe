package com.initex.canoe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.initex.canoe.domain.BoatClass;
import com.initex.canoe.domain.Event;
import com.initex.canoe.domain.RaceConfig;
import com.initex.canoe.dto.CountryDTO;
import com.initex.canoe.dto.RaceConfigDTO;
import com.initex.canoe.dto.RaceConfigQuery;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.services.CountryService;
import com.initex.canoe.services.RaceConfigService;
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
import static com.initex.canoe.services.utils.Constants.DATABASE_SAVE_ERROR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RaceConfigController.class)
class RaceConfigControllerTest {

    private static final String URL_TEMPLATE = "/api/v1/race-config";
    private final String errorResponseMessage = "{\"message\":\"%s\"}";
    private String requestBody;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private RaceConfigService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        requestBody = mapper.writeValueAsString(createRaceConfig());
    }

    @Test
    void getRaceConfig() throws Exception {
        when(service.getList(any(RaceConfigQuery.class))).thenReturn(Collections.singletonList(createRaceConfig()));
        mockMvc.perform(get(URL_TEMPLATE + "?eventId=1"))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(Collections.singletonList(createRaceConfig())))));
    }

    @Test
    void getRaceConfig_ReturnErrorMessage() throws Exception {
        when(service.getList(any(RaceConfigQuery.class))).thenThrow(new CanoeException(DATABASE_READ_ERROR));
        mockMvc.perform(get(URL_TEMPLATE + "?eventId=1"))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_READ_ERROR)));
    }

    @Test
    void addRaceConfig() throws Exception {
        when(service.addRaceConfig(any(RaceConfigDTO.class))).thenReturn(createRaceConfigDTO());
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(createRaceConfigDTO()))));
    }

    @Test
    void addRaceConfig_GetDuplicateError() throws Exception {
        when(service.addRaceConfig(any(RaceConfigDTO.class))).thenThrow(new CanoeException(DUPLICATE_DATA_EXISTS));
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format(errorResponseMessage, DUPLICATE_DATA_EXISTS)));
    }

    @Test
    void addRaceConfig_SaveError() throws Exception {
        when(service.addRaceConfig(any(RaceConfigDTO.class))).thenThrow(new CanoeException(DATABASE_SAVE_ERROR));
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_SAVE_ERROR)));
    }

    @Test
    void updateRaceConfig() throws Exception {
        when(service.updateRaceConfig(anyString(), any(RaceConfigDTO.class))).thenReturn(createRaceConfigDTO());
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(createRaceConfigDTO()))));
    }

    @Test
    void updateRaceConfig_UpdateError_NoSuchRecord() throws Exception {
        when(service.updateRaceConfig(anyString(), any(RaceConfigDTO.class))).thenThrow(new CanoeException(NO_SUCH_RECORD_ERROR));
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format(errorResponseMessage, NO_SUCH_RECORD_ERROR)));
    }

    @Test
    void updateRaceConfig_UpdateError() throws Exception {
        when(service.updateRaceConfig(anyString(), any(RaceConfigDTO.class))).thenThrow(new CanoeException(DATABASE_UPDATE_ERROR));
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_UPDATE_ERROR)));
    }

    private RaceConfig createRaceConfig() {
        RaceConfig raceConfig = new RaceConfig();
        raceConfig.setId(1L);
        raceConfig.setBoatClass(new BoatClass());
        raceConfig.setEvent(new Event());
        raceConfig.setHeat1(1);
        raceConfig.setHeat2(2);
        raceConfig.setSemiFinal(3);
        return raceConfig;
    }

    private RaceConfigDTO createRaceConfigDTO() {
        RaceConfigDTO raceConfigDTO = new RaceConfigDTO();
        raceConfigDTO.setId(1L);
        raceConfigDTO.setBoatClass(new BoatClass());
        raceConfigDTO.setEvent(new Event());
        raceConfigDTO.setHeat1(1);
        raceConfigDTO.setHeat2(2);
        raceConfigDTO.setSemiFinal(3);
        return raceConfigDTO;
    }
}