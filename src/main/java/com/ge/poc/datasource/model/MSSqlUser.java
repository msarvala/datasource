package com.ge.poc.datasource.model;


import io.swagger.annotations.ApiParam;

public class MSSqlUser {


    @ApiParam(hidden=true)
    private long id;

    public MSSqlUser( String firstName, String lastName ) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private String firstName;
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }
}
