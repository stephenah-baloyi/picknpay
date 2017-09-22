/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.picknpay.service;

import com.picknpay.model.Product;
import com.picknpay.repository.ProductRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lucky
 */
@Service
public class ProductService {
    
    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public HashMap saveProduct(Product products){
        
        HashMap serverResponse = new HashMap();
        String message ="";
        String status = "";
       
        productRepository.save(products);
           message = "Product successfully added";
           status = "OK";
            
        serverResponse.put("status", status);
        serverResponse.put("message", message);
        serverResponse.put("prod",products);
        
        return serverResponse;
    } 
    
    
    public List<Product> allProducts(){
	
       List<Product> products = new ArrayList<>();
         
       productRepository.findAll().forEach(products::add);
       
       return products;
       
    }
    public Long find(Long Id)
    {
        Product prod =  productRepository.findOne(Id);
        Long productId = prod.getId();
        return productId;
    }
    public HashMap updateProduct(Product product, Long productId){
        
        HashMap serverResponse = new HashMap();
        String message ="";
        String status = "";
        
        List<Product> products = allProducts();
       
       for (int i = 0; i < products.size(); i++) {
           Product prodArray = products.get(i);
           
           if(prodArray.getId().equals(productId)){
            
               productRepository.save(product);
               message = "Product successfully Updated";
               status = "OK";
            }
            
       }                   
        serverResponse.put("status", status);
        serverResponse.put("message", message);
        serverResponse.put("prod",product);
        
        return serverResponse;
    } 
    public void deleteProduct(Product product){
        
       productRepository.delete(product);

    }
}
