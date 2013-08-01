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
package com.dvdprime.server.mobile.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 * List 반환 클래스
 * 
 * @author 작은광명
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({ "status", "message", "count", "list" })
public class ListResponse extends AbstractEntity
{
    /**
     * generated serial version ID
     */
    private static final long serialVersionUID = -3957871357348586238L;
    
    /** 결과 아이템 수 */
    private int count;
    
    /** 목록 */
    private List<?> list;
    
    // ////////////////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // ////////////////////////////////////////////////////////////////////////////////////
    public ListResponse()
    {
    }
    
    public ListResponse(List<?> list)
    {
        this.list = list;
        
        if (this.list != null && !this.list.isEmpty())
        {
            this.count = this.list.size();
        }
    }
    
}
