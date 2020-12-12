package com.initex.canoe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.initex.canoe.domain.BoatClass;
import com.initex.canoe.dto.BoatClassDTO;
import com.initex.canoe.exception.CanoeException;
import com.initex.canoe.services.BoatClassService;
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
@WebMvcTest(BoatClassController.class)
class BoatClassControllerTest {

    private final String requestBody = "{\"boatClass\": \"" + SOME_BOAT_CLASS + "\"}";
    private final String errorResponseMessage = "{\"message\":\"%s\"}";

    private static final String URL_TEMPLATE = "/api/v1/boatClass";
    private static final String SOME_BOAT_CLASS = "someBoatClass";

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BoatClassService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getBoatClass() throws Exception {
        when(service.getBoatClass()).thenReturn(Collections.singletonList(createBoatClass()));
        mockMvc.perform(get(URL_TEMPLATE))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(Collections.singletonList(createBoatClass())))));
    }

    @Test
    void getBoatClass_ReturnErrorMessage() throws Exception {
        when(service.getBoatClass()).thenThrow(new CanoeException(DATABASE_READ_ERROR));
        mockMvc.perform(get(URL_TEMPLATE))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_READ_ERROR)));
    }

    @Test
    void addBoatClass() throws Exception {
        when(service.addBoatClass(any(BoatClassDTO.class))).thenReturn(createBoatClassDTO());
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(createBoatClassDTO()))));
    }

    @Test
    void addBoatClass_GetDuplicateError() throws Exception {
        when(service.addBoatClass(any(BoatClassDTO.class))).thenThrow(new CanoeException(DUPLICATE_DATA_EXISTS));
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format(errorResponseMessage, DUPLICATE_DATA_EXISTS)));
    }

    @Test
    void addBoatClass_SaveError() throws Exception {
        when(service.addBoatClass(any(BoatClassDTO.class))).thenThrow(new CanoeException(DATABASE_SAVE_ERROR));
        mockMvc.perform(post(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_SAVE_ERROR)));
    }

    @Test
    void updateBoatClass() throws Exception {
        when(service.updateBoatClass(anyString(), any(BoatClassDTO.class))).thenReturn(createBoatClassDTO());
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string((mapper.writeValueAsString(createBoatClassDTO()))));
    }

    @Test
    void updateBoatClass_UpdateError_NoSuchRecord() throws Exception {
        when(service.updateBoatClass(anyString(), any(BoatClassDTO.class))).thenThrow(new CanoeException(NO_SUCH_RECORD_ERROR));
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(String.format(errorResponseMessage, NO_SUCH_RECORD_ERROR)));
    }

    @Test
    void updateBoatClass_UpdateError() throws Exception {
        when(service.updateBoatClass(anyString(), any(BoatClassDTO.class))).thenThrow(new CanoeException(DATABASE_UPDATE_ERROR));
        mockMvc.perform(put(URL_TEMPLATE + "/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().is5xxServerError())
                .andExpect(content().string(String.format(errorResponseMessage, DATABASE_UPDATE_ERROR)));
    }

    private BoatClass createBoatClass() {
        BoatClass boatClass = new BoatClass();
        boatClass.setId(1L);
        boatClass.setBoatClass(SOME_BOAT_CLASS);
        return boatClass;
    }

    private BoatClassDTO createBoatClassDTO() {
        BoatClassDTO boatClassDTO = new BoatClassDTO();
        boatClassDTO.setId(1L);
        boatClassDTO.setBoatClass(SOME_BOAT_CLASS);
        return boatClassDTO;
    }
}

