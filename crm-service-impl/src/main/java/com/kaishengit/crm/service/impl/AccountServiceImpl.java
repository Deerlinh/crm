package com.kaishengit.crm.service.impl;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.AccountDeptKey;
import com.kaishengit.crm.entity.Dept;
import com.kaishengit.crm.example.AccountDeptExample;
import com.kaishengit.crm.example.AccountExample;
import com.kaishengit.crm.example.DeptExample;
import com.kaishengit.crm.mapper.AccountDeptMapper;
import com.kaishengit.crm.mapper.AccountMapper;
import com.kaishengit.crm.mapper.DeptMapper;
import com.kaishengit.crm.service.AccountService;


import com.kaishengit.crm.service.exception.AuthenticationException;


import com.kaishengit.crm.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by 蔡林红 on 2017/11/6.
 */
@Service
public class AccountServiceImpl implements AccountService{

    private Logger logger= LoggerFactory.getLogger(AccountServiceImpl.class);
    //部门表中公司的Id
    private  static  final Integer COMPANY_ID=1;
    @Autowired
    private AccountMapper acountMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private AccountDeptMapper accountDeptMapper;

    @Override
    public Account longin(String mobile, String password) throws AuthenticationException {
        AccountExample accountExample=new AccountExample();
        accountExample.createCriteria().andMoblieEqualTo(mobile);

        List<Account > accounts= acountMapper.selectByExample(accountExample);
        Account account=null;
        if (accounts!= null && !accounts.isEmpty()){
            account=accounts.get(0);
        }
        if (account!=null && account.getPassword().equals(password)){
        logger.info("{} 在 {} 登录成功",account.getUserName(),new Date());
        return account;
        }else {
            throw  new AuthenticationException("账号或密码错误");
        }

    }



    @Override
    public void saveNewDept(String deptName) throws ServiceException {
        DeptExample deptExample= new DeptExample();
        deptExample.createCriteria().andDeptNameEqualTo(deptName);

        List<Dept> deptList=deptMapper.selectByExample(deptExample);
        Dept dept=null;
        if(deptList!= null && !deptList.isEmpty()){
            dept=deptList.get(0);
        }
        if (dept!= null){
            throw  new ServiceException("部门已存在");
        }
        dept= new Dept();
        dept.setDeptName(deptName);
        dept.setPidId(COMPANY_ID);

        deptMapper.insertSelective(dept);

        logger.info("添加新部门{}",deptName);
    }

    @Override
    public List<Dept> findByAllDept() {
        return deptMapper.selectByExample(new DeptExample());
    }

    @Override
    public List<Account> pageForAccount(Map<String, Object> queryParm) {
        Integer start= (Integer) queryParm.get("start");
        Integer length= (Integer) queryParm.get("length");
        Integer deptId= (Integer) queryParm.get("deptId");
        String accountName= (String) queryParm.get("accountName");

        if(deptId == null || COMPANY_ID.equals(deptId)){
            deptId=null;
        }
        List<Account> accountList=acountMapper.findByDeptId(accountName,deptId,start,length);
        return accountList;
    }

    @Override
    public Long accountCountByDeptId(Integer deptId) {
        if(deptId == null || COMPANY_ID.equals(deptId)){
        deptId=null;
        }
        return acountMapper.acountByDeptId(deptId);
    }

    @Override
    @Transactional
    public void saveNewEmployee(String userName, String mobile, String password, Integer[] deptIds) throws ServiceException {
        AccountExample accountExample= new AccountExample();
        accountExample.createCriteria().andMoblieEqualTo(mobile);

        List<Account> accountList=acountMapper.selectByExample(accountExample);
        if(accountList!= null && !accountList.isEmpty()){
                throw  new ServiceException("该手机号已被注册");
        }

        Account account= new Account();

        account.setUserName(userName);
        account.setPassword(password);
        account.setMobile(mobile);
        account.setCreateTime(new Date());
        account.setUpdateTime(new Date());

        acountMapper.insertSelective(account);

        for (Integer deptId:deptIds){
            AccountDeptKey accountDeptKey = new AccountDeptKey();
            accountDeptKey.setAccountId(account.getId());
            accountDeptKey.setDeptId(deptId);
            accountDeptMapper.insert(accountDeptKey);

        }
        logger.info("添加新账号{}",userName);
    }

    /**
     * 删除客户
     * @param id
     * @throws ServiceException
     */
    @Override
    public void deleteEmployeeById(Integer id) throws ServiceException {
        AccountDeptExample accountDeptExample=new AccountDeptExample();
        accountDeptExample.createCriteria().andAccountIdEqualTo(id);

        accountDeptMapper.deleteByExample(accountDeptExample);
        acountMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询所有Account
     * @return
     */
    @Override
    public List<Account> findAllAccount() {
        return acountMapper.selectByExample(new AccountExample());
    }


}
