sudo apt update
sudo apt upgrade -y
echo "intsall java"
sudo apt install openjdk-8-jdk-headless -y
echo "install java done"
echo "intsall maven"
sudo apt install maven -y
echo "intsall maven done"
# docker
echo "intsall docker"
sudo apt install docker.io -y
echo "intsall docker done"
# postgres
echo "intsall postgres"
sudo apt-get install -y postgresql postgresql-contrib
echo "intsall postgres done"

echo "start docker"
sudo systemctl start docker
echo "docker started"

echo "start postgres"
sudo systemctl start postgresql
echo "start postgres done"
# make changes to postgres
echo "make postgres changes"
sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'postgres';"
sudo -u postgres psql -c "CREATE DATABASE postgres;"
sudo -u postgres psql -c "DROP TABLE meme_table;"
echo "posgres changes done"

echo "build backend"
cd xmeme-backend
mvn clean install -Dmaven.test.skip=true
echo "build backend done"

echo "going back to root repo dir"
cd -
echo "going back to root repo dir done"