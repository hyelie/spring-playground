# spring-playground
Repository for technical issue benchmark and learning test

## Getting Started
### Prerequisite
 - JDK 17
 - Docker
 - Docker Compose

#### JDK 17
Install JDK 17 and refer to the following link to set environment variable. [Install JDK on Linux, Setup the Java development environment](https://hyelie.tistory.com/entry/GCP%EC%97%90-Java-%EA%B0%9C%EB%B0%9C%ED%99%98%EA%B2%BD-%EC%84%B8%ED%8C%85?category=947331)

```
sudo apt install openjdk-17-jdk
```

#### Docker, Docker-Compose
```
# install docker 
sudo apt-get update && sudo apt-get upgrade
sudo curl -fsSL https://get.docker.com/ | sudo sh
sudo chmod 777 /var/run/docker.sock
docker ps

# install docker compose
sudo curl -L https://github.com/docker/compose/releases/download/1.26.2/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
docker-compose --version
```

### Installation
```
git clone https://github.com/hyelie/spring-playground.git
cd spring-playground
```

### Set up DB
```
sudo chmod 755 ./env/mysql/conf.d/my.cnf
docker-compose -f ./env/docker-compose.yml --env-file ./env/.env.playground up -d
```

#### Connect your DB
```
docker ps
# CONTAINER ID   IMAGE       COMMAND                  CREATED          STATUS          PORTS                                                  NAMES
# f5affda97e9d   mysql:5.7   "docker-entrypoint.s…"   11 seconds ago   Up 10 seconds   33060/tcp, 0.0.0.0:3307->3306/tcp, :::3307->3306/tcp   playground_db

docker exec -it playground_db mysql -u root -p
# Enter password : password
```

### Run project
```
cd spring
./gradlew
./gradlew bootRun
```