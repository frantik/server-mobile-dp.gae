package com.dvdprime.server.mobile;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Server_mobile_dpServlet extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        resp.setContentType("text/plain");
        resp.getWriter().println("Hello, world");
    }
}
