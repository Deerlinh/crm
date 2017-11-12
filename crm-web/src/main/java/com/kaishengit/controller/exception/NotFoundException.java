package com.kaishengit.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by 蔡林红 on 2017/11/10.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends  RuntimeException {

    public NotFoundException(){};
    public  NotFoundException(String message){
        super(message);
    }

}
