# Setup an OpenLDAP server running in Docker

## Start a container with docker compose

```bash
docker compose up -d
```

## Populate sample data

```bash
docker cp users.ldif openldap-server:/

docker container exec -it openldap-server ldapadd -x -D "cn=admin,dc=springframework,dc=org" -w password -f /users.ldif
```

## Verify data loaded

```bash
docker container exec my-openldap-server ldapsearch -x -H ldap://localhost -b "dc=springframework,dc=org" -D "cn=admin,dc=springframework,dc=org" -w password "(uid=dbrown)"
```
