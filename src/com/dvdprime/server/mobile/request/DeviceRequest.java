package com.dvdprime.server.mobile.request;

import java.util.Date;

import javax.ws.rs.core.MultivaluedMap;

import lombok.Data;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 디바이스 파라미터
 * 
 * @author 작은광명
 * 
 */
@Data
public class DeviceRequest
{
    /** 회원 아이디 */
    @JsonProperty("id")
    private String id;
    
    /** 디바이스 토큰 */
    @JsonProperty("token")
    private String deviceToken;
    
    /** 버전 */
    @JsonProperty("version")
    private String version;
    
    /** 날짜 */
    @JsonProperty("date")
    private Date date;
    
    // //////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // //////////////////////////////////////////////////////////////
    public DeviceRequest()
    {
    }
    
    /**
     * @param formParameters
     * @return
     */
    public static DeviceRequest fromMultiValuedFormParameters(MultivaluedMap<String, String> formParameters)
    {
        DeviceRequest result = new DeviceRequest();
        result.setId(nullSafeGetFormParameter("id", formParameters));
        result.setDeviceToken(nullSafeGetFormParameter("token", formParameters));
        result.setVersion(nullSafeGetFormParameter("version", formParameters));
        return result;
    }
    
    private static String nullSafeGetFormParameter(String parameterName, MultivaluedMap<String, String> formParameters)
    {
        return formParameters.get(parameterName) == null ? null : formParameters.get(parameterName).get(0);
    }
}
