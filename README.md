The calculator application is built using Spring Boot.

## Running the application

Application can be started up by entering the following command from the calculator directory  
./gradlew bootRun

Once running, the application may be accessed at  
http://localhost:8080/v1/api/calculate  
API is authenticated using basic auth and can be accessed with the following seeded credentials –  
Username: external-user  
Password: Id@4567

## Application Details

API used for fetching exchange convertion rate is https://www.exchangerate-api.com/  
Exchange rates are cached in a Java Bean and updated daily
