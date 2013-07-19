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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import lombok.Data;

import com.dvdprime.server.mobile.request.NotificationRequest;
import com.dvdprime.server.mobile.util.Util;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/**
 * This class defines the methods for basic operations of create, update &
 * retrieve for customer entity
 * 
 * @author 작은광명
 * 
 */
@Data
public class Notification
{
    /** 글 제목 */
    private String title;
    
    /** 메시지 */
    private String message;
    
    /** 링크 URL */
    private String linkUrl;
    
    /** 해당 오브젝트 고유번호 */
    private String targetKey;
    
    /** 등록 시간 */
    private long creationTime;
    
    // //////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // //////////////////////////////////////////////////////////////
    public Notification()
    {
    }
    
    public Notification(Entity entity)
    {
        try
        {
            this.title = URLDecoder.decode((String) entity.getProperty("title"), "utf-8");
            this.message = URLDecoder.decode((String) entity.getProperty("message"), "utf-8");
            this.linkUrl = URLDecoder.decode((String) entity.getProperty("linkUrl"), "utf-8");
            this.targetKey = URLDecoder.decode((String) entity.getProperty("targetKey"), "utf-8");
            this.creationTime = (Long) entity.getProperty("creationTime");
        }
        catch (UnsupportedEncodingException e)
        {
        }
    }
    
    // //////////////////////////////////////////////////////////////
    //
    // Methods
    //
    // //////////////////////////////////////////////////////////////
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
                        notification.setProperty("linkUrl", URLEncoder.encode(param.getLinkUrl(), "utf-8"));
                        notification.setProperty("targetKey", URLEncoder.encode(param.getTargetKey(), "utf-8"));
                        notification.setProperty("creationTime", creationTime);
                        
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
     * @return list with the members searched for
     */
    public static List<Notification> retrieveNotification(NotificationRequest req)
    {
        List<Entity> entities = Util.listNotification(req);
        
        if (entities != null)
        {
            List<Notification> mResult = Lists.newArrayList();
            for (Entity entity : entities)
            {
                mResult.add(new Notification(entity));
            }
            
            return mResult;
        }
        else
        {
            return null;
        }
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
