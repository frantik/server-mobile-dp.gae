package com.dvdprime.server.mobile.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 * List 반환 클래스
 * 
 * @author 작은광명
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({ "status", "message", "count", "list" })
public class ListResponse extends AbstractEntity
{
    /**
     * generated serial version ID
     */
    private static final long serialVersionUID = -3957871357348586238L;
    
    /** 결과 아이템 수 */
    private int count;
    
    /** 목록 */
    private List<?> list;
    
    // ////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////
    public ListResponse()
    {
    }
    
    public ListResponse(List<?> list)
    {
        this.list = list;
        
        if (this.list != null && !this.list.isEmpty())
        {
            this.count = this.list.size();
        }
    }
    
}
