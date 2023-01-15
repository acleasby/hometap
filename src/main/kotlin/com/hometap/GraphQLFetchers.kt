package com.hometap

import com.hometap.model.Property
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton
import kotlinx.coroutines.runBlocking

@Singleton
class PropertyDataFetcher(
    private val propertyDataProvider: PropertyDataProvider
) : DataFetcher<Property> {
    override fun get(environment: DataFetchingEnvironment): Property? {
        val address = environment.getArgument<String>("address")
        val zipcode = environment.getArgument<String>("zipcode")

        return runBlocking {
            propertyDataProvider.getPropertyDetails(address, zipcode)
        }
    }
}