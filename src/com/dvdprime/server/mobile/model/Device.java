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
package com.dvdprime.server.mobile.model;

import java.util.Date;
import java.util.Iterator;

import com.dvdprime.server.mobile.request.DeviceRequest;
import com.dvdprime.server.mobile.util.StringUtil;
import com.dvdprime.server.mobile.util.Util;
import com.google.appengine.api.datastore.Entity;

/**
 * This class defines the methods for basic operations of create, update &
 * retrieve for customer entity
 * 
 * @author 작은광명
 * 
 */
public class Device
{
    
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
     *            {@link DeviceRequest}
     */
    public static boolean createOrUpdateDevice(DeviceRequest param)
    {
        Entity device = null;
        long time = new Date().getTime();
        Iterable<Entity> deviceList = getRetrieveDevice(param.getId());
        if (deviceList == null)
        {
            device = new Entity("Device");
            device.setProperty("id", param.getId());
            device.setProperty("token", param.getDeviceToken());
            device.setProperty("version", param.getVersion());
            device.setProperty("creationTime", time);
            device.setProperty("updatedTime", time);
        }
        else
        {
            Iterator<Entity> iter = deviceList.iterator();
            while (iter.hasNext())
            {
                Entity entity = iter.next();
                if (StringUtil.equals((String) entity.getProperty("token"), param.getDeviceToken()))
                {
                    device = entity;
                    if (param.getVersion() != null && !"".equals(param.getVersion()))
                    {
                        device.setProperty("version", param.getVersion());
                    }
                    device.setProperty("updatedTime", time);
                    break;
                }
            }
            if (device == null)
            {
                device = new Entity("Device");
                device.setProperty("id", param.getId());
                device.setProperty("token", param.getDeviceToken());
                device.setProperty("version", param.getVersion());
                device.setProperty("creationTime", time);
                device.setProperty("updatedTime", time);
            }
        }
        
        try
        {
            if (device != null)
            {
                Util.persistEntity(device);
            }
        }
        catch (Exception e)
        {
            return false;
        }
        
        return true;
    }
    
    /**
     * List all the customers available
     * 
     * @return an iterable list with all the customers
     */
    public static Iterable<Entity> getAllDevices()
    {
        Iterable<Entity> entities = Util.listEntities("Device");
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
    public static Iterable<Entity> getRetrieveDevice(String id)
    {
        Iterable<Entity> entities = Util.listEntities("Device", "id", id);
        return entities;
    }
    
}
