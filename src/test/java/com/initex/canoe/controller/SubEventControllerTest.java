package com.initex.canoe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.initex.canoe.domain.EventFormat;
import com.initex.canoe.domain.SubEvent;
import com.initex.canoe.dto.SubEventDTO;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.services.SubEventService;
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
@WebMvcTest(SubEventController.class)
class SubEventControllerTest {

    private static final String URL_TEMPLATE = "/api/v1/sub-events";
    private final String errorResponseMessage = "{\"message\":\"%s\"}";
    private String requestBody;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private SubEventService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        requestBody = mapper.writeValueAsString(createSubEventDTO());
    }

    @Test
    void getSubEvent() throws Exception {
        when(service.getSubEvent()).thenReturn(Collections.singletonList(createSubEvent()));
        mockMvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(Collections.singletonList(createSubEvent())))));
    }

    @Test
    void getSubEvent_ReturnErrorMessage() throws Exception {
        when(service.getSubEvent()).thenThrow(new CanoeException(DATABASE_READ_ERROR));
        mockMvc.perform(get(URL_TEMPLATE))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_READ_ERROR)));
    }

    @Test
    void addSubEvent() throws Exception {
        when(service.addSubEvent(any(SubEventDTO.class))).thenReturn(createSubEventDTO());
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(createSubEventDTO()))));
    }

    @Test
    void addSubEvent_GetDuplicateError() throws Exception {
        when(service.addSubEvent(any(SubEventDTO.class))).thenThrow(new CanoeException(DUPLICATE_DATA_EXISTS));
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format(errorResponseMessage, DUPLICATE_DATA_EXISTS)));
    }

    @Test
    void addSubEvent_SaveError() throws Exception {
        when(service.addSubEvent(any(SubEventDTO.class))).thenThrow(new CanoeException(DATABASE_SAVE_ERROR));
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_SAVE_ERROR)));
    }

    @Test
    void updateSubEvent() throws Exception {
        when(service.updateSubEvent(anyString(), any(SubEventDTO.class))).thenReturn(createSubEventDTO());
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(createSubEventDTO()))));
    }

    @Test
    void updateSubEvent_UpdateError_NoSuchRecord() throws Exception {
        when(service.updateSubEvent(anyString(), any(SubEventDTO.class))).thenThrow(new CanoeException(NO_SUCH_RECORD_ERROR));
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format(errorResponseMessage, NO_SUCH_RECORD_ERROR)));
    }

    @Test
    void updateSubEvent_UpdateError() throws Exception {
        when(service.updateSubEvent(anyString(), any(SubEventDTO.class))).thenThrow(new CanoeException(DATABASE_UPDATE_ERROR));
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_UPDATE_ERROR)));
    }

    private SubEvent createSubEvent() {
        SubEvent subEvent = new SubEvent();
        subEvent.setId(1L);
        subEvent.setSubEvent("FINAL DESTINATION");
        subEvent.setEventFormat(new EventFormat());
        return subEvent;
    }

    private SubEventDTO createSubEventDTO() {
        SubEventDTO subEventDTO = new SubEventDTO();
        subEventDTO.setId(1L);
        subEventDTO.setSubEvent("FINAL DESTINATION");
        subEventDTO.setEventFormat(new EventFormat());
        return subEventDTO;
    }
}