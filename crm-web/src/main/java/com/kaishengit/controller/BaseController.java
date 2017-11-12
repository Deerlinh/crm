package com.kaishengit.controller;

import com.kaishengit.crm.entity.Account;

import javax.servlet.http.HttpSession;

/**
 * Created by 蔡林红 on 2017/11/10.
 */
public abstract class BaseController {

    public Account getCurrentAccount (HttpSession session){
        return (Account) session.getAttribute("curr_account");
    }
}
