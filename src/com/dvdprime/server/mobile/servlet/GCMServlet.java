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
package com.dvdprime.server.mobile.servlet;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dvdprime.server.mobile.model.Notification;
import com.google.common.base.Stopwatch;

/**
 * GCM 메시지 발송
 * 
 * @author 작은광명
 * @version 1.0
 * @created 2013. 9. 10. 오전 6:04:43
 * @history
 */
public class GCMServlet extends HttpServlet
{
    /**
     * generated serial version ID
     */
    private static final long serialVersionUID = -5350577103762795894L;

    /** Logger */
    private final Logger logger = Logger.getLogger(GCMServlet.class.getCanonicalName());
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
    {
        Stopwatch sw = new Stopwatch().start();
        logger.log(Level.INFO, "Start GCM: {0}ms", sw.elapsed(TimeUnit.MILLISECONDS));
        
        Notification.getNotificationList();

        logger.log(Level.INFO, "End GCM: {0}ms", sw.elapsed(TimeUnit.MILLISECONDS));
    }
}
