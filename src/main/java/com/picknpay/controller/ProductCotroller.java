/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.picknpay.controller;

import com.picknpay.model.Product;
import com.picknpay.service.ProductService;
import java.util.HashMap;
import java.util.List;
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
public class ProductCotroller {
    
    @Autowired
    private ProductService productService;
    
    @RequestMapping(value = "/admin/product", method = RequestMethod.POST)
    public HashMap addproduct(@RequestBody Product product)
    {
        HashMap responseData = productService.saveProduct(product);
        
        return responseData;
    }
    
    @RequestMapping("/admin/products")
    public List<Product> findAllProducts(){
        List<Product> allProducts = productService.allProducts();
        //System.out.println(allProducts.toString());
        return allProducts;
       
    }
    @RequestMapping("/admin/display" )
    public List<Product> display(){
        List<Product> allProducts = productService.allProducts();
        //System.out.println(allProducts.toString());
        return allProducts;
       
    }
    @RequestMapping(method = RequestMethod.POST, value = "admin/updateProduct/{productId}")
    public HashMap updateProduct(@RequestBody Product product, @PathVariable("productId") Long productId){
        
        HashMap responseData =  productService.updateProduct(product, productId);

       return  responseData;
        
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/admin/deleteProduct/{productId}")
    public void deleteProduct(@PathVariable("productId") Product productId){
        
        //Product product = productService.find(productId);
        productService.deleteProduct(productId);

    }
    
}
