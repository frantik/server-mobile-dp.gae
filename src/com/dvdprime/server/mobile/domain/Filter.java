package com.dvdprime.server.mobile.domain;

import lombok.Data;

/**
 * 필터 정보
 * 
 * @author 작은광명
 * 
 */
@Data
public class Filter
{
    /**
     * 회원 아이디
     */
    private String id;
    
    /**
     * 회원 닉네임
     */
    private String nick;
    
    public Filter(String id, String nick)
    {
        this.id = id;
        this.nick = nick;
    }
}
