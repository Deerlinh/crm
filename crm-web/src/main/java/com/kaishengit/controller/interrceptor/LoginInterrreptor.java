package com.kaishengit.controller.interrceptor;

import com.kaishengit.crm.entity.Account;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by 蔡林红 on 2017/11/8.
 */
public class LoginInterrreptor extends HandlerInterceptorAdapter {

    @Override
    public  boolean preHandle(HttpServletRequest request,
                               HttpServletResponse response,
                               Object handler) throws  Exception{

        String requestURL=request.getRequestURI();
        System.out.println(requestURL);
        if(requestURL.startsWith("/static/")){
            return  true;
        }
        if("/login/new".equals(requestURL) || "/login".equals(requestURL)) {
            return true;
        }

        HttpSession httpSession=request.getSession();
        Account account=(Account) httpSession.getAttribute("curr_account");
        if (account!=null){
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }


}
