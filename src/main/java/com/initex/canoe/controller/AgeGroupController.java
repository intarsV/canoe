package com.initex.canoe.controller;

import com.initex.canoe.domain.AgeGroup;
import com.initex.canoe.dto.AgeGroupDTO;
import com.initex.canoe.services.AgeGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/groups")
public class AgeGroupController {

    private AgeGroupService service;

    @Autowired
    public AgeGroupController(AgeGroupService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    public List<AgeGroup> getGroups() {
        return service.getGroups();
    }

    @PostMapping
    @ResponseBody
    public AgeGroupDTO addGroup(@RequestBody final AgeGroupDTO ageGroupDTO) {
        return service.addGroup(ageGroupDTO);
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public AgeGroupDTO updateGroup(@PathVariable final String id, @RequestBody final AgeGroupDTO ageGroupDTO) {
        return service.updateGroup(id, ageGroupDTO);
    }

}
