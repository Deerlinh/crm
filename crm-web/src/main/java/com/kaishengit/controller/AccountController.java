package com.kaishengit.controller;





import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.service.AccountService;

import com.kaishengit.crm.service.exception.ServiceException;
import com.kaishengit.web.result.AjaxResult;
import com.kaishengit.web.result.DataTablesResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 蔡林红 on 2017/11/6.
 */
@Controller
@RequestMapping("/employee")
public class AccountController {

    @Autowired
    private  AccountService accountService;

    @GetMapping
    public String list(){
        return "employee/list";
    }
    @PostMapping("/dept/new")
    @ResponseBody
    public AjaxResult saveNewDetp(String deptName){
    try{
        accountService.saveNewDept(deptName);
        return  AjaxResult.success();
    }catch (ServiceException ex){
        ex.printStackTrace();
        return AjaxResult.error(ex.getMessage());
    }
 }

 @GetMapping("/dept.json")
 @ResponseBody
 public List<Dept> findByAllDept(){
        return accountService.findByAllDept();
 }
@GetMapping("/load.json")
@ResponseBody
public DataTablesResult<Account> loadEmployeeList(Integer draw,
                                                  Integer start, Integer length,Integer deptId, HttpServletRequest request){
     String keyword=request.getParameter("search[value]");

    Map<String,Object> map=new HashMap<>();
    map.put("start",start);
    map.put("length",length);
    map.put("accountName",keyword);
    map.put("deptId",deptId);

    List<Account> accountList=accountService.pageForAccount(map);
    Long total= accountService.accountCountByDeptId(deptId);
    System.out.println(draw);
    System.out.println(total);
    return  new DataTablesResult<>(draw,total.intValue(),accountList);
}

@PostMapping("/new")
@ResponseBody
public AjaxResult saveNewEmployee(String userName,String mobile,String password,Integer[] deptId){

    try{
        accountService.saveNewEmployee(userName,mobile,password,deptId);
        return  AjaxResult.success();
    }catch (ServiceException ex){
            ex.printStackTrace();
            return AjaxResult.error(ex.getMessage());
    }
}

@PostMapping("/{id:\\d+}/delete")
@ResponseBody
public AjaxResult delete(@PathVariable Integer id){
    accountService.deleteEmployeeById(id);
    return  AjaxResult.success();
}
}



