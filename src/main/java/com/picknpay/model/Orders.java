/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.picknpay.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author Lucky
 */
@Entity
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date orderDate;
    private String status;
    private Long customer;
    private Long shipping;

    public Orders() {
    }

    public Orders(Date orderDate, String status, Long customer, Long shipping) {
        this.orderDate = orderDate;
        this.status = status;
        this.customer = customer;
        this.shipping = shipping;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCustomer() {
        return customer;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    public Long getShipping() {
        return shipping;
    }

    public void setShipping(Long shipping) {
        this.shipping = shipping;
    }
    public double getTotal(double price, int qty)
    {
        double total =0.0;
        
        total += price * qty;
        return total;
    }
    
    @Override
    public String toString() {
        return "Orders{" + "id=" + id + ", orderDate=" + orderDate + ", status=" + status + ", customer=" + customer + ", shipping=" + shipping + '}';
    }
    

}
