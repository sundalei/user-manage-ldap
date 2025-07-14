package com.example.dto;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class UserDTO {

  /** Common name of the user. */
  private String commonName;

  /** Surname of the user. */
  private String sn;

  /** UID of the user. */
  private String uid;

  /** Password of the user. */
  private String userPassword;

  public String getCommonName() {
    return commonName;
  }

  public void setCommonName(final String commonName) {
    this.commonName = commonName;
  }

  public String getSn() {
    return sn;
  }

  public void setSn(final String sn) {
    this.sn = sn;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(final String uid) {
    this.uid = uid;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(final String userPassword) {
    this.userPassword = userPassword;
  }

  /**
   * Converts a string of ASCII values to a string of characters.
   *
   * @param asciiValues Comma-separated string of ASCII values
   * @return String of characters
   */
  public static String asciiValuesToString(String asciiValues) {
    return Stream.of(asciiValues.split(","))
        .map(Integer::parseInt)
        .map(code -> (char) code.intValue())
        .map(String::valueOf)
        .collect(Collectors.joining());
  }
}
