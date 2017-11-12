package com.kaishengit.crm.service.exception;

/**
 * Created by 蔡林红 on 2017/11/8.
 */
public class ServiceException extends  RuntimeException{

    public ServiceException(){}

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable th) {
        super(th);
    }

    public ServiceException(Throwable th,String message) {
        super(message,th);
    }

}
