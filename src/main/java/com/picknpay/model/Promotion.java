/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.picknpay.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class Promotion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Product id;
    private Product product;
    private String description;
    private double new_price;
    private double saved_amount;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date start_date;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date end_date;

    public Promotion() {
    }

    public Promotion(Product product, String description, double new_price, double saved_amount, Date start_date, Date end_date) {
        this.product = product;
        this.description = description;
        this.new_price = new_price;
        this.saved_amount = saved_amount;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    
    
    public Product getId() {
        return id;
    }

    public void setId(Product id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getNew_price() {
        return new_price;
    }

    public void setNew_price(double new_price) {
        this.new_price = new_price;
    }

    public double getSaved_amount() {
        return saved_amount;
    }

    public void setSaved_amount(double saved_amount) {
        this.saved_amount = saved_amount;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promotion)) {
            return false;
        }
        Promotion other = (Promotion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Promotion{" + "id=" + id + ", product=" + product + ", description=" + description + ", new_price=" + new_price + ", saved_amount=" + saved_amount + ", start_date=" + start_date + ", end_date=" + end_date + '}';
    }

}
