# myRetail-service

myRetail-service is a REST-based 'microservice' which exposes API for web and mobile clients to GET and UPDATE product information as JSON. The service runs on embedded Tomcat server. The service gets product details from another REST service (RedSky) and gets/updates price details from AWS DynamoDB NoSQL data source.

myRetail-service is Dockerized and can be run as a Docker container as well. Also can be deployed on cloud to achieve required scalability.

## Technologies and tools used -
* Java Spring Boot Framework
* Spring RestTemplate to call a REST service
* Spring Data to integrate with NoSQL data source
* AWS SDK to interact with DynamoDB
* Docker to build and run service as a Docker Container
* Gradle build framework
* JUnit with Mockito framework

## Get the source code
Clone from the git repository
```
git clone https://github.com/vinutahosur/myretail-service.git
```

## Build
Pre-requisites: gradle
```
cd myretail-service
gradle clean build
```

## Run as desktop application
```
java -jar build\libs\myretail-service-010.jar
```

## Populate Test Data in DynamoDB
Pre-requisites:Configure AWS credentials

* Create Table named "ProductPrice"
Table can be created from AWS console or using the CLI command below.
```
aws dynamodb create-table --table-name ProductPrice --attribute-definitions AttributeName=id,AttributeType=N --key-schema AttributeName=id,KeyType=HASH --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1
```

* Insert test data
Data can be inserted from AWS console or using the CLI command below.
```
aws dynamodb put-item --table-name ProductPrice --item file://ProductPrice_DataLoad.json
```

## Run as Docker Container
Pre-requisites:Install Docker

* Create docker image
```
docker build -t myretail-service:latest .
```

* Run Docker container
This command will make myretaile-service available on port 8080.
```
docker run -e AWS_ACCESS_KEY_ID=<your_aws_access_key> -e AWS_SECRET_ACCESS_KEY=<your_aws_secret_key> -p 8080:8080 myretail-service:latest
```

* List Docker Containers
```
docker ps
```

* Stop Docker Container
```
docker stop <container-id returned by 'docker ps'>
```

## Test
myRetail-service exposes two API - 
### 1. GET http://localhost:8080/myretail/products/{id}: 
Returns a '200 OK' HTTP response and product information for the {id} in JSON. 
As per sample data we have loaded,use id=13860428 to test.
Sample JSON response:
``` 
{
    "id": 13860428,
    "name": "The Big Lebowski (Blu-ray)",
    "current_price": {
        "value": 13.48,
        "currency_code": "USD"
    }
}
```
Error Handling:
1. For {id} where the product information is not returned by RedSky REST service, the above API returns '404 (Not Found)' HTTP response.
2. For {id} where the pricing information is not available in NoSQL data source, HTTP '200 OK' is recieved but the "current_price" value will be 'null' in the JSON response.

### 2. PUT http://localhost:8080/myretail/products/{id}: 
Update the Price information for Product with {id} in the NoSQL data source.
Sample JSON Request:
```
{
    "id": 13860428,
    "name": "The Big Lebowski (Blu-ray)",
    "current_price": {
        "value": 99.99,
        "currency_code": "USD"
    }
}
```
Returns a '200 OK' HTTP response and updated product information for the {id} in JSON. 
Sample JSON response:
```
{
    "id": 13860428,
    "name": "The Big Lebowski (Blu-ray)",
    "current_price": {
        "value": 99.99,
        "currency_code": "USD"
    }
}
```
Please note that as of now, this API can handle updates to Price information alone and not the change in name.

If using POSTMAN to test, you can import the 'myRetail_Test_Collection.postman_collection.json' test collection from project main folder.

## Yet to be addressed

* Security
    - API Access Control - OAuth2.0
    - Vulnerability Mitigation
* Caching 
* Comprehensive Error Handling
* Logging
* Auditing

