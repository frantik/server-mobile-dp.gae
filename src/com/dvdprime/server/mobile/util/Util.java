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
package com.dvdprime.server.mobile.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dvdprime.server.mobile.request.NotificationRequest;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

/**
 * This is the utility class for all servlets. It provides method for inserting, deleting, searching the entity from data store. Also contains method for displaying the entity in JSON format.
 * 
 */
public class Util
{
    
    private static final Logger logger = Logger.getLogger(Util.class.getCanonicalName());
    
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    private static MemcacheService keycache = MemcacheServiceFactory.getMemcacheService();
    
    /**
     * Add the entity to cache and also to the datastore
     * 
     * @param entity
     *            : entity to be persisted
     */
    public static void persistEntity(Entity entity)
    {
        logger.log(Level.INFO, "Saving entity");
        Key key = entity.getKey();
        Transaction txn = datastore.beginTransaction();
        try
        {
            datastore.put(entity);
            txn.commit();
        }
        finally
        {
            if (txn.isActive())
            {
                txn.rollback();
            }
            else
            {
                addToCache(key, entity);
            }
        }
    }
    
    /**
     * Delete the entity from persistent store represented by the key Also delete it from cache
     * 
     * @param key
     *            : key to delete the entity from the persistent store
     */
    public static void deleteEntity(Key key)
    {
        logger.log(Level.INFO, "Deleting entity");
        Transaction txn = datastore.beginTransaction();
        try
        {
            datastore.delete(key);
            txn.commit();
        }
        finally
        {
            if (txn.isActive())
            {
                txn.rollback();
            }
            else
            {
                deleteFromCache(key);
            }
        }
    }
    
    /**
     * Delete the entities represented by a list of keys Delete the entitites from cache also
     * 
     * @param keys
     *            : keys for the entities that are to be deleted
     */
    public static void deleteEntity(final List<Key> keys)
    {
        datastore.delete(new Iterable<Key>() {
            public Iterator<Key> iterator()
            {
                return keys.iterator();
            }
        });
        deleteFromCache(keys);
    }
    
    /**
     * Search and return the entity from the cache . If absent , search the datastore.
     * 
     * @param key
     *            : key to find the entity
     * @return entity
     */
    public static Entity findEntity(Key key)
    {
        logger.log(Level.INFO, "Search the entity");
        try
        {
            Entity entity = getFromCache(key);
            if (entity != null) { return entity; }
            return datastore.get(key);
        }
        catch (EntityNotFoundException e)
        {
            return null;
        }
    }
    
    /**
     * Search and return the entity from the datastore
     * 
     * @param kind
     *            : key to find the entity
     * @param search
     * @return
     */
    public static Entity findEntity(String kind, Map<String, Object> search)
    {
        logger.log(Level.INFO, "Search entity based on search criteria");
        Query query = new Query(kind);
        if (search != null && !search.isEmpty())
        {
            Iterator<String> iter = search.keySet().iterator();
            while (iter.hasNext())
            {
                String key = iter.next();
                query.setFilter(new FilterPredicate(key, FilterOperator.EQUAL, search.get(key)));
            }
            PreparedQuery pq = datastore.prepare(query);
            return pq.asSingleEntity();
        }
        else
        {
            return null;
        }
    }
    
    public static Iterable<Entity> listEntities(String kind)
    {
        return listEntities(kind, null, "");
    }
    
    /***
     * Search entities based on search criteria
     * 
     * @param kind
     * @param searchBy
     *            : Searching Criteria (Property)
     * @param searchFor
     *            : Searching Value (Property Value)
     * @return List all entities of a kind from the cache or datastore (if not in cache) with the specified properties
     */
    public static List<Entity> listEntities(String kind, String searchBy, String searchFor)
    {
        logger.log(Level.INFO, "Search entities based on search criteria");
        Query query = new Query(kind);
        if (searchFor != null && !"".equals(searchFor))
        {
            query.setFilter(new FilterPredicate(searchBy, FilterOperator.EQUAL, searchFor));
        }
        return datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
    }
    
