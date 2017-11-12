package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Client;
import com.kaishengit.crm.example.ClientExample;
import com.kaishengit.crm.mapper.AccountMapper;
import com.kaishengit.crm.mapper.ClientMapper;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.exception.ServiceException;
import org.apache.commons.io.IOUtils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by 蔡林红 on 2017/11/9.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private Logger logger= LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private AccountMapper accountMapper;
    /**
     * Spring EL,获取字符串
     */
    @Value("#{'${cuctomer.trade}'.split(',')}")
    private  List<String> customerTrade;

    @Value("#{'${customer.source}'.split(',')}")
    private List<String> cusromerSource;

    @Override
    public void saveByClient(Client client) {
       client.setCreateTime(new Date());
       client.setUpdateTime(new Date());
       clientMapper.insertSelective(client);


        logger.info("添加新客户{}",client.getCustName());
}

    @Override
    public Client findById(Integer id) {
        return   clientMapper.selectByPrimaryKey(id);

    }



    @Override
    public PageInfo<Client> pageForMyCustomer(Account account, Integer pageNo) {
        ClientExample clientExample= new ClientExample();
        clientExample.createCriteria().andAccountIdEqualTo(account.getId());

        PageHelper.startPage(pageNo,5);
        List<Client> clientList=clientMapper.selectByExample(clientExample);
        return new PageInfo<>(clientList);
    }

    @Override
    public List<String> findAllCustomerTrode() {
        return customerTrade;
    }

    @Override
    public List<String> findAllCustomerSources() {
        return cusromerSource;
    }

    @Override
    public void editClient(Client client) {
        client.setUpdateTime(new Date());
        clientMapper.updateByPrimaryKeySelective(client);
    }

    /**
     * 删除客户
     * @param client
     */
    @Override
    public void deleteClient(Client client) {
        // TODO  检查是否有关联记录
        // TODO 检查是否有关联事项
        // TODO 检查是否有关联资料文件
        clientMapper.deleteByPrimaryKey(client.getId());
    }

    @Override
    public void publicClent(Client client) {
        Account account=accountMapper.selectByPrimaryKey(client.getAccountId());

        client.setAccountId(null);
        client.setReminder(client.getReminder()+" " + account.getUserName() +"将该客户放入公海");
        clientMapper.updateByPrimaryKey(client);
    }

    /**
     * 转交客户给其他人
     * @param client
     * @param toAccountId
     */
    @Override
    public void tranClient(Client client, Integer toAccountId) {
        Account account=accountMapper.selectByPrimaryKey(client.getAccountId());
        client.setAccountId(toAccountId);
        client.setReminder(client.getReminder()+" " + "从"+ account.getUserName() + "转交过来");
        clientMapper.updateByPrimaryKeySelective(client);

    }

    /**
     * 将文件导出为CSV 格式
     * @param outputStream
     * @param account
     * @throws IOException
     */
    @Override
    public void exportCsvFileToOutPutStream(OutputStream outputStream, Account account) throws IOException {
        //根据Account 查询所有的Client
       List<Client> clientList =findAllCustomerByAccount(account);

        StringBuilder sb=new StringBuilder();

        sb.append("姓名").append(",").append("联系电话").append(",")
                .append("职位").append(",").append("地址").append(",")
                .append("\r\n");
        for (Client client : clientList){
                sb.append(client.getCustName()).append(",")
                        .append(client.getMobile()).append(",")
                        .append(client.getJobTitle()).append(",")
                        .append(client.getAddress()).append("\r\n");

        }

        IOUtils.write(sb.toString(),outputStream,"GBK");
        outputStream.flush();
        outputStream.close();

    }

    /**
     * 导出文件为xl格式
     * @param outputStream
     * @param account
     * @throws IOException
     */
    @Override
    public void exportXLSFileToOutPutStream(OutputStream outputStream, Account account) throws IOException {
        List<Client> clientList = findAllCustomerByAccount(account);
        //创建工作表
        Workbook workbook= new HSSFWorkbook();
        //创建Sheet
        Sheet sheet=workbook.createSheet("我的客户");

        //创建行
        Row titleRow=sheet.createRow(0);
         //创建单元格
        Cell nameCell=titleRow.createCell(0);
        nameCell.setCellValue("姓名");
        titleRow.createCell(1).setCellValue("电话");
        titleRow.createCell(2).setCellValue("职位");
        titleRow.createCell(3).setCellValue("地址");

        for (int i=0;i<clientList.size();i++){
            Client client= clientList.get(i);

            Row dataRow=sheet.createRow(i+1);
            dataRow.createCell(0).setCellValue(client.getCustName());
            dataRow.createCell(1).setCellValue(client.getMobile());
            dataRow.createCell(2).setCellValue(client.getJobTitle());
            dataRow.createCell(3).setCellValue(client.getAddress());



        }
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();

    }

    private List<Client> findAllCustomerByAccount(Account account) {
        ClientExample clientExample= new ClientExample();
        clientExample.createCriteria().andAccountIdEqualTo(account.getId());
        List<Client> clientList=clientMapper.selectByExample(clientExample);
        return  clientList;
    }
}
