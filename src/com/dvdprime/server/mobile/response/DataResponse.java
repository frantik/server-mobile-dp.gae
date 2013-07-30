package com.dvdprime.server.mobile.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 * Data 반환 클래스
 * 
 * @author 작은광명
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({ "status", "message", "data" })
public class DataResponse extends AbstractEntity
{
    /**
     * generated serial version ID
     */
    private static final long serialVersionUID = 2460697475380113544L;
    
    /** 목록 */
    private Object data;
    
    // ////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////
    public DataResponse()
    {
    }
    
    public DataResponse(Object data)
    {
        this.data = data;
    }
    
}
