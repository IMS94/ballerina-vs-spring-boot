# Ballerina Vs. Spring Boot - REST APIs with OAuth2 Security

## The Scenario

```mermaid
sequenceDiagram
    autonumber

    actor User
    participant Auth as Authentication<br/>Layer
    participant API as REST API
    participant ExtAPI as External API<br/>(REST Countries)
    participant IS as OAuth2 Service Provider

    activate User
    activate Auth

    User ->> Auth: GET /country/{callingcode}
    Note right of User: Include authorization header<br/>Authorization: Bearer ${access_token}

    activate IS
    Auth ->> IS: oauth2/introspect
    IS -->> Auth: Introspect Response
    deactivate IS
    Auth ->> Auth: Check if token is valid/active

    activate API
    Auth ->> API: Forward request(GET /country/{callingcode})
    deactivate Auth

    activate ExtAPI
    API ->> ExtAPI: GET /v2/callingcode/{callingcode}
    ExtAPI -->> API: Response <br/>(countries by provided calling code)
    deactivate ExtAPI

    API ->> API: Extract country names from response

    API -->> User: Respond with country names

    deactivate API
```

## Requests

### Obtain Access Token - Client Credentials
```curl
curl --location --request POST 'https://api.asgardeo.io/t/imeshaorg/oauth2/token' \
    --header 'Content-Type: application/x-www-form-urlencoded' \
    --data-urlencode 'grant_type=client_credentials' \
    --data-urlencode 'client_id=<client_id>' \
    --data-urlencode 'client_secret=<client_secret>'
```

### Invoke Service - Get Country by Calling Code

```curl
curl --location --request GET 'http://localhost:8080/country/1' \
    --header 'Authorization: Bearer <access_token>'
```

## References

1. Spring OAuth2 Resource Server - https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/opaque-token.html
2. BBE OAuth2 Service - https://ballerina.io/learn/by-example/http-service-oauth2/
