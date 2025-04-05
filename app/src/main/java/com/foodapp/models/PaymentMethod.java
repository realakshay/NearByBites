package com.foodapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * Model class to represent a payment method
 */
public class PaymentMethod implements Parcelable {
    private int id;
    private String cardNumber;
    private String cardholderName;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;
    private String cardType;
    private String type; // Payment type (e.g., "Credit Card", "PayPal")
    private String detail; // Payment detail (e.g., "xxxx xxxx xxxx 1234")
    
    // Constructor for card details
    public PaymentMethod(String cardNumber, String cardholderName, String expiryMonth, String expiryYear, String cvv, String cardType) {
        this.cardNumber = cardNumber;
        this.cardholderName = cardholderName;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvv = cvv;
        this.cardType = cardType;
        this.type = "Credit Card";
        this.detail = "xxxx xxxx xxxx " + cardNumber.substring(cardNumber.length() - 4);
    }
    
    // Constructor for simplified payment method
    public PaymentMethod(int id, String type, String detail) {
        this.id = id;
        this.type = type;
        this.detail = detail;
    }
    
    protected PaymentMethod(Parcel in) {
        id = in.readInt();
        cardNumber = in.readString();
        cardholderName = in.readString();
        expiryMonth = in.readString();
        expiryYear = in.readString();
        cvv = in.readString();
        cardType = in.readString();
        type = in.readString();
        detail = in.readString();
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(cardNumber);
        dest.writeString(cardholderName);
        dest.writeString(expiryMonth);
        dest.writeString(expiryYear);
        dest.writeString(cvv);
        dest.writeString(cardType);
        dest.writeString(type);
        dest.writeString(detail);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public static final Creator<PaymentMethod> CREATOR = new Creator<PaymentMethod>() {
        @Override
        public PaymentMethod createFromParcel(Parcel in) {
            return new PaymentMethod(in);
        }
        
        @Override
        public PaymentMethod[] newArray(int size) {
            return new PaymentMethod[size];
        }
    };
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public String getCardholderName() {
        return cardholderName;
    }
    
    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }
    
    public String getExpiryMonth() {
        return expiryMonth;
    }
    
    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }
    
    public String getExpiryYear() {
        return expiryYear;
    }
    
    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }
    
    public String getCvv() {
        return cvv;
    }
    
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    
    public String getCardType() {
        return cardType;
    }
    
    public void setCardType(String cardType) {
        this.cardType = cardType;
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
    
    public String getFormattedExpiry() {
        return expiryMonth + "/" + expiryYear;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentMethod that = (PaymentMethod) o;
        return Objects.equals(cardNumber, that.cardNumber);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(cardNumber);
    }
}
