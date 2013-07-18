package com.dvdprime.server.mobile.resource;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.dvdprime.server.mobile.constants.ResponseMessage;
import com.dvdprime.server.mobile.model.Filter;
import com.dvdprime.server.mobile.request.FilterRequest;
import com.dvdprime.server.mobile.response.ErrorResponse;
import com.dvdprime.server.mobile.response.ListResponse;
import com.dvdprime.server.mobile.util.Util;

/**
 * 필터 등록/삭제
 * 
 * @author 작은광명
 * 
 */
@Path("/filter")
@Produces(MediaType.APPLICATION_JSON)
public class FilterResource
{
    /** Logger */
    private final Logger logger = Logger.getLogger(FilterResource.class.getCanonicalName());
    
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
    
    /**
     * 필터 정보 등록
     * 
     * @param formParameters
     * @return
     */
    @POST
    public Response Post(final MultivaluedMap<String, String> formParameters)
    {
        logger.log(Level.INFO, "Create Filter");
        
        Filter.createOrUpdateFilter(FilterRequest.fromMultiValuedFormParameters(formParameters));
        
        return Response.ok(ResponseMessage.SUCCESS).build();
    }
    
    /**
     * 필터 정보 삭제
     * 
     * @param id
     *            회원 아이디
     * @return
     */
    @DELETE
    public Response Delete(@FormParam("id")
    String id, @FormParam("targetId")
    String targetId)
    {
        logger.log(Level.INFO, "Deleting Filter: {0}, {1}", new Object[] { id, targetId });
        try
        {
            Filter.updateOrDeleteFilter(id, targetId);
            
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
