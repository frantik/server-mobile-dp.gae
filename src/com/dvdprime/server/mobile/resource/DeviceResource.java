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
        DeviceRequest param = DeviceRequest.fromMultiValuedFormParameters(formParameters);
        logger.log(Level.INFO, "Create Device params: {0}", param);
        
        try
        {
            if (Device.createOrUpdateDevice(param))
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
