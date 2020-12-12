package com.initex.canoe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.initex.canoe.domain.Event;
import com.initex.canoe.domain.EventFormat;
import com.initex.canoe.dto.EventDTO;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.services.EventService;
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
@WebMvcTest(EventController.class)
class EventControllerTest {

    private static final String URL_TEMPLATE = "/api/v1/events";
    private final String errorResponseMessage = "{\"message\":\"%s\"}";
    private String requestBody;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private EventService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        requestBody = mapper.writeValueAsString(createEvent());
    }

    @Test
    void getEvents() throws Exception {
        when(service.getEvents()).thenReturn(Collections.singletonList(createEvent()));
        mockMvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(Collections.singletonList(createEvent())))));
    }

    @Test
    void getEvents_ReturnErrorMessage() throws Exception {
        when(service.getEvents()).thenThrow(new CanoeException(DATABASE_READ_ERROR));
        mockMvc.perform(get(URL_TEMPLATE))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_READ_ERROR)));
    }

    @Test
    void addEvents() throws Exception {
        when(service.addEvent(any(EventDTO.class))).thenReturn(createEventDTO());
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(createEventDTO()))));
    }

    @Test
    void addCEvents_GetDuplicateError() throws Exception {
        when(service.addEvent(any(EventDTO.class))).thenThrow(new CanoeException(DUPLICATE_DATA_EXISTS));
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format(errorResponseMessage, DUPLICATE_DATA_EXISTS)));
    }

    @Test
    void addEvents_SaveError() throws Exception {
        when(service.addEvent(any(EventDTO.class))).thenThrow(new CanoeException(DATABASE_SAVE_ERROR));
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_SAVE_ERROR)));
    }

    @Test
    void updateCompetitor() throws Exception {
        when(service.updateEvent(anyString(), any(EventDTO.class))).thenReturn(createEventDTO());
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(createEventDTO()))));
    }

    @Test
    void updateCompetitor_UpdateError_NoSuchRecord() throws Exception {
        when(service.updateEvent(anyString(), any(EventDTO.class))).thenThrow(new CanoeException(NO_SUCH_RECORD_ERROR));
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format(errorResponseMessage, NO_SUCH_RECORD_ERROR)));
    }

    @Test
    void updateCompetitor_UpdateError() throws Exception {
        when(service.updateEvent(anyString(), any(EventDTO.class))).thenThrow(new CanoeException(DATABASE_UPDATE_ERROR));
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_UPDATE_ERROR)));
    }

    private Event createEvent() {
        Event event = new Event();
        event.setId(1L);
        event.setEventName("ChristmasParty");
        event.setEventFormat(new EventFormat());
        return event;
    }

    private EventDTO createEventDTO() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(1L);
        eventDTO.setEventName("ChristmasParty");
        eventDTO.setEventFormat(new EventFormat());
        return eventDTO;
    }
}