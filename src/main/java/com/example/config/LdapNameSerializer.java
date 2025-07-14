package com.example.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import javax.naming.ldap.LdapName;

public final class LdapNameSerializer extends JsonSerializer<LdapName> {

  @Override
  public void serialize(
      final LdapName value, final JsonGenerator gen, final SerializerProvider serializers)
      throws IOException {
    gen.writeString(value.toString());
  }
}
