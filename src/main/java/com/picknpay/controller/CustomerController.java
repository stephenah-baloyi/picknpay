/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.picknpay.controller;

import com.picknpay.model.Customer;
import com.picknpay.service.CustomerService;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Lucky
 */
@RestController
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public HashMap register(@RequestBody Customer customer)
    {
        HashMap responseData = customerService.saveCustomer(customer);
        
        return responseData;
    }
    
   /* @RequestMapping(value = "/register/{customer}",method = RequestMethod.GET)
    public HashMap registerExtra(@PathVariable("customer") Customer customer){
        
        
        //System.out.println(personB.toString());
        //HashMap responseData = personService.saveCustomer(customer);
        
        return null;
    }*/
    @RequestMapping( value = "/login", method = RequestMethod.POST)
    public HashMap login(@RequestBody Customer customer,HttpServletRequest request){
        
        HttpSession session = request.getSession();
        HashMap responseData = customerService.login(customer,session);
        
        return responseData;
      
    }
    @RequestMapping( value = "/logout/{sessionId}/{userId}", method = RequestMethod.POST)
    public HashMap logout(@PathVariable("sessionId") String sessionId,@PathVariable("userId") Long userId){
        
        HashMap responseData = customerService.logout(sessionId,userId);
        
        return responseData;
      
    }
}
