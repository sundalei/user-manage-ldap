package com.example.dto;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserDTO {

  private String commonName;
  private String sn;
  private String uid;
  private String userPassword;

  // Getters and Setters
  public String getCommonName() {
    return commonName;
  }

  public void setCommonName(String commonName) {
    this.commonName = commonName;
  }

  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }

  public static String asciiValuesToString(String asciiValues) {
    return Stream.of(asciiValues.split(","))
        .map(Integer::parseInt)
        .map(code -> (char) code.intValue())
        .map(String::valueOf)
        .collect(Collectors.joining());
  }
}
