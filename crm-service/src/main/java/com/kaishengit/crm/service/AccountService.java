package com.kaishengit.crm.service;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.service.exception.AuthenticationException;
import com.kaishengit.crm.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Created by 蔡林红 on 2017/11/6.
 */

public interface AccountService {

    //登录根据手机号验证
     Account longin(String mobile,String password) throws AuthenticationException;


    //添加新部门
    void saveNewDept(String deptName) throws ServiceException;

    //查询所有Dept部门
    List<Dept> findByAllDept();

    //根据查询 参数获得Account的分页对象
    List<Account> pageForAccount(Map<String, Object> queryParm);


    Long accountCountByDeptId(Integer deptId);

    //添加新客户
    void saveNewEmployee(String userName, String mobile, String password, Integer[] deptId) throws  ServiceException;

    //根据ID删除信息
    void deleteEmployeeById(Integer id) throws  ServiceException;

    //查询Account里所有的信息
    List<Account> findAllAccount();


}
