package com.initex.canoe.controller;

import com.initex.canoe.dto.CountryDTO;
import com.initex.canoe.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    private CountryService service;

    @Autowired
    public CountryController(CountryService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseBody
    public List<CountryDTO> getCountry() {
        return service.getCountryList();
    }

    @PostMapping
    @ResponseBody
    public CountryDTO addCountry(@RequestBody final CountryDTO countryDTO) {
        return service.addCountry(countryDTO);
    }

    @PutMapping(value = "/{id}")
    @ResponseBody
    public CountryDTO updateCountry(@PathVariable final String id, @RequestBody final CountryDTO countryDTO) {
        return service.updateCountry(id, countryDTO);
    }
}
