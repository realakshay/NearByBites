package com.foodapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class to represent a user
 */
public class User implements Parcelable {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    
    public User(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
    
    // Constructor for processing a full name
    private void processFullName(String fullName) {
        // Parse full name
        if (fullName != null && !fullName.isEmpty()) {
            if (fullName.contains(" ")) {
                int lastSpaceIndex = fullName.lastIndexOf(" ");
                this.firstName = fullName.substring(0, lastSpaceIndex);
                this.lastName = fullName.substring(lastSpaceIndex + 1);
            } else {
                // Only one name provided
                this.firstName = fullName;
                this.lastName = "";
            }
        }
    }
    
    // Constructor for social login where first parameter is the full name
    public User(String fullName, boolean isSocialLogin, String email, String phone) {
        this.email = email;
        this.phone = phone;
        processFullName(fullName);
    }
    
    protected User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        email = in.readString();
        phone = in.readString();
        password = in.readString();
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(password);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }
        
        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    
    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFullName() {
        if (lastName != null && !lastName.isEmpty()) {
            return firstName + " " + lastName;
        }
        return firstName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    // Legacy method name for backward compatibility
    public String getPhoneNumber() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
