package com.initex.canoe.controller;

import com.initex.canoe.domain.BoatClass;
import com.initex.canoe.dto.BoatClassDTO;
import com.initex.canoe.services.BoatClassService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/boatClass")
public class BoatClassController {

    private BoatClassService service;

    @Autowired
    public BoatClassController(BoatClassService service) {
        this.service = service;
    }

    @ApiOperation(value = "get boat class", nickname = "getBoatclass")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = BoatClass.class, responseContainer = "List")
    })
    @GetMapping
    @ResponseBody
    public List<BoatClass> getBoatClass() {
        return service.getBoatClass();
    }


    @ApiOperation(value = "add boat class", nickname = "addBoatclass")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = BoatClassDTO.class)
    })
    @PostMapping
    @ResponseBody
    public BoatClassDTO addBoatClass(@RequestBody final BoatClassDTO boatClassDTO) {
        return service.addBoatClass(boatClassDTO);
    }

    @ApiOperation(value = "update boat class", nickname = "updateBoatclass")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = BoatClassDTO.class)
    })
    @PutMapping(value = "/{id}")
    @ResponseBody
    public BoatClassDTO updateBoatClass(@PathVariable final String id, @RequestBody final BoatClassDTO boatClassDTO) {
        return service.updateBoatClass(id, boatClassDTO);
    }
}