    /***
     * Search entities based on search criteria
     * 
     * @param kind
     * @param searchBy
     *            : Searching Criteria (Property)
     * @param searchFor
     *            : Searching Value List (Property Value)
     * @return List all entities of a kind from the cache or datastore (if not in cache) with the specified properties
     */
    public static Iterable<Entity> listEntities(String kind, String searchBy, List<String> searchFor)
    {
        logger.log(Level.INFO, "Search entities based on search criteria");
        Query query = new Query(kind);
        if (searchFor != null && !"".equals(searchFor))
        {
            query.setFilter(new FilterPredicate(searchBy, FilterOperator.IN, searchFor));
        }
        PreparedQuery pq = datastore.prepare(query);
        return pq.asIterable();
    }
    
    /***
     * Search entities based on search criteria
     * 
     * @param kind
     * @param searchBy
     *            : Searching Criteria (Property)
     * @param searchFor
     *            : Searching Value (Property Value)
     * @param sort
     *            : Sort Value
     * @return List all entities of a kind from the cache or datastore (if not in cache) with the specified properties
     */
    public static Iterable<Entity> listEntities(String kind, String searchBy, String searchFor, String sort)
    {
        logger.log(Level.INFO, "Search entities based on search criteria");
        Query query = new Query(kind);
        if (searchFor != null && !"".equals(searchFor))
        {
            query.setFilter(new FilterPredicate(searchBy, FilterOperator.EQUAL, searchFor));
        }
        if (sort != null)
        {
            query.addSort(sort, SortDirection.DESCENDING);
        }
        PreparedQuery pq = datastore.prepare(query);
        return pq.asIterable();
    }
    
    /**
     * Search Config List
     * 
     * @param id
     *            Member ID
     * @return
     */
    public static List<Entity> listConfig(String id)
    {
        logger.log(Level.INFO, "Search config entities based on member id");
        Query query = new Query("Config");
        query.setFilter(new FilterPredicate("id", FilterOperator.EQUAL, id));
        
        FetchOptions option = FetchOptions.Builder.withDefaults();
        
        return datastore.prepare(query).asList(option);
    }
    
    /**
     * Search Not Sent Notification List
     * 
     * @param request
     *            {@link NotificationRequest}
     * @return
     */
    public static List<Entity> listNotification()
    {
        logger.log(Level.INFO, "Search not sent notifications entities");
        Query query = new Query("Notification");
        FilterPredicate statusFilter = new FilterPredicate("status", FilterOperator.EQUAL, 10);
        query.setFilter(CompositeFilterOperator.and(statusFilter));
        query.addSort("creationTime", SortDirection.DESCENDING);
        
        return datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
    }
    
    /**
     * Search Notification List
     * 
     * @param request
     *            {@link NotificationRequest}
     * @return
     */
    public static List<Entity> listNotification(NotificationRequest request)
    {
        logger.log(Level.INFO, "Search notifications entities based on NotificationRequest.class");
        Query query = new Query("Notification");
        FilterPredicate idFilter = new FilterPredicate("id", FilterOperator.EQUAL, request.getIds());
        FilterPredicate timeFilter = new FilterPredicate("creationTime", FilterOperator.LESS_THAN_OR_EQUAL, request.getStartTime());
        query.setFilter(CompositeFilterOperator.and(idFilter, timeFilter));
        query.addSort("creationTime", SortDirection.DESCENDING);
        
        FetchOptions option = FetchOptions.Builder.withDefaults().offset((request.getPage() - 1) * request.getLimit()).limit(request.getLimit());
        
        return datastore.prepare(query).asList(option);
    }
    
