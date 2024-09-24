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
  
### Description:  
Incoming request should have below fields as part of mocking  
- userRole as [Employee / Affiliate / Customer] (Ignorecase) to apply the percentage discounts.
- userCreatedOn field as LocalDate(YYYY-MM-DD) to check if the customer is eligible for percentage discount
- inside items array, category field is used to check if product is grocery or not. If value is passed as Grocery(Ignorecase) no percentage discounts will be applied
  
Response is an order structure, where discounts applied to each product can be seen in DiscountInfo and PriceInfo sections  
** Net Payable amount is given in "data.priceInfo.targetCurrency.netPrice" field of response **
