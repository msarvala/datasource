package com.ge.poc.datasource.controllers;


import com.ge.poc.datasource.model.Census;
import com.ge.poc.datasource.model.MSSqlUser;
import com.ge.poc.datasource.model.MongoUser;
import com.ge.poc.datasource.services.DataAccessorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class DataAccessorController {

    @Autowired
    DataAccessorServiceImpl dataAccessorService;


    @GetMapping("/msssql/users")
    public List<MSSqlUser> getMsSqlUsers(){
        return dataAccessorService.getMsSqlUsers();
    }

    @PostMapping("/mssql/addUser")
    public boolean addMsSqlUser( @RequestBody MSSqlUser MSSqlUser) {
         return dataAccessorService.addUser( MSSqlUser );
    }


    @GetMapping("/mongo/users")
    public List<MongoUser> getMongoDBUsers(){
        return dataAccessorService.getMongoDbUsers();
    }

    @PostMapping("/mongo/addUser")
    public MongoUser addUserToMonoDb( @RequestBody MongoUser user){
        return dataAccessorService.addMongoDbUser(user);
    }


    @GetMapping("/falfile/read")
    public List<Census> readFlafile( ){
        return dataAccessorService.readFlatFile();
    }

}
