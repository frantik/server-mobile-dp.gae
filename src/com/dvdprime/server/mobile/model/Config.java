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
import java.util.List;

import lombok.Data;

import org.jsoup.helper.StringUtil;

import com.dvdprime.server.mobile.constants.ParamCode;
import com.dvdprime.server.mobile.util.Util;
import com.google.appengine.api.datastore.Entity;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

/**
 * This class defines the methods for basic operations of create, update &
 * retrieve for customer entity
 * 
 * @author 작은광명
 * 
 */
@Data
public class Config
{
    /** 푸시 종류 */
    private String type;
    
    /** 수신 여부 */
    private boolean enabled;
    
    // //////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // //////////////////////////////////////////////////////////////
    public Config()
    {
    }
    
    public Config(Entity entity)
    {
        this.type = (String) entity.getProperty("type");
        this.enabled = (Boolean) entity.getProperty("enabled");
    }
    
    public Config(String type, boolean enabled)
    {
        this.type = type;
        this.enabled = enabled;
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
     * @param id
     *            회원 아이디
     * @param type
     *            설정 종류
     * @param enabled
     *            설정 여부
     * @return
     */
    public static boolean createOrUpdate(String id, String type, boolean enabled)
    {
        if (StringUtil.isBlank(id) || !ParamCode.CONFIG_TYPE_LIST.contains(type)) { return false; }
        
        long time = new Date().getTime();
        Entity config = getConfigOne(id, type);
        if (config == null)
        {
            config = new Entity("Config");
            config.setProperty("id", id);
            config.setProperty("type", type);
            config.setProperty("enabled", enabled);
            config.setProperty("creationTime", time);
            config.setProperty("updatedTime", time);
        }
        else
        {
            config.setProperty("enabled", enabled);
            config.setProperty("updatedTime", time);
        }
        
        try
        {
            Util.persistEntity(config);
        }
        catch (Exception e)
        {
            return false;
        }
        
        return true;
    }
    
    /**
     * Searches for configs of member
     * 
     * @param id
     *            : member_id of the dvdprime
     */
    public static List<Config> retrieveConfig(String id)
    {
        List<Config> mResult = Lists.newArrayList();
        List<Entity> resultList = Util.listConfig(id);
        if (resultList != null && !resultList.isEmpty())
        {
            for (Entity e : resultList) {
                mResult.add(new Config(e));
            }
        } else {
            mResult.add(new Config(ParamCode.CONFIG_TYPE_PUSH_CMT, true));
        }
        
        return mResult;
    }
    
    /**
     * Search for one config entity
     * 
     * @param id
     *            member id
     * @param type
     *            config type
     * @return
     */
    public static Entity getConfigOne(String id, String type)
    {
        return Util.findEntity("Config", ImmutableMap.<String, Object> builder().put("id", id).put("type", type).build());
    }
}
