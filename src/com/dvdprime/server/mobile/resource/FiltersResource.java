package com.dvdprime.server.mobile.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dvdprime.server.mobile.constants.ResponseMessage;
import com.dvdprime.server.mobile.model.Filter;
import com.dvdprime.server.mobile.response.ListResponse;

/**
 * 필터 목록 조회
 * 
 * @author 작은광명
 * 
 */
@Path("/filters")
@Produces(MediaType.APPLICATION_JSON)
public class FiltersResource
{
    /** Logger */
    private final Logger logger = Logger.getLogger(FiltersResource.class.getCanonicalName());
    
    /**
     * 등록한 필터 목록
     * 
     * @param id
     *            회원 아이디
     * @return
     */
    @GET
    public Response Get(@QueryParam("id")
    String id)
    {
        logger.log(Level.INFO, "Get Filter : {0}", id);
        
        try
        {
            return Response.ok(new ListResponse(Filter.getRetriveFilters(id))).build();
        }
        catch (Exception e)
        {
            return Response.ok(ResponseMessage.SERVER_ERROR).build();
        }
    }
    
}
