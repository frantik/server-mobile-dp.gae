package com.dvdprime.server.mobile.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.dvdprime.server.mobile.constants.ResponseMessage;
import com.dvdprime.server.mobile.model.Notification;
import com.dvdprime.server.mobile.request.NotificationRequest;

/**
 * 알림 정보 등록
 * 
 * @author 작은광명
 * 
 */
@Path("/notification")
@Produces(MediaType.APPLICATION_JSON)
public class NotificationResource
{
    /** Logger */
    private final Logger logger = Logger.getLogger(NotificationResource.class.getCanonicalName());
    
    /**
     * 알림 정보 등록
     * 
     * @param formParameters
     * @return
     */
    @POST
    public Response Post(final MultivaluedMap<String, String> formParameters)
    {
        NotificationRequest param = NotificationRequest.fromMultiValuedFormParameters(formParameters);
        logger.log(Level.INFO, "Create Notification params: {0}", param);
        
        try
        {
            if (Notification.createNotification(param))
            {
                return Response.ok(ResponseMessage.SUCCESS).build();
            }
            else
            {
                return Response.ok(ResponseMessage.FAIL).build();
            }
        }
        catch (Exception e)
        {
            return Response.ok(ResponseMessage.SERVER_ERROR).build();
        }
        
    }
    
}
