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

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dvdprime.server.mobile.constants.ResponseMessage;
import com.dvdprime.server.mobile.model.Notification;
import com.dvdprime.server.mobile.request.NotificationRequest;
import com.dvdprime.server.mobile.response.ResultListResponse;

/**
 * 알림 정보 조회
 * 
 * @author 작은광명
 * 
 */
@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
public class NotificationsResource
{
    /** Logger */
    private final Logger logger = Logger.getLogger(NotificationsResource.class.getCanonicalName());
    
    @GET
    public Response Get(@DefaultValue("1")
    @QueryParam("page")
    int page, @DefaultValue("20")
    @QueryParam("limit")
    int limit, @QueryParam("startTime")
    long startTime, @QueryParam("id")
    String id)
    {
        try
        {
            NotificationRequest param = new NotificationRequest(id, page, limit, startTime);
            logger.log(Level.INFO, "Retrieve Notification params: {0}", param);
            List<Notification> list = null;
            if (id != null)
            {
                list = Notification.retrieveNotification(param);
            }
            return Response.ok(new ResultListResponse(param, list)).build();
        }
        catch (Exception e)
        {
            return Response.ok(ResponseMessage.SERVER_ERROR).build();
        }
    }
}
