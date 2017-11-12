package com.kaishengit.controller;

import com.github.pagehelper.PageInfo;
import com.kaishengit.controller.exception.ForbiddenException;
import com.kaishengit.controller.exception.NotFoundException;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.Client;
import com.kaishengit.crm.mapper.ClientMapper;
import com.kaishengit.crm.service.AccountService;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.crm.service.exception.ServiceException;
import com.kaishengit.web.result.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 蔡林红 on 2017/11/9.
 */

@Controller

@RequestMapping("/customer")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;


    @GetMapping("/my")
    private String my(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                      Model model,
                      HttpSession session) {
        Account account = getCurrentAccount(session);

        PageInfo<Client> pageInfo = customerService.pageForMyCustomer(account, pageNo);

        model.addAttribute("page", pageInfo);
        return "customer/my";
    }

    /*
        String custName, String jobTitle, String mobile,
        String address, String trade, String source,
        String level, String remork*/
    @PostMapping("/save")
    public String save(Client client, RedirectAttributes redirectAttributes) {
        customerService.saveByClient(client);
        redirectAttributes.addFlashAttribute("message", "添加成功");
        return "redirect:/customer/my";
    }


    @GetMapping("/save")
    public String save(Model model) {
        model.addAttribute("trades", customerService.findAllCustomerTrode());
        model.addAttribute("sources", customerService.findAllCustomerSources());
        return "customer/save";
    }


    @GetMapping("/message/{id:\\d+}")
    public String message(@PathVariable Integer id, Model model, HttpSession session) {
        Client client = checkCustomerRole(id, session);
        model.addAttribute("Client", client);
        model.addAttribute("accountList",accountService.findAllAccount());
        return "customer/message";
    }


    @GetMapping("/edit/{id:\\d+}")
    public String edit(@PathVariable Integer id,
                       HttpSession session,
                       Model model) {
        Client client = checkCustomerRole(id, session);
        model.addAttribute("Client",client);
         model.addAttribute("trades", customerService.findAllCustomerTrode());
        model.addAttribute("sources", customerService.findAllCustomerSources());
        return "customer/edit";
    }


    /**
     * 修改信息
     * @param client
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/edit/{id:\\d+}")
    public String edit(Client client, HttpSession session,RedirectAttributes redirectAttributes) {
        checkCustomerRole(client.getId(),session);
       customerService.editClient(client);
         redirectAttributes.addFlashAttribute("message", "修改成功");
        return "redirect:/customer/message/"+ client.getId();
    }


     /**
     * 验证当前客户是否属于当前登录的对象
     *
     * @param id
     * @param session
     * @return
     */
    private Client checkCustomerRole(Integer id, HttpSession session) {

        Client client = customerService.findById(id);

        if (client == null) {
            throw new NotFoundException("找不到" + id + "对应的客户");
        }
        Account account = getCurrentAccount(session);
        if (!client.getAccountId().equals(account.getId())) {
            throw new ForbiddenException("没有查看" + id + "用户的权限");
        }
        return client;
    }


    /**
     * 删除客户
     * @return
     */
    @GetMapping("/my/{id:\\d+}/delete")
    public String delete (@PathVariable Integer id,HttpSession session,RedirectAttributes redirectAttributes){
        Client client= checkCustomerRole(id,session);
        customerService.deleteClient(client);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return  "redirect:/customer/my";
    }

    /**
     * 将客户放入公海
     * @param id
     * @param session
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/my/{id:\\d+}/public")
    public  String publicClient(@PathVariable Integer id, HttpSession session,RedirectAttributes redirectAttributes){
        Client client= checkCustomerRole(id,session);
        customerService.publicClent(client);
        redirectAttributes.addFlashAttribute("message","将该客户放入公海");
        return "redirect:/customer/my";
    }

    /**
     * 将客户转交他人
     * @param customerId
     * @param toAccountId
     * @param session
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/my/{customerId:\\d+}/tran/{toAccountId:\\d+}")
    public String tranClient(@PathVariable Integer customerId,
                             @PathVariable Integer toAccountId,
                             HttpSession session,
                             RedirectAttributes redirectAttributes){

         Client client=checkCustomerRole(customerId,session);
        customerService.tranClient(client,toAccountId);
        redirectAttributes.addFlashAttribute("message","转交成功");
        return "redirect:/customer/my";
    }

    @GetMapping("/my/export.csv")

    public void exportCsvData(HttpSession session, HttpServletResponse response) throws IOException {
       //查询当前所登录的用户
        Account account=getCurrentAccount(session);

        //设置类型
        response.setContentType("text/csv;charset=GBK");

        //EXel文件名称
        String fileName=new String("我的客户.csv".getBytes("UTF-8"),"ISO8859-1");

        //添加标题，表头
        response.addHeader("Content-Disposition","attachment; filename=\" "+fileName+ "\"");
        //输出流
        OutputStream outputStream=response.getOutputStream();
        //
        customerService.exportCsvFileToOutPutStream(outputStream,account);

    }
    @GetMapping("/my/export.xls")
  public void   exportXlsData(HttpServletResponse response,HttpSession session) throws  IOException{
    Account account= getCurrentAccount(session);
    response.setContentType("application/vnd.ms-excel");
    String fileName=new String("我的客户.xls".getBytes("UTF-8"),"ISO8859-1");

    response.addHeader("Content-Disposition","attachment;filename=\""+fileName+"\"");

    OutputStream outputStream = response.getOutputStream();
    customerService.exportXLSFileToOutPutStream(outputStream,account);
  }

}

