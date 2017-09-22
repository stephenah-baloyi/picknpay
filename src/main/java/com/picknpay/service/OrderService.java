/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.picknpay.service;

import com.picknpay.controller.ProductCotroller;
import com.picknpay.model.Orders;
import com.picknpay.model.Product;
import com.picknpay.model.ShippingInfo;
import com.picknpay.repository.CustomerRepository;
import com.picknpay.repository.OrderRepository;
import com.picknpay.repository.ShippingRepository;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ShippingRepository shippingRepository;
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private ProductCotroller productCotroller;

    public OrderService(OrderRepository orderRepository, ShippingRepository shippingRepository, CustomerRepository customerRepository, ProductCotroller productCotroller) {
        this.orderRepository = orderRepository;
        this.shippingRepository = shippingRepository;
        this.customerRepository = customerRepository;
        this.productCotroller = productCotroller;
    }

  public HashMap saveShipping(ShippingInfo shipping){
        
        HashMap serverResponse = new HashMap();
        String message ="";
        String status = "";
       
        shippingRepository.save(shipping);
        message = "Shipping Info successfully added";
        status = "OK";
            
        serverResponse.put("status", status);
        serverResponse.put("message", message);
        serverResponse.put("info",shipping);
        
        return serverResponse;
    }
    public ShippingInfo findById(Long Id)
    {
        return shippingRepository.findOne(Id);
    }
    public HashMap createOrder(Orders orders)
    {
        HashMap serverResponse = new HashMap();
        String message ="";
        String status = "";
       
        orderRepository.save(orders);
        message = "Order successfully added";
        status = "OK";
            
        serverResponse.put("status", status);
        serverResponse.put("message", message);
        serverResponse.put("order",orders);
        
        return serverResponse;
    }
    
    
}