    /**
     * Get the list of children from a parent key in the entity group
     * 
     * @param kind
     *            : the entity kind of the children that is to be searched for
     * @param ancestor
     *            : the parent key of the entity group where we need to search
     * @return iterable with all children of the parent of the specified kind
     */
    public static Iterable<Entity> listChildren(String kind, Key ancestor)
    {
        logger.log(Level.INFO, "Search entities based on parent");
        Query query = new Query(kind);
        query.setAncestor(ancestor);
        query.setFilter(new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor));
        PreparedQuery pq = datastore.prepare(query);
        return pq.asIterable();
    }
    
    /**
     * Get the list of keys of all children for a given entity kind in a given entity group represented by the parent key
     * 
     * @param kind
     *            : Entity kind of the children that needs to be searched for
     * @param ancestor
     *            : Parent key of the entity group that needs to be searched for
     * @return an iterable with keys of children
     */
    public static Iterable<Entity> listChildKeys(String kind, Key ancestor)
    {
        logger.log(Level.INFO, "Search entities based on parent");
        Query query = new Query(kind);
        query.setAncestor(ancestor).setKeysOnly();
        query.setFilter(new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.GREATER_THAN, ancestor));
        PreparedQuery pq = datastore.prepare(query);
        return pq.asIterable();
    }
    
    /**
     * List the entities in JSON format
     * 
     * @param entities
     *            entities to return as JSON strings
     */
    public static String writeJSON(Iterable<Entity> entities)
    {
        logger.log(Level.INFO, "creating JSON format object");
        StringBuilder sb = new StringBuilder();
        int i = 0;
        sb.append("{\"data\": [");
        for (Entity result : entities)
        {
            Map<String, Object> properties = result.getProperties();
            sb.append("{");
            if (result.getKey().getName() == null)
                sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
            else
                sb.append("\"name\" : \"" + result.getKey().getName() + "\",");
            
            for (String key : properties.keySet())
            {
                sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("},");
            i++;
        }
        if (i > 0)
        {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        sb.append("]}");
        return sb.toString();
    }
    
    /**
     * Retrieves Parent and Child entities into JSON String
     * 
     * @param entities
     *            : List of parent entities
     * @param childKind
     *            : Entity type for Child
     * @param fkName
     *            : foreign-key to the parent in the child entity
     * @return JSON string
     */
    public static String writeJSON(Iterable<Entity> entities, String childKind, String fkName)
    {
        logger.log(Level.INFO, "creating JSON format object for parent child relation");
        StringBuilder sb = new StringBuilder();
        int i = 0;
        sb.append("{\"data\": [");
        for (Entity result : entities)
        {
            Map<String, Object> properties = result.getProperties();
            sb.append("{");
            if (result.getKey().getName() == null)
                sb.append("\"name\" : \"" + result.getKey().getId() + "\",");
            else
                sb.append("\"name\" : \"" + result.getKey().getName() + "\",");
            for (String key : properties.keySet())
            {
                sb.append("\"" + key + "\" : \"" + properties.get(key) + "\",");
            }
            Iterable<Entity> child = listEntities(childKind, fkName, String.valueOf(result.getKey().getId()));
            for (Entity en : child)
            {
                for (String key : en.getProperties().keySet())
                {
                    sb.append("\"" + key + "\" : \"" + en.getProperties().get(key) + "\",");
                }
            }
            sb.deleteCharAt(sb.lastIndexOf(","));
            sb.append("},");
            i++;
        }
        if (i > 0)
        {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        sb.append("]}");
        return sb.toString();
    }
    
    /**
     * Adds the entity to cache
     * 
     * @param key
     *            : key of the entity
     * @param entity
     *            : Entity being added
     */
    public static void addToCache(Key key, Entity entity)
    {
        logger.log(Level.INFO, "Adding entity to cache");
        keycache.put(key, entity);
    }
    
    /**
     * Delete the entity from cache
     * 
     * @param key
     *            : Key of the entity that needs to be deleted
     */
    public static void deleteFromCache(Key key)
    {
        logger.log(Level.INFO, "Deleting entity from cache");
        keycache.delete(key);
    }
    
    /**
     * Delete entities based on a set of keys
     * 
     * @param keys
     *            : list of keys for the entities that are to be deleted
     */
    public static void deleteFromCache(List<Key> keys)
    {
        keycache.deleteAll(keys);
    }
    
    /**
     * Search for an entity based on key in the cache
     * 
     * @param key
     *            : key of the entity that is searched for
     * @return the entity
     */
    public static Entity getFromCache(Key key)
    {
        logger.log(Level.INFO, "Searching entity in cache");
        return (Entity) keycache.get(key);
    }
    
    /**
     * Utility method to send the error back to UI
     * 
     * @param data
     * @param resp
     * @throws IOException
     */
    public static String getErrorResponse(Exception ex) throws IOException
    {
        return "Error:" + ex.toString();
    }
    
    /**
     * Utility method to get the datastore service in entities
     * 
     * @return datastore
     */
    public static DatastoreService getDatastoreServiceInstance()
    {
        return datastore;
    }
}