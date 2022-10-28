package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/country")
public class CountryController {

    private RestTemplate restTemplate;

    public CountryController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @GetMapping("/{callingCode}")
    public List<String> getCountryByCallingCode(@PathVariable int callingCode) {
        String url = "https://restcountries.com/v2/callingcode/{callingCode}";
        Country[] countries = this.restTemplate.getForObject(url, Country[].class, callingCode);
        if (countries == null || countries.length == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid calling code");
        }

        return Arrays.stream(countries)
                .map(country -> country.getName())
                .collect(Collectors.toList());
    }
}
