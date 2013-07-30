package com.dvdprime.server.mobile.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dvdprime.server.mobile.constants.ResponseMessage;
import com.dvdprime.server.mobile.response.DataResponse;
import com.google.common.collect.ImmutableMap;

/**
 * 게시판 디비 버전 조회
 * 
 * @author 작은광명
 * 
 */
@Path("/database")
@Produces(MediaType.APPLICATION_JSON)
public class DatabaseResource
{
    @GET
    public Response Get()
    {
        try
        {
            return Response.ok(new DataResponse(ImmutableMap.<String, Integer> builder().put("version", 20130701).build())).build();
        }
        catch (Exception e)
        {
            return Response.ok(ResponseMessage.SERVER_ERROR).build();
        }
    }
}
