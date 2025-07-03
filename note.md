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

## Populate sample data

```bash
docker cp users.ldif my-openldap-server:/

docker container exec -it my-openldap-server ldapadd -x -D "cn=admin,dc=springframework,dc=org" -w password -f /users.ldif
```

## Verify data loaded

```bash
docker container exec my-openldap-server ldapsearch -x -H ldap://localhost -b "dc=springframework,dc=org" -D "cn=admin,dc=springframework,dc=org" -w password "(uid=dbrown)"
```
