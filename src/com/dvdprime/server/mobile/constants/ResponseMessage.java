package com.dvdprime.server.mobile.constants;

/**
 * 응답 메시지 정의
 * 
 * @author 작은광명
 *
 */
public class ResponseMessage
{
    private static final String DEFAULT_MSG = "{\"status\":%s, \"message\":\"%s\"}";
    
    /* --------------------------------------------------------------------------------- */
    /* 공통 메시지 */
    /* --------------------------------------------------------------------------------- */
    /** 요청 성공 */
    public static final String SUCCESS = String.format(DEFAULT_MSG, HttpStatus.OK, "success");
    
    /** 존재하지 않는 요청 또는 잘못된 요청 */
    public static final String NOT_FOUND = String.format(DEFAULT_MSG, HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");
    
    /** 내부 장애 또는 오류 */
    public static final String SERVER_ERROR = String.format(DEFAULT_MSG, HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 장애가 발생했습니다. 잠시후에 다시 시도해주세요.");

}
