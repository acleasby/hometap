package com.hometap.providers.housecanary

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.DeserializationFeature
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.micronaut.jackson.annotation.JacksonFeatures

@Client("housecanary")
@JacksonFeatures(
    disabledDeserializationFeatures = [DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES]
)
interface HousecanaryClient {
    @Get("/property/details?address={address}&zipcode={zipcode}")
    suspend fun getPropertyDetails(address: String, zipcode: String): PropertyDetails
}

data class PropertyDetails(@JsonProperty("property/details") val details: PropertyDetailsResult)

data class PropertyDetailsResult(
    @JsonProperty("api_code_description") val apiCodeDescription: String,
    @JsonProperty("api_code") val apiCode: Int,
    val result: Result
)

data class Result(val property: Property, val assessment: Assessment)

data class Assessment(val apn: String)

data class Property(
    @JsonProperty("roof_cover")
    val roofCover: String,
    @JsonProperty("roof_type")
    val roofType: String,
    val sewer: Sewer,
)

enum class Sewer {
    @JsonProperty("municipal")
    MUNICIPAL,

    @JsonProperty("none")
    NONE,

    @JsonProperty("storm")
    STORM,

    @JsonProperty("septic")
    SEPTIC,

    @JsonProperty("yes")
    YES
}