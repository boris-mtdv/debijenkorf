# Getting Started

## Prerequisites
Make sure you have java 11 and maven installed on your machine

## Testing
Navigate to the debijenkorf directory and run:

mvn clean test

## Running the application
First build the application by running:
mvn package

java -jar target/debijenkorf0.0.1-SNAPSHOT.jar

## Notes
Several requirements have been left out due to time constrants, and certain parts of the application have
been mocked, such as the Amazon Bucket Client, the process of downloading new images from the
source url, the different application environments, and the image resizing according to 
specified properties. The images have been represented as dummy byte arrays.

## Sending requests
The application runs on the default port for SpringBoot web applications: 8080

example url with path variables: 

http://0.0.0.0:8080/image/show/thumbnail/dummy_seo_name/?reference=first_image.jpg

## Extending the application
The application was designed with future extension in mind, such that if more resource types 
were to be processed a new service for that type would have to be added (one that implements 
the ResourceService interface) and configured in the ServiceLocator class. This would allow 
the logic in the rest controller to remain untouched (as long as the requests have the same format)
and to handle an indefinite variety of resource requests.