package com.dvdprime.server.mobile.response;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 * Result가 포함된 List형 반환 클래스
 * 
 * @author 작은광명
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement
@JsonPropertyOrder({ "status", "message", "result", "count", "list" })
public class ResultListResponse extends AbstractEntity
{
    /**
     * generated serial version ID
     */
    private static final long serialVersionUID = -7075009144580438878L;
    
    /** 요청 파라미터가 적용된 파라미터 */
    @JsonProperty("result")
    private Object result;
    
    /** 결과 아이템 수 */
    private int count;
    
    /** 아이템 목록 */
    private List<?> list;
    
    // //////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // //////////////////////////////////////////////////////////////////////////////////
    public ResultListResponse()
    {
        
    }
    
    public ResultListResponse(Object result, List<?> list)
    {
        this.result = result;
        this.list = list;
        if (this.list != null && !this.list.isEmpty())
        {
            this.count = this.list.size();
        }
    }
    
}
