/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.picknpay.repository;

import com.picknpay.model.Product;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Lucky
 */
public interface ProductRepository extends CrudRepository<Product, Long>{
    
}
