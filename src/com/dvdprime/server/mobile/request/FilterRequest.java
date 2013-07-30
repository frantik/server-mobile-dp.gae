package com.dvdprime.server.mobile.request;

import lombok.Data;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 필터 파라미터
 * 
 * @author 작은광명
 * 
 */
@Data
public class FilterRequest
{
    /** 요청 회원 아이디 */
    @JsonProperty("id")
    private String id;
    
    /** 타겟 회원 토큰 */
    @JsonProperty("targetId")
    private String targetId;
    
    /** 타겟 회원 닉네임 */
    @JsonProperty("targetNick")
    private String targetNick;
    
    // //////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // //////////////////////////////////////////////////////////////
    public FilterRequest()
    {
    }
    
    public FilterRequest(String id, String targetId, String targetNick)
    {
        this.id = id;
        this.targetId = targetId;
        this.targetNick = targetNick;
    }
}
