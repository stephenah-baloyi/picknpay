/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.picknpay.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author HP
 */
@Entity
public class OrderDetails implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long OrderId;
    private Long produdtId;
    private String productName;
    private int quantity;
    private double price;
    private double total;

    public OrderDetails() {
    }

    public OrderDetails(Long OrderId, Long produdtId, String productName, int quantity, double price, double total) {
        this.OrderId = OrderId;
        this.produdtId = produdtId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return OrderId;
    }

    public void setOrderId(Long OrderId) {
        this.OrderId = OrderId;
    }

    public Long getProdudtId() {
        return produdtId;
    }

    public void setProdudtId(Long produdtId) {
        this.produdtId = produdtId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    } 
    public void intock(int qty)
    {
        qty -= this.quantity;
    }
    @Override
    public String toString() {
        return "OrderDetails{" + "id=" + id + ", OrderId=" + OrderId + ", produdtId=" + produdtId + ", productName=" + productName + ", quantity=" + quantity + ", price=" + price + ", total=" + total + '}';
    }
    
    
    
}
