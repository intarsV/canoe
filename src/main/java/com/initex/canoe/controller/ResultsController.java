package com.initex.canoe.controller;

import com.initex.canoe.dto.MyResponse;
import com.initex.canoe.dto.ResultQuery;
import com.initex.canoe.services.ResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/results")
public class ResultsController {

    private ResultsService service;

    @Autowired
    public ResultsController(ResultsService service) {
        this.service = service;
    }

    @GetMapping
    public MyResponse getList(final ResultQuery query) {
        return new MyResponse(service.getReport(query).toByteArray());
    }
}
