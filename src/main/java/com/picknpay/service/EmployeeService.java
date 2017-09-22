/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.picknpay.service;

import com.picknpay.model.Employee;
import com.picknpay.repository.EmployeeRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lucky
 */
@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
    public HashMap saveEmployee(Employee employee){
        
        HashMap serverResponse = new HashMap();
        String message = "Sorry , the username/email is already registered";
        String status = "FAILED";
        
        //searching the database for available customers with same email.
        Employee emp = employeeRepository.findByEmail(employee.getEmail());
        
        //if the customer email is not available, you register
        if(emp == null){
            employeeRepository.save(employee);
            message = "You are successfully registered";
            status = "OK";
        }
        
        serverResponse.put("status", status);
        serverResponse.put("message", message);
        
        return serverResponse;
    }
    
    public List<Employee> getEmplyees()
    {
        List<Employee> employee = new ArrayList<>();
        
        employeeRepository.findAll().forEach(employee::add);
        return employee;
    }
    
    public HashMap login(Employee employee, HttpSession session){
        
        List<Employee> employees = getEmplyees();
        HashMap employeeResponse = new HashMap();
        
        String email = employee.getEmail();
        String password = employee.getPassword();
            
        String message = "Email and Password not found, Please register first.";
        String url = "/admin/adminLogin";
        HttpStatus status = null;
        String sessionId = "";
        
        Employee loggedEmp = null;
         
        for (int i = 0; i < employees.size(); i++) {
            Employee empArray = employees.get(i);
            
            if(empArray.getEmail().equals(email))
            {   
                if(empArray.getPassword().equals(password))
                {
                    status = HttpStatus.FOUND;
                    message = "You have successfully logged in.";
                    url = "/admin/products";
                    loggedEmp = empArray;
                    sessionId = session.getId();
                    loggedEmp.setLoginID(sessionId);
                    employeeRepository.save(loggedEmp);
                    
                }
                else{
                        status = HttpStatus.NOT_FOUND;
                        message = "Password is incorrect, try again";
                }
                i = employees.size() + 1;
            }else{
                message = "Email is incorrect, try again."; 
            }
        }
        employeeResponse.put("status", status);
        employeeResponse.put("url", url);
        employeeResponse.put("message", message);
        employeeResponse.put("sessionId",sessionId);
        employeeResponse.put("loggedEmp",loggedEmp);
        employeeResponse.put("userId",loggedEmp.getId());
        return employeeResponse; 
    }
    
    public HashMap logout(String sessionId, Long empId){
        HashMap response = new HashMap();
        
        Employee employee = employeeRepository.findOne(empId);
        String loginId = employee.getLoginID();
        String status = "";
       
        if(sessionId.equals(loginId))
        {
            employee.setLoginID(null);
            employeeRepository.save(employee);
             status = "OK";
                      
        }
        response.put("status",status);
        return response;
    }
    
}
