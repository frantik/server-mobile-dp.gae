package com.dvdprime.server.mobile.request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MultivaluedMap;

import lombok.Data;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 알림  파라미터
 * 
 * @author 작은광명
 * 
 */
@Data
public class NotificationRequest
{
    /** Logger */
    private static final Logger logger = Logger.getLogger(NotificationRequest.class.getCanonicalName());

    /** 회원 아이디 목록(콤마 구분) */
    @JsonProperty("ids")
    private String ids;
    
    /** 글 제목 */
    @JsonProperty("title")
    private String title;
    
    /** 메시지 */
    @JsonProperty("message")
    private String message;
    
    // //////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // //////////////////////////////////////////////////////////////
    public NotificationRequest()
    {
    }
    
    /**
     * @param formParameters
     * @return
     */
    public static NotificationRequest fromMultiValuedFormParameters(MultivaluedMap<String, String> formParameters)
    {
        NotificationRequest result = new NotificationRequest();
        result.setIds(nullSafeGetFormParameter("ids", formParameters));
        result.setTitle(nullSafeGetFormParameter("title", formParameters));
        result.setMessage(nullSafeGetFormParameter("message", formParameters));
        logger.log(Level.INFO, "params: {0}", result.toString());
        return result;
    }
    
    private static String nullSafeGetFormParameter(String parameterName, MultivaluedMap<String, String> formParameters)
    {
        try
        {
            return formParameters.get(parameterName) == null ? null : URLDecoder.decode(formParameters.get(parameterName).get(0), "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            return null;
        }
    }
}
