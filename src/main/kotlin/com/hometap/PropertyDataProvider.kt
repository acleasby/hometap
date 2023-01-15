package com.hometap

import com.hometap.model.Property

interface PropertyDataProvider {
    suspend fun getPropertyDetails(address: String, zipcode: String): Property?
}