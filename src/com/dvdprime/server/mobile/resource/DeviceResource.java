package com.dvdprime.server.mobile.resource;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.dvdprime.server.mobile.constants.ResponseMessage;
import com.dvdprime.server.mobile.model.Device;
import com.dvdprime.server.mobile.request.DeviceRequest;
import com.dvdprime.server.mobile.response.ErrorResponse;
import com.dvdprime.server.mobile.util.StringUtil;
import com.dvdprime.server.mobile.util.Util;
import com.google.appengine.api.datastore.Entity;

/**
 * 디바이스 정보 등록 삭제
 * 
 * @author 작은광명
 * 
 */
@Path("/device")
@Produces(MediaType.APPLICATION_JSON)
public class DeviceResource
{
    /** Logger */
    private final Logger logger = Logger.getLogger(DeviceResource.class.getCanonicalName());
    
    /**
     * 디바이스 정보 등록
     * 
     * @param formParameters
     * @return
     */
    @POST
    public Response Post(final MultivaluedMap<String, String> formParameters)
    {
        logger.log(Level.INFO, "Create Device");
        
        Device.createOrUpdateDevice(DeviceRequest.fromMultiValuedFormParameters(formParameters));
        
        return Response.ok(ResponseMessage.SUCCESS).build();
    }
    
    /**
     * 디바이스 정보 삭제
     * 
     * @param id
     *            회원 아이디
     * @param token
     *            디바이스 토큰
     * @return
     */
    @DELETE
    public Response Delete(@FormParam("id")
    String id, @FormParam("token")
    String token)
    {
        logger.log(Level.INFO, "Deleting Device: {0}, {1}", new Object[] { id, token });
        Iterable<Entity> entities = Util.listEntities("Device", "id", id);
        try
        {
            if (entities != null)
            {
                Iterator<Entity> iter = entities.iterator();
                while (iter.hasNext())
                {
                    Entity entity = iter.next();
                    if (StringUtil.equals((String) entity.getProperty("token"), token))
                    {
                        Util.deleteFromCache(entity.getKey());
                        Util.deleteEntity(entity.getKey());
                    }
                }
            }
            
            return Response.ok(ResponseMessage.SUCCESS).build();
        }
        catch (Exception e)
        {
            try
            {
                return Response.ok(new ErrorResponse(Util.getErrorResponse(e))).build();
            }
            catch (IOException e1)
            {
                return Response.ok(ResponseMessage.SERVER_ERROR).build();
            }
        }
    }
}
