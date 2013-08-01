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
package com.dvdprime.server.mobile.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dvdprime.server.mobile.constants.ResponseMessage;
import com.dvdprime.server.mobile.response.DataResponse;
import com.google.common.collect.ImmutableMap;

/**
 * 게시판 디비 버전 조회
 * 
 * @author 작은광명
 * 
 */
@Path("/database")
@Produces(MediaType.APPLICATION_JSON)
public class DatabaseResource
{
    @GET
    public Response Get()
    {
        try
        {
            DataResponse res = new DataResponse(ImmutableMap.<String, Integer> builder().put("version", 20130701).build());
            res.setMessage("쿠르베게시판이 추가된 새로운 DB가 있습니다. (지금 업데이트 받으실려면 클릭)");
            return Response.ok(res).build();
        }
        catch (Exception e)
        {
            return Response.ok(ResponseMessage.SERVER_ERROR).build();
        }
    }
}
