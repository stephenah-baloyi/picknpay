/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.picknpay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Lucky
 */
@Controller
public class SiteController {
    
    @RequestMapping("/")
    public String index(){
        
        return "index";
    }
    
    @RequestMapping("/{page}")
    public String getView(@PathVariable("page") String page){
       
        //System.out.println(page);
        
        return page;
    }
    
}
