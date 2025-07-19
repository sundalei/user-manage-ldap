#!/bin/sh

# Wait for the OpenLDAP server to be ready
sleep 10

# Copy the users.ldif file to the appropriate location
cp /tmp/users.ldif /users.ldif

# Import the data using ldapadd
ldapadd -x -D "cn=admin,dc=springframework,dc=org" -w "${LDAP_ADMIN_PASSWORD}" -f /users.ldif