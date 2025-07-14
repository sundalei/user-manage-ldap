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
public final class User {

  /** DN of the user. */
  @Id
  @JsonSerialize(using = LdapNameSerializer.class)
  private Name dn;

  /** Common name of the user. */
  @Attribute(name = "cn")
  private String commonName;

  /** Surname of the user. */
  private String sn;

  /** UID of the user. */
  @DnAttribute(value = "uid", index = 1)
  private String uid;

  /** Password of the user. */
  @Attribute(name = "userPassword", type = Attribute.Type.BINARY)
  private byte[] userPassword;

  public Name getDn() {
    return dn;
  }

  public void setDn(final Name dn) {
    this.dn = dn;
  }

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

  public byte[] getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(final byte[] userPassword) {
    // this.userPassword = plaintextPassword.getBytes(java.nio.charset.StandardCharsets.UTF_8);
    this.userPassword = userPassword;
  }
}
