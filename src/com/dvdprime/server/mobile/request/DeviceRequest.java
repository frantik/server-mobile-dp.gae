/**
 * Copyright 2013 작은광명
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
