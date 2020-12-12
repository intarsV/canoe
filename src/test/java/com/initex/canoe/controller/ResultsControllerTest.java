package com.initex.canoe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.initex.canoe.dto.MyResponse;
import com.initex.canoe.dto.ResultQuery;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.services.ResultsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayOutputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ResultsController.class)
class ResultsControllerTest {

    private static final String URL_TEMPLATE = "/api/v1/results";
    private static final String WRONG_REPORT_SELECTION = "Wrong report selection!";
    private final String errorResponseMessage = "{\"message\":\"%s\"}";

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ResultsService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getResults() throws Exception {
        when(service.getReport(any(ResultQuery.class))).thenReturn(new ByteArrayOutputStream());
        mockMvc.perform(get(URL_TEMPLATE + "?eventId=1"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(createMyResponse())));
    }

    @Test
    void getResults_ReturnErrorMessage() throws Exception {
        when(service.getReport(any(ResultQuery.class))).thenThrow(new CanoeException(WRONG_REPORT_SELECTION));
        mockMvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format(errorResponseMessage, WRONG_REPORT_SELECTION)));
    }

    private MyResponse createMyResponse() {
        Object o = "";
        return new MyResponse(o);
    }
}