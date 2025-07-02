# Setup an OpenLDAP server running in Docker

## Start a container

```bash
docker container run --name my-openldap-server \
-p 389:389 \
-p 636:636 \
--detach \
-e LDAP_ORGANISATION="SpringFramework Org" \
-e LDAP_DOMAIN="springframework.org" \
-e LDAP_ADMIN_PASSWORD="password" \
osixia/openldap:1.5.0 --copy-service --loglevel debug
```
