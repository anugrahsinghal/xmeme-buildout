# sudo apt-get update

sudo apt-get install -y openjdk-8-jdk
sudo apt-get install -y maven

sudo apt-get install -y postgresql postgresql-contrib

echo "Starting postgres"
pg_ctl start -l logfile
sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'postgres';"
sudo -u postgres psql -c "CREATE DATABASE postgres;"
echo "posgres done"


cd xmeme-backend
mvn clean install -Dmaven.test.skip=true
cd -