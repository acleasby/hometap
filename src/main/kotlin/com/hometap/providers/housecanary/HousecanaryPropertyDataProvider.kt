package com.hometap.providers.housecanary

import com.hometap.PropertyDataProvider
import com.hometap.model.Property
import jakarta.inject.Singleton

@Singleton
class HousecanaryPropertyDataProvider(
    private val client: HousecanaryClient
) : PropertyDataProvider {
    override suspend fun getPropertyDetails(address: String, zipcode: String): Property? {
        val details = client.getPropertyDetails(address, zipcode)
        return with(details?.details?.result?.property) {
            Property(
                roofCover = roofCover,
                roofType = roofType,
                septic = sewer == Sewer.SEPTIC
            )
        }
    }
}