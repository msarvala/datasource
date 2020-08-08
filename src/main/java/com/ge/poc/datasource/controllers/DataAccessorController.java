package com.ge.poc.datasource.controllers;


import com.ge.poc.datasource.services.DataAccessorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class DataAccessorController {

    @Autowired
    DataAccessorServiceImpl personService;

    @GetMapping
    public String welcome(){
        return "Welcome To Spring Boot + Docker DataSources Microservice@" + LocalDateTime.now();
    }

}
