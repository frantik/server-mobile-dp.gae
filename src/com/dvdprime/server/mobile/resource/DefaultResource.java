package com.dvdprime.server.mobile.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("default")
@Produces(MediaType.APPLICATION_JSON)
public class DefaultResource
{
    @GET
    public String Get()
    {
        return "Hello, DvdPrime Mobile Server by frantik";
    }
}
