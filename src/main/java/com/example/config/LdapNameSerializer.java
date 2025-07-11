package com.example.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import javax.naming.ldap.LdapName;

public class LdapNameSerializer extends JsonSerializer<LdapName> {

  @Override
  public void serialize(LdapName value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeString(value.toString());
  }
}
