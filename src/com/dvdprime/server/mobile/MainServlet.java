package com.dvdprime.server.mobile;

import java.io.IOException;
import javax.servlet.http.*;

/**
 * 기본 페이지
 * 
 * @author 작은광명
 *
 */
@SuppressWarnings("serial")
public class MainServlet extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        resp.setContentType("text/plain");
        resp.getWriter().println("Hello, DvdPrime Mobile Server by frantik");
    }
}
