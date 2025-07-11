package com.example.entity;

import com.example.config.LdapNameSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.naming.Name;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

@Entry(
    base = "ou=groups",
    objectClasses = {"inetOrgPerson", "organizationalPerson", "person", "top"})
public class User {

  @Id
  @JsonSerialize(using = LdapNameSerializer.class)
  private Name dn;

  @Attribute(name = "cn")
  private String commonName;

  private String sn;

  @DnAttribute(value = "uid", index = 1)
  private String uid;

  @Attribute(name = "userPassword", type = Attribute.Type.BINARY)
  private byte[] userPassword;

  public Name getDn() {
    return dn;
  }

  public void setDn(Name dn) {
    this.dn = dn;
  }

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

  public byte[] getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(byte[] userPassword) {
    // this.userPassword = plaintextPassword.getBytes(java.nio.charset.StandardCharsets.UTF_8);
    this.userPassword = userPassword;
  }
}
