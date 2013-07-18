/*
 * Copyright ⓒ 2011 Tuck & Company Inc. All Rights Reserved
 */
package com.dvdprime.server.mobile.request;

import javax.ws.rs.core.MultivaluedMap;

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
    
    /**
     * @param formParameters
     * @return
     */
    public static FilterRequest fromMultiValuedFormParameters(MultivaluedMap<String, String> formParameters)
    {
        FilterRequest result = new FilterRequest();
        result.setId(nullSafeGetFormParameter("id", formParameters));
        result.setTargetId(nullSafeGetFormParameter("targetId", formParameters));
        result.setTargetNick(nullSafeGetFormParameter("targetNick", formParameters));
        return result;
    }
    
    private static String nullSafeGetFormParameter(String parameterName, MultivaluedMap<String, String> formParameters)
    {
        return formParameters.get(parameterName) == null ? null : formParameters.get(parameterName).get(0);
    }
}
