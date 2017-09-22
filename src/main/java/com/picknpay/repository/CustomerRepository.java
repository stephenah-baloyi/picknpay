/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.picknpay.repository;

import com.picknpay.model.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Lucky
 */
public interface CustomerRepository extends CrudRepository<Customer, Long>{
    Customer findByEmail(String email);
    
}
