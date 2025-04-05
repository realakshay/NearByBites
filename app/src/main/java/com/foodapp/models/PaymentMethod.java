package com.foodapp.models;

public class PaymentMethod {
    private int id;
    private String type;
    private String detail;
    
    public PaymentMethod(int id, String type, String detail) {
        this.id = id;
        this.type = type;
        this.detail = detail;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getDetail() {
        return detail;
    }
    
    public void setDetail(String detail) {
        this.detail = detail;
    }
}
