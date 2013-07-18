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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dvdprime.server.mobile.request.FilterRequest;
import com.dvdprime.server.mobile.util.Util;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/**
 * This class defines the methods for basic operations of create, update &
 * retrieve for customer entity
 * 
 * @author
 * 
 */
public class Filter
{
    
    /**
     * Checks if the entity is existing and if it is not, it creates the entity
     * else it updates the entity
     * 
     * @param param
     *            {@link FilterRequest}
     */
    public static void createOrUpdateFilter(FilterRequest param)
    {
        Entity filter = getSingleFilter(param.getId());
        if (filter == null)
        {
            filter = new Entity("Filter", param.getId());
            filter.setProperty("target", param.getTargetId() + ":" + param.getTargetNick());
        }
        else
        {
            Map<String, String> targetMap = Splitter.on(',').omitEmptyStrings().withKeyValueSeparator(":").split((String) filter.getProperty("target"));
            
            if (!targetMap.containsKey(param.getTargetId()))
            {
                targetMap.put(param.getTargetId(), param.getTargetNick());
            }
            
            filter.setProperty("target", Joiner.on(",").withKeyValueSeparator(":").join(targetMap));
        }
        Util.persistEntity(filter);
    }
    
    /**
     * Checks if the entity is existing and if it is not, it creates the entity
     * else it updates the entity
     * 
     * @param param
     *            {@link FilterRequest}
     */
    public static void updateOrDeleteFilter(String id, String targetId)
    {
        Entity filter = getSingleFilter(id);
        if (filter != null)
        {
            Map<String, String> targetMap = Splitter.on(',').omitEmptyStrings().withKeyValueSeparator(":").split((String) filter.getProperty("target"));
            targetMap.remove(targetId);
            
            if (targetMap.isEmpty())
            {
                Util.deleteFromCache(filter.getKey());
                Util.deleteEntity(filter.getKey());
            }
            else
            {
                filter.setProperty("target", Joiner.on(",").withKeyValueSeparator(":").join(targetMap));
                Util.persistEntity(filter);
            }
        }
    }
    
    /**
     * List all the customers available
     * 
     * @return an iterable list with all the customers
     */
    public static Iterable<Entity> getAllFilters()
    {
        Iterable<Entity> entities = Util.listEntities("Filter", null, null);
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
    public static Iterable<Entity> getFilter(String id)
    {
        Iterable<Entity> entities = Util.listEntities("Filter", "id", id);
        return entities;
    }
    
    /**
     * Retrieve Filters
     * 
     * @param id
     *            : member_id of the dvdprime
     * @return
     */
    public static List<com.dvdprime.server.mobile.domain.Filter> getRetriveFilters(String id)
    {
        List<com.dvdprime.server.mobile.domain.Filter> mResult = null;
        
        try
        {
            Entity result = getSingleFilter(id);
            
            if (result != null)
            {
                Map<String, String> targetMap = Splitter.on(',').omitEmptyStrings().withKeyValueSeparator(":").split((String) result.getProperty("target"));
                
                if (targetMap != null && !targetMap.isEmpty())
                {
                    mResult = Lists.newArrayList();
                    Iterator<String> keys = targetMap.keySet().iterator();
                    while (keys.hasNext())
                    {
                        String key = keys.next();
                        mResult.add(new com.dvdprime.server.mobile.domain.Filter(key, targetMap.get(key)));
                    }
                }
            }
        }
        catch (Exception e)
        {
        }
        
        return mResult;
    }
    
    /**
     * Searches for a device and returns the entity as an iterable The search is
     * key based instead of query
     * 
     * @param String
     *            id : member_id of the dvdprime
     * @return the entity with the id as key
     */
    public static Entity getSingleFilter(String id)
    {
        Key key = KeyFactory.createKey("Filter", id);
        return Util.findEntity(key);
    }
}
