package BBM.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {


@Id
private String phone;  // Primary key based on phone number

private String name;
private String dob;
private String gender;
private String bloodGroup;

private String password;
private String state;
private String district;
private String address;
private String email;
private String pinCode;
//private LocalDateTime otpGeneratedTime; 

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

// Getters and Setters
public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getDob() {
    return dob;
}

public void setDob(String dob) {
    this.dob = dob;
}

public String getGender() {
    return gender;
}

public void setGender(String gender) {
    this.gender = gender;
}

public String getBloodGroup() {
    return bloodGroup;
}

public void setBloodGroup(String bloodGroup) {
    this.bloodGroup = bloodGroup;
}

public String getPhone() {
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

public String getState() {
    return state;
}

public void setState(String state) {
    this.state = state;
}

public String getDistrict() {
    return district;
}

public void setDistrict(String district) {
    this.district = district;
}

public String getAddress() {
    return address;
}

public void setAddress(String address) {
    this.address = address;
}

public String getPinCode() {
    return pinCode;
}

public void setPinCode(String pinCode) {
    this.pinCode = pinCode;
}

} 
