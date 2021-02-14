sudo apt update
sudo apt upgrade -y

sudo apt install openjdk-8-jdk-headless -y
sudo apt install maven -y

sudo apt install docker.io -y
sudo systemctl start docker
# postgres
sudo apt-get install -y postgresql postgresql-contrib
sudo systemctl start postgresql


cd xmeme-backend
mvn clean install -Dmaven.test.skip=true

# test performance diff b/w postgres and h2
# https://medium.com/@r.thilina/handling-log-files-in-spring-boot-microservices-63810289637f
# docker run --rm -d --name demo-app demo-app
# 
# docker exec -it demo-app /bin/sh
