package com.dvdprime.server.mobile.response;

import lombok.Data;

import org.codehaus.jackson.annotate.JsonProperty;

import com.dvdprime.server.mobile.constants.HttpStatus;

@Data
public class ErrorResponse
{
    
    @JsonProperty("status")
    private int error;
    
    @JsonProperty("message")
    private String errorMessage;
    
    public ErrorResponse()
    {
        super();
    }
    
    public ErrorResponse(String errorMessage)
    {
        this.error = HttpStatus.FORBIDDEN;
        this.errorMessage = errorMessage;
    }
    
    public ErrorResponse(int error, String errorMessage)
    {
        super();
        this.error = error;
        this.errorMessage = errorMessage;
    }
    
}
