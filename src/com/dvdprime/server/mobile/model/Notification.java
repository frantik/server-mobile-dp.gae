/**
 * Copyright 2011 Google
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
package com.dvdprime.server.mobile.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;

import com.dvdprime.server.mobile.request.NotificationRequest;
import com.dvdprime.server.mobile.util.Util;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.common.base.Splitter;

/**
 * This class defines the methods for basic operations of create, update &
 * retrieve for customer entity
 * 
 * @author
 * 
 */
public class Notification
{
    
    /**
     * Checks if the entity is existing and if it is not, it creates the entity
     * else it updates the entity
     * 
     * @param param
     *            {@link NotificationRequest}
     */
    public static void createNotification(NotificationRequest param)
    {
        if (param.getIds() != null)
        {
            Iterable<String> ids = Splitter.on(',').omitEmptyStrings().split(param.getIds());
            
            if (ids != null)
            {
                long creationTime = new Date().getTime();
                Iterator<String> it = ids.iterator();
                while (it.hasNext())
                {
                    
                    try
                    {
                        Entity notification = new Entity("Notification");
                        notification.setProperty("id", it.next());
                        notification.setProperty("title", URLEncoder.encode(param.getTitle(), "utf-8"));
                        notification.setProperty("message", URLEncoder.encode(param.getMessage(), "utf-8"));
                        notification.setProperty("creation", creationTime);
                        
                        Util.persistEntity(notification);
                    }
                    catch (UnsupportedEncodingException e)
                    {
                    }
                }
            }
        }
        
    }
    
    /**
     * List all the customers available
     * 
     * @return an iterable list with all the customers
     */
    public static Iterable<Entity> getAllNotifications()
    {
        Iterable<Entity> entities = Util.listEntities("Notification");
        return entities;
    }
    
    /**
     * Searches for a customer and returns the entity as an iterable The search
     * is performed by creating a query and searching for the attribute
     * 
     * @param id
     *            : member_id of the dvdprime
     * @return iterable with the members searched for
     */
    public static Iterable<Entity> getNotification(String id)
    {
        Iterable<Entity> entities = Util.listEntities("Notification", "id", id, "creation");
        return entities;
    }
    
    /**
     * Searches for a device and returns the entity as an iterable The search is
     * key based instead of query
     * 
     * @param String
     *            id : member_id of the dvdprime
     * @return the entity with the id as key
     */
    public static Entity getSingleNotification(String id)
    {
        Key key = KeyFactory.createKey("Notification", id);
        return Util.findEntity(key);
    }
}
