package com.kaishengit.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by 蔡林红 on 2017/11/10.
 */

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForbiddenException extends  RuntimeException {
    public  ForbiddenException(){}

    public  ForbiddenException(String message){
        super(message);
    }
}
