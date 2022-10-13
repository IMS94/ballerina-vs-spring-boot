package com.example.demo;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/country")
public class CountryController {

    private RestTemplate restTemplate;

    public CountryController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @GetMapping("/{callingCode}")
    public String getCountryByCallingCode(@PathVariable int callingCode) {
        String url = "https://restcountries.com/v2/callingcode/{callingCode}";
        Country[] countries = this.restTemplate.getForObject(url, Country[].class, callingCode);
        if (countries == null || countries.length == 0) {
            return "Invalid Calling Code";
        }
        return countries[0].getName();
    }
}
