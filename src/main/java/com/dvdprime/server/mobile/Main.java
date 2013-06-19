package com.dvdprime.server.mobile;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 기본 테스트 화면
 * 
 * @author 작은광명
 * 
 */
@Path("/main")
@Produces(MediaType.APPLICATION_JSON)
public class Main
{
    @GET
    public String get()
    {
        return "Hello, DVD PRIME mobile";
    }
}
