/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.picknpay.controller;

import com.picknpay.model.Employee;
import com.picknpay.service.EmployeeService;
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
public class EmployeeContoller {
    
    @Autowired
    private EmployeeService employeeService;
    
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public HashMap saveEmployee(@RequestBody Employee employee)
    {
        HashMap responseData = employeeService.saveEmployee(employee);
        
        return responseData;
    }
    @RequestMapping( value = "/admin/adminLogin", method = RequestMethod.POST)
    public HashMap login(@RequestBody Employee employee,HttpServletRequest request){
        
        HttpSession session = request.getSession();
        HashMap responseData = employeeService.login(employee,session);
        
        return responseData;
      
    }
    @RequestMapping( value = "/admin/logoutAdmin/{sessionId}/{userId}", method = RequestMethod.POST)
    public HashMap logout(@PathVariable("sessionId") String sessionId,@PathVariable("userId") Long userId){
        
        HashMap responseData = employeeService.logout(sessionId,userId);
        
        return responseData;
      
    }
    
}
