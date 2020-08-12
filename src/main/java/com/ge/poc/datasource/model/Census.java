package com.ge.poc.datasource.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Census {

    private String code;
    private String occupation;
    private long count;

    public Census( ) {

    }

    public Census( String code, String occupation, long count ) {
        this.code = code;
        this.occupation = occupation;
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public void setCode( String code ) {
        this.code = code;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation( String occupation ) {
        this.occupation = occupation;
    }

    public long getCount() {
        return count;
    }

    public void setCount( long count ) {
        this.count = count;
    }
}
