package com.example.config;

import javax.naming.ldap.LdapName;

import org.springframework.boot.jackson.ObjectValueSerializer;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;

public final class LdapNameSerializer extends ObjectValueSerializer<LdapName> {

  @Override
  protected void serializeObject(
      LdapName value, JsonGenerator jgen, SerializationContext context) {
    jgen.writeString(value.toString());
  }
}
