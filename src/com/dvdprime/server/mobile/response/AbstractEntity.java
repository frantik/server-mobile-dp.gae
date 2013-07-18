package com.dvdprime.server.mobile.response;

import java.io.Serializable;

import lombok.Data;

import org.codehaus.jackson.annotate.JsonProperty;

import com.dvdprime.server.mobile.constants.HttpStatus;

/**
 * Abstract domain that defines common functionality.
 */
@Data
public abstract class AbstractEntity implements Serializable
{
    
    /**
     * generated serial version ID
     */
    private static final long serialVersionUID = -5608408180849622006L;
    
    /** 결과 코드 */
    @JsonProperty
    private int status = HttpStatus.OK;
    
    /** 결과 메시지 */
    @JsonProperty
    private String message = "success";
    
}
