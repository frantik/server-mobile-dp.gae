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

import com.dvdprime.server.mobile.request.DeviceRequest;
import com.dvdprime.server.mobile.util.Util;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * This class defines the methods for basic operations of create, update &
 * retrieve for customer entity
 * 
 * @author
 * 
 */
public class Device
{
    
    /**
     * Checks if the entity is existing and if it is not, it creates the entity
     * else it updates the entity
     * 
     * @param param
     *            {@link DeviceRequest}
     */
    public static void createOrUpdateDevice(DeviceRequest param)
    {
        Entity device = getSingleDevice(param.getId());
        if (device == null)
        {
            device = new Entity("Device", param.getId());
            device.setProperty("token", param.getDeviceToken());
            device.setProperty("version", param.getVersion());
            device.setProperty("date", param.getDate());
        }
        else
        {
            if (param.getDeviceToken() != null && !"".equals(param.getDeviceToken()))
            {
                device.setProperty("token", param.getDeviceToken());
            }
            if (param.getVersion() != null && !"".equals(param.getVersion()))
            {
                device.setProperty("version", param.getVersion());
            }
            if (param.getDate() != null)
            {
                device.setProperty("date", param.getDate());
            }
        }
        Util.persistEntity(device);
    }
    
    /**
     * List all the customers available
     * 
     * @return an iterable list with all the customers
     */
    public static Iterable<Entity> getAllDevices()
    {
        Iterable<Entity> entities = Util.listEntities("Device", null, null);
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
    public static Iterable<Entity> getDevice(String id)
    {
        Iterable<Entity> entities = Util.listEntities("Device", "id", id);
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
    public static Entity getSingleDevice(String id)
    {
        Key key = KeyFactory.createKey("Device", id);
        return Util.findEntity(key);
    }
}
