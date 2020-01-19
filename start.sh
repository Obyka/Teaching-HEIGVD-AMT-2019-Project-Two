docker-compose -f docker/docker-compose.yaml down
cd project2-api-login/spring-server
mvn clean install -DskipTests
cp target/*.jar ../../docker/api-login/api-login-1.0.0.jar

cd ../../project2-api-pokemon/spring-server
cp target/*.jar ../../docker/api-pokemon/api-pokemon-1.0.0.jar
cd ../..
docker-compose -f docker/docker-compose.yaml down
docker-compose -f docker/docker-compose.yaml up --build
