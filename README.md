# Spring Cloud Project Example
This project demonstrates usage of a native Spring Cloud dependencies
for building microservice architecture based on Java and Spring Boot.


## Cloud system components
The system consists of the following components:
1. Configuration Service
2. Discovery Service
3. Gateway Service
4. Custom User Service replicated twice
5. Custom Product Service replicated twice


### Configuration Service (Spring Cloud Config Server)
Spring Cloud Config Server provides an HTTP resource-based API 
for external configuration. The server is embeddable in a Spring Boot application, 
by using the `@EnableConfigServer` annotation.

The purpose of this server is to store the externalized configuration used by other services.


### Discovery Service (Spring Cloud Eureka Server)
Eureka Server is service discovery for your microservices, where all client applications can register by 
themselves and other microservices look up the Eureka Server to get independent microservices to get the job complete.

Eureka Server is also known as Discovery Server and it contains all the information about client microservices 
running on which IP address and port. Eureka Server can also provide essential metrics for each microservice.


### Gateway Service (Spring Cloud Gateway Server)
The Gateway Service provides out-of-the-box routing mechanisms often used in microservices applications 
as a way of hiding multiple services behind a single facade.

Being focused on routing requests, the Spring Cloud Gateway forwards requests to 
a Gateway Handler Mapping – which determines what should be done with requests matching a specific route.

Spring Cloud Gateway matches routes using the Spring WebFlux HandlerMapping infrastructure.

It also includes many built-in Route Predicate Factories. All of these predicates match different attributes 
of the HTTP request. Multiple Route Predicate Factories can be combined via the logical “and”.

Route matching can be applied both programmatically or via configuration properties file using a different type 
of Route Predicate Factories.


### Custom User Service
This microservice is custom-made service that has its own database and it's completely independent from 
other custom-made services.

### Custom Product Service
This microservice is custom-made service that has its own database and it's completely independent from
other custom-made services.

Using Open Feign Client, it implements inner communication with User Service. 
Open Feign is HTTP Client which provides load balancing out-of-the box and many other features.


## Architecture Diagram
![alt text](https://github.com/hedza06/spring-cloud-demo/blob/master/spring-cloud-platform.png)


## Running the project
1. Navigate to project directory and run command: `docker-compose build && docker-compose up -d`. This command will 
run MySQL database on port `3307/tcp`. Also, the authorization server (KeyCloak) will be started at port `8888/tcp`.
2. Configure Compound in IntelliJ (running multiple instances)


## Application ports
1. Config server: `8088/tcp`
2. Discovery server (Eureka): `8061/tcp`
3. Gateway server: `8060/tcp`
4. User microservice (instance one): `9900/tcp`
5. User microservice (instance two): `9901/tcp`
6. Product microservice (instance one): `9090/tcp`
7. Product microservice (instance two): `9091/tcp`
8. MySQL Database: `3307/tcp`
9. KeyCloak Authorization Server: `8888/tcp`


## Authorization
Authorization is used only while microservices (user & product) communicate with each other. 

The service that initiate HTTP request first must send authentication request to KeyCloak, and after getting an access 
token, can consume other REST APIs which require access token.

KeyCloak configuration from User microservice looks like the following:
```
keycloak:
  auth-server-url: http://localhost:8888/auth
  realm: SpringCloudKeycloak
  resource: product-user
  principal-attribute: product-user
  public-client: true
```
You should pay attention to configure all access data (realm, user, roles etc.) on KeyCloak before running 
the Demo Cloud Project.

Access (client) configuration from Product microservice looks like the following:
```
custom-auth:
  url: "http://localhost:8888/auth/realms/SpringCloudKeycloak/protocol/openid-connect/token"
  client-id: product-client
  client-username: product-user
  client-password: productuser321
```

### Open Feign Client Interceptor
User microservice endpoints only process authenticated requests which means that every client should first get the access
token from KeyCloak and then call specific API. In this use case, product service acts like client
and consumes endpoint from User service (but first it needs the access token).

Getting the access token is implemented using `FeignClientInterceptor` which
implements `RequestInterceptor`.

```
@Slf4j
@Component
@RequiredArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

    private final RestTemplate restTemplate;
    private final KeycloakCredentialsConfig credentialsConfig;


    @Override
    public void apply(RequestTemplate requestTemplate)
    {
        String accessToken = getAuthToken();
        requestTemplate.header("Authorization", "Bearer " + accessToken);
    }

    /**
     * Getting access token from KeyCloak Auth Server
     *
     * @return String value of access token
     */
    private String getAuthToken()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", credentialsConfig.getClientId());
        params.add("username", credentialsConfig.getClientUsername());
        params.add("password", credentialsConfig.getClientPassword());
        params.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        try
        {
            String url = credentialsConfig.getUrl();
            ResponseEntity<KeycloakToken> responseEntity = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, KeycloakToken.class
            );
            String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();

            Authentication authentication = new UsernamePasswordAuthenticationToken(accessToken, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return accessToken;
        }
        catch (RestClientException e) {
            log.error("Error occur while authenticating to KeyCloak. Message: {}", e.getMessage());
        }
        return null;
    }
}
```
Possible improvement in this use case can be to store access token for specific amount of time
in Redis database. This logic will be implemented soon.

## REST API Services
In folder `postman.collections` you can find JSON file that can be imported in Postman,
so you can use REST API services and test inner communication between services through gateway service.


## Coming next...
Next steps would be:
1. Adding keycloak as authorization server (secure custom microservices)
2. Implementation of hexagonal architecture
3. Adding Spring Cloud Vault


## Contribution/Suggestions
If someone is interested for contribution or have some suggestions please contact me on e-mail hedzaprog@gmail.com.
There are more to come from hexagonal architecture and first I'm planing to write tests for given examples in project.


## Author
Heril Muratović  
Software Engineer  

Mobile: +38269657962  
E-mail: hedzaprog@gmail.com  
Skype: hedza06  
Twitter: hedzakirk  
LinkedIn: https://www.linkedin.com/in/heril-muratovi%C4%87-021097132/  
StackOverflow: https://stackoverflow.com/users/4078505/heril-muratovic