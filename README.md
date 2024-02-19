# Spring Boot 3, Spring Security, and Keycloak

## Purpose
A sample java code to demonstrate a Spring Boot 3 integration with Keycloak 17. It utilize Keycloak login page, and fetch a user's attribute based on Keycloak user profile. 

## Version
- Spring Boot 3.0.4
- Keycloak 23
- Red Hat OpenJDK 17
- Grafana
- Prometheus

---
## Screenshots
Keycloak User Profile

![User Profile](images/sboot-keycloak-01.png)

JSON Response reading Keycloak Profile

![JSON](images/sboot-keycloak-02.png)

---

## Blog Post
Explanation of this code can be seen on below `Red Hat Developer` article, 
```
https://developers.redhat.com/articles/2023/07/24/how-integrate-spring-boot-3-spring-security-and-keycloak
```
---

## Keycloak with PostgreSQL, which includes Keycloak's monitoring using Prometheus and Grafana

1. Requires [docker](https://docs.docker.com/get-docker/) and [compose](https://docs.docker.com/compose/install/)
2. Parameterized using variables in the [`.env`](.env) file
3. Up the project using command:
```
docker compose up -d
```

| App | Port | Username | Password
|-|-|-|-
| Keycloak | 8080 | `admin` | `keycloak`
| Prometheus | 9090 | |
| Grafana | 3000 | `admin` | `grafana`

| Useful commands | Discription
|-|-
| `docker stats` | Containers resource usage (`--no-stream` only pull the first result)
| `docker compose logs` | Shows logs of containers (`-f` to follow logs)
| `docker compose down` | Stop and remove containers (`-v` remove named volumes declared in the volumes section of the Compose file and anonymous volumes attached to containers)
| `docker system prune -a -f` | Remove all unused containers, networks, images (`--volumes` prune volumes)

---

## Disclaimer
```
This code is provided "as is" without any guarantee whatsoever. 
Feel free to fork, add, remove, change, or do whatever you want with it. 
```