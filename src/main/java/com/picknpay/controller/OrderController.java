/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.picknpay.controller;

import com.picknpay.model.OrderDetails;
import com.picknpay.model.Orders;
import com.picknpay.model.Product;
import com.picknpay.model.ShippingInfo;
import com.picknpay.repository.OrderDetailsRepository;
import com.picknpay.repository.OrderRepository;
import com.picknpay.repository.ProductRepository;
import com.picknpay.service.CustomerService;
import com.picknpay.service.OrderService;
import com.picknpay.service.ProductService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HP
 */
@RestController
public class OrderController {
    
    @Autowired
    private OrderService orderService;
     @Autowired
    private OrderRepository orderRepository;
     
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    
    @RequestMapping(method = RequestMethod.POST,value = "/shipping")
    public HashMap shipping(@RequestBody ShippingInfo ships){
        //String message = "Shipping Info added";    
        HashMap response = orderService.saveShipping(ships);
        
        //response.put("message", message);
        return response;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/checkout/{prodId}/{custId}")
    public Orders createOrder(@RequestBody ArrayList<Product> product,@PathVariable("prodId") Long prodId,@PathVariable("custId") Long custId){
        Date date = new Date();
        String status = "Not Delivered";

        Orders order = new Orders(date,status,custId,null);
        
        orderService.createOrder(order);
        
        double total = 0;
        Long tmp = 0L;
        for (int i = 0; i < product.size(); i++) {
          
            OrderDetails details = new OrderDetails(order.getId(),prodId,product.get(i).getProductName(),product.get(i).getQuantity(),
                    product.get(i).getPrice(),order.getTotal(product.get(i).getPrice(), product.get(i).getQuantity())); 
            
            orderDetailsRepository.save(details);
            
            //tmp = order.getId();
        }
           
        return order;
    }
    
   /* @RequestMapping(method = RequestMethod.POST,value = "/checkout")
    public HashMap checkout(@RequestBody List<Product> product){
      Date orderDate = new Date();
      double total = 0;
      String status = "Not Delivered";
      //String message = "Order Created";
      //orderService.findById(shiping_id);
       HashMap response = new HashMap();     
      for(int i =0; i < product.size(); i++)
      {
        Orders orders = new Orders(product.get(i).getQuantity(),product.get(i).getPrice(),total,orderDate, status,
                null,null,productRepository.findOne(product.get(i).getId()));        
        
        
        response = orderService.createOrder(orders);
      }    
      return response;
    }*/
    
}
