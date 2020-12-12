package com.initex.canoe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.initex.canoe.domain.EventFormat;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.services.EventFormatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.initex.canoe.services.utils.Constants.DATABASE_READ_ERROR;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EventFormatController.class)
class EventFormatControllerTest {

    private static final String URL_TEMPLATE = "/api/v1/eventFormats";
    private final String errorResponseMessage = "{\"message\":\"%s\"}";
    private String requestBody;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private EventFormatService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        requestBody = mapper.writeValueAsString(createEventFormats());
    }

    @Test
    void getEventFormat() throws Exception {
        when(service.getEventFormats()).thenReturn(Collections.singletonList(createEventFormats()));
        mockMvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(Collections.singletonList(createEventFormats())))));
    }

    @Test
    void getEventFormat_ReturnErrorMessage() throws Exception {
        when(service.getEventFormats()).thenThrow(new CanoeException(DATABASE_READ_ERROR));
        mockMvc.perform(get(URL_TEMPLATE))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_READ_ERROR)));
    }

    private EventFormat createEventFormats() {
        EventFormat eventFormat = new EventFormat();
        eventFormat.setId(1L);
        eventFormat.setFormatName("FormatOne");
        return eventFormat;
    }

}