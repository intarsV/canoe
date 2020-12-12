package com.initex.canoe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.initex.canoe.domain.AgeGroup;
import com.initex.canoe.dto.AgeGroupDTO;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.services.AgeGroupService;
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
@WebMvcTest(AgeGroupController.class)
class AgeGroupControllerTest {

    private static final String URL_TEMPLATE = "/api/v1/groups";
    private final String errorResponseMessage = "{\"message\":\"%s\"}";
    private String requestBody;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AgeGroupService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        requestBody = mapper.writeValueAsString(createAgeGroup());
    }

    @Test
    void getAgeGroup() throws Exception {
        when(service.getGroups()).thenReturn(Collections.singletonList(createAgeGroup()));
        mockMvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(Collections.singletonList(createAgeGroup())))));
    }

    @Test
    void getAgeGroup_ReturnErrorMessage() throws Exception {
        when(service.getGroups()).thenThrow(new CanoeException(DATABASE_READ_ERROR));
        mockMvc.perform(get(URL_TEMPLATE))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_READ_ERROR)));
    }

    @Test
    void addCAgeGroup() throws Exception {
        when(service.addGroup(any(AgeGroupDTO.class))).thenReturn(createAgeGroupDTO());
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(createAgeGroupDTO()))));
    }

    @Test
    void addCAgeGroup_GetDuplicateError() throws Exception {
        when(service.addGroup(any(AgeGroupDTO.class))).thenThrow(new CanoeException(DUPLICATE_DATA_EXISTS));
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format(errorResponseMessage, DUPLICATE_DATA_EXISTS)));
    }

    @Test
    void addCAgeGroup_SaveError() throws Exception {
        when(service.addGroup(any(AgeGroupDTO.class))).thenThrow(new CanoeException(DATABASE_SAVE_ERROR));
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_SAVE_ERROR)));
    }

    @Test
    void updateAgeGroup() throws Exception {
        when(service.updateGroup(anyString(), any(AgeGroupDTO.class))).thenReturn(createAgeGroupDTO());
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(createAgeGroupDTO()))));
    }

    @Test
    void updateAgeGroup_UpdateError_NoSuchRecord() throws Exception {
        when(service.updateGroup(anyString(), any(AgeGroupDTO.class))).thenThrow(new CanoeException(NO_SUCH_RECORD_ERROR));
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format(errorResponseMessage, NO_SUCH_RECORD_ERROR)));
    }

    @Test
    void updateAgeGroup_UpdateError() throws Exception {
        when(service.updateGroup(anyString(), any(AgeGroupDTO.class))).thenThrow(new CanoeException(DATABASE_UPDATE_ERROR));
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_UPDATE_ERROR)));
    }

    private AgeGroup createAgeGroup() {
        AgeGroup ageGroup = new AgeGroup();
        ageGroup.setId(1L);
        ageGroup.setAgeGroup("U-18");
        return ageGroup;
    }

    private AgeGroupDTO createAgeGroupDTO() {
        AgeGroupDTO ageGroupDTO = new AgeGroupDTO();
        ageGroupDTO.setId(1L);
        ageGroupDTO.setAgeGroup("U-18");
        return ageGroupDTO;
    }
}