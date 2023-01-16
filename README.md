# Overview

This repo provides a basic GraphQL service for retrieving property details, in particular whether a property has a septic system.

The GraphQL service is meant to connect to HouseCanary's API, but it is not currently configured with authentication
credentials, so two example static responses are available via a local web server for testing the service.

# Running the System

A Docker Compose file is provided that will start two services:  the GraphQL API service and a webserver for
mimicking the response from the real HouseCanary API.  To start both services, run: `docker-compose up`.  Note that the
build time for the API service will take a couple of minutes.

# Accessing the Service

The service is configured with GraphiQL to make testing easier.  It can be accessed at:  http://localhost:8080/graphiql

### To query for property details of a property without septic, run this query:
```graphql
query {
  property(address:"abc",zipcode:"1") {
    roofCover
    roofType
    septic
  }
}
```
The static source for the HouseCanary response is `/pyserver/details-1.json`

### For a property with septic, run:
```graphql
query {
  property(address:"abc",zipcode:"2") {
    roofCover
    roofType
    septic
  }
}
```
(The static source for the HouseCanary response is `/pyserver/details-2.json`)

# Other Considerations

Given the limited scope of this project, some concerns have not been addressed.  Options for future enhancements follow.

## Scalability

The GraphQL service is "shared nothing", that is, there is no state of any kind that would be shared between multiple
instances of the service.  In production, the service could be scaled by adding instances behind a load balancer.

## Security

The current implementation does not offer any access controls.  In production, given the "shared nothing" nature,
authentication and authorization would be configured through the Micronaut framework and implemented using JWTs or
similar tokens.

## Extensibility

The implementation provided here uses a limited subset of the property model provided by HouseCanary.  Depending on
future requirements, additional HouseCanary data could be added or the model might be enhanced with data from
other providers.  The GraphQL implementation allows for this (see implementation of _PropertyDataFetcher_ and
_HousecanaryPropertyDataProvider_); additional providers could be added using the existing _PropertyDataProvider_
interface or using additional interfaces for other types of data.

## Serviceability

The code does not offer any serviceability features as-is, but in a production implementation integration with an
APM tool (e.g. DataDog) would be necessary.

## Testability

The current implementation uses static versions of HouseCanary responses.  This is brittle and would not detect
changes to HouseCanary's API.  A better approach would be to use a sandbox endpoint, if provided, to test against
the actual API in an integration or system test.