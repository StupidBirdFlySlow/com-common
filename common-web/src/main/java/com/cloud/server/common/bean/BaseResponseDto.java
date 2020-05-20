package com.cloud.server.common.bean;

import lombok.Data;

import java.io.Serializable;
@Data
public class BaseResponseDto<T>  implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -600973566825725443L;

    private  T data;
    private  int status = 200;
    private String message = "Success";
    private String errorMsg="fail";
    public BaseResponseDto( int status, String message,String errorMsg) {
        this.status = status;
        this.message = message;
        this.errorMsg=errorMsg;
    }

    public BaseResponseDto() {
    }

    public static BaseResponseDto error(Object data){
        return new BaseResponseDto(500,data.toString(),"fail");

    }

    public BaseResponseDto(T data) {
        this.data = data;
    }

    public static BaseResponseDto ok(Object data){
        return new BaseResponseDto(data);


    }

}
