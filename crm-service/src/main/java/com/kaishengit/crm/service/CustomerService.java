package com.kaishengit.crm.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Client;
import com.kaishengit.crm.service.exception.ServiceException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by 蔡林红 on 2017/11/9.
 */
public interface CustomerService {


    void saveByClient(Client client);



    Client findById(Integer id);



    PageInfo<Client> pageForMyCustomer(Account account, Integer pageNo);

    List<String> findAllCustomerTrode();

    List<String> findAllCustomerSources();

    void editClient(Client client);

    /**
     * 删除客户
     * @param client
     */
    void deleteClient(Client client);

    void publicClent(Client client);

    void tranClient(Client client, Integer toAccountId);

    void exportCsvFileToOutPutStream(OutputStream outputStream, Account account) throws IOException;

    void exportXLSFileToOutPutStream(OutputStream outputStream, Account account)throws  IOException;


   /* void saveByClient(String userName, String jobTitle, String mobile, String address, String trade, String source, String level, String remork);*/


}
