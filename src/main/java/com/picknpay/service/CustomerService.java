/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.picknpay.service;

import com.picknpay.model.Customer;
import com.picknpay.repository.CustomerRepository;
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
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public List<Customer> getCustomers()
    {
        List<Customer> customer = new ArrayList<>();
        
        customerRepository.findAll().forEach(customer::add);
        return customer;
    }
    public HashMap saveCustomer(Customer customer){
        
        HashMap serverResponse = new HashMap();
        String message = "Sorry , the username/email is already registered";
        String status = "FAILED";
        
        //searching the database for available customers with same email.
        Customer cust = customerRepository.findByEmail(customer.getEmail());
        
        //if the customer email is not available, you register
        if(cust == null){
            customerRepository.save(customer);
            message = "You are successfully registered";
            status = "OK";
        }
        
        serverResponse.put("status", status);
        serverResponse.put("message", message);
        
        return serverResponse;
    }   

    public HashMap login(Customer customer, HttpSession session){
        
        List<Customer> customers = getCustomers();
        HashMap customerResponse = new HashMap();
        
        String email = customer.getEmail();
        String password = customer.getPassword();
        
        String message = "Username and Password not found, Please register first.";
        String url = "/login";
        HttpStatus status = null;
        String sessionId = "";
        
        Customer loggedCustomer = null;
         
        for (int i = 0; i < customers.size(); i++) {
            Customer custArray = customers.get(i);
            
            if(custArray.getEmail().equals(email))
            {
                if(custArray.getPassword().equals(password))
                {
                    status = HttpStatus.FOUND;
                    message = "You have successfully logged in.";
                    url = "/";
                    loggedCustomer = custArray;
                    sessionId = session.getId();
                    loggedCustomer.setLoginID(sessionId);
                    customerRepository.save(loggedCustomer);
                    
                }
                else{
                    status = HttpStatus.NOT_FOUND;
                    message = "Password is incorrect, try again";
                }
                i = customers.size() + 1;
            }else{
                message = "Email is incorrect, try again."; 
            }
        }
        customerResponse.put("HttpStatus", status);
        customerResponse.put("url", url);
        customerResponse.put("message", message);
        customerResponse.put("sessionId",sessionId);
        customerResponse.put("loggedCustomer",loggedCustomer);
        customerResponse.put("userId",loggedCustomer.getId());
        return customerResponse; 
    }
    
        
    public HashMap logout(String sessionId, Long custId){
        HashMap response = new HashMap();

        Customer customer = customerRepository.findOne(custId);
        String loginId = customer.getLoginID();
        String status = "";
                
        if(sessionId.equals(loginId))
        {
            customer.setLoginID(null);
            customerRepository.save(customer);
            status = "OK";
            //System.out.println(status);          
        }
        response.put("status",status);
        return response;
    }
    public Customer findById(Long Id)
    {
        return customerRepository.findOne(Id);
    }
}
