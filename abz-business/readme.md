= OAuth 2.0 Resource Server Sample

This sample demonstrates integrating Resource Server with a mock Authorization Server, though it can be modified to integrate
with your favorite Authorization Server.

With it, you can run the integration tests or run the application as a stand-alone service to explore how you can
secure your own service with OAuth 2.0 Bearer Tokens using Spring Security.

== 1. Running the tests

To run the tests, do:

```bash
./gradlew integrationTest
```

Or import the project into your IDE and run `OAuth2ResourceServerApplicationTests` from there.

=== What is it doing?

By default, the tests are pointing at a mock Authorization Server instance.

The tests are configured with a set of hard-coded tokens originally obtained from the mock Authorization Server,
and each makes a query to the Resource Server with their corresponding token.

The Resource Server subsquently verifies with the Authorization Server and authorizes the request, returning the phrase

```bash
Hello, subject!
```

where "subject" is the value of the `sub` field in the JWT returned by the Authorization Server.

== 2. Running the app

To run as a stand-alone application, do:

```bash
./gradlew bootRun
```

Or import the project into your IDE and run `OAuth2ResourceServerApplication` from there.

Once it is up, you can use the following token:

```bash
export TOKEN=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzdWJqZWN0IiwiZXhwIjoyMTY0MjQ1ODgwLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiMDFkOThlZWEtNjc0MC00OGRlLTk4ODAtYzM5ZjgyMGZiNzVlIiwiY2xpZW50X2lkIjoibm9zY29wZXMiLCJzY29wZSI6WyJub25lIl19.VOzgGLOUuQ_R2Ur1Ke41VaobddhKgUZgto7Y3AGxst7SuxLQ4LgWwdSSDRx-jRvypjsCgYPbjAYLhn9nCbfwtCitkymUKUNKdebvVAI0y8YvliWTL5S-GiJD9dN8SSsXUla9A4xB_9Mt5JAlRpQotQSCLojVSKQmjhMpQWmYAlKVjnlImoRwQFPI4w3Ijn4G4EMTKWUYRfrD0-WNT9ZYWBeza6QgV6sraP7ToRB3eQLy2p04cU40X-RHLeYCsMBfxsMMh89CJff-9tn7VDKi1hAGc_Lp9yS9ZaItJuFJTjf8S_vsjVB1nBhvdS_6IED_m_fOU52KiGSO2qL6shxHvg
```

And then make this request:

```bash
curl -H "Authorization: Bearer $TOKEN" localhost:8080
```

Which will respond with the phrase:

```bash
Hello, subject!
```

where `subject` is the value of the `sub` field in the JWT returned by the Authorization Server.

Or this to make a GET request to /messages:

```bash
export TOKEN=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzdWJqZWN0IiwiZXhwIjoyMTk4NTkwOTQ4LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiMDJhYzQ3ZTQtZTEyYi00N2Y4LWEzZTEtMjZiZjZhNGYzOGRmIiwiY2xpZW50X2lkIjoicmVhZGVyIiwic2NvcGUiOlsibWVzc2FnZTpyZWFkIl19.gSwMxZ0oVaG7XRggHZh-z6A3tvthMb-G0x-Q2TdpxkDOdywNmo8AapHcbgn7IM-hR8aN_8h2o4IVW8MTtbydM1LgBr_qB2nkKzHiBFfTjykyDwV94jQqkJPZueXhoEh5bV02zz7oF88JhspraCgPcHsvSWmaz2dQC3sZvu90DMDA9DiKiPEpV_AYt8dEl1zelpjvr9V12AWDFb8OkAVSYNnztSWqD_sGtkgIdFYLaB4sWeVDOIVFb5G2Zsi-7tFe1QfWFl293M-lmJ9VNiK_o223wA97jiEKQBegZahJDulA-iXqdtPEvuIW-IXpA6w4rY_dCOtG48Wwvb3K7GxYbg
curl -H "Authorization: Bearer $TOKEN" 127.0.0.1:8084/purchase

```

Will respond with:

```bash
secret message
```

In order to make a POST request to /message, you can use the following request:

```bash
export TOKEN=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJzdWJqZWN0IiwiZXhwIjoyMTY0MjQzOTA0LCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiZGI4ZjgwMzQtM2VlNy00NjBjLTk3NTEtMDJiMDA1OWI5NzA4IiwiY2xpZW50X2lkIjoid3JpdGVyIiwic2NvcGUiOlsibWVzc2FnZTp3cml0ZSJdfQ.USvpx_ntKXtchLmc93auJq0qSav6vLm4B7ItPzhrDH2xmogBP35eKeklwXK5GCb7ck1aKJV5SpguBlTCz0bZC1zAWKB6gyFIqedALPAran5QR-8WpGfl0wFqds7d8Jw3xmpUUBduRLab9hkeAhgoVgxevc8d6ITM7kRnHo5wT3VzvBU8DquedVXm5fbBnRPgG4_jOWJKbqYpqaR2z2TnZRWh3CqL82Orh1Ww1dJYF_fae1dTVV4tvN5iSndYcGxMoBaiw3kRRi6EyNxnXnt1pFtZqc1f6D9x4AHiri8_vpBp2vwG5OfQD5-rrleP_XlIB3rNQT7tu3fiqu4vUzQaEg

curl -H "Authorization: Bearer $TOKEN" -d "my message" localhost:8080/message
```

Will respond this:

```bash
Message was created. Content: my message
```

== 2. Testing against other Authorization Servers

_In order to use this sample, your Authorization Server must support JWTs that either use the "scope" or "scp" attribute._

To change the sample to point at your Authorization Server, simply find this property in the `application.yml`:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: ${mockwebserver.url}/.well-known/jwks.json
```

And change the property to your Authorization Server's JWK set endpoint:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: https://dev-123456.oktapreview.com/oauth2/default/v1/keys
```

And then you can run the app the same as before:

```bash
./gradlew bootRun
```

Make sure to obtain valid tokens from your Authorization Server in order to play with the sample Resource Server.
To use the `/` endpoint, any valid token from your Authorization Server will do.
To use the `/message` endpoint, the token should have the `message:read` scope.
