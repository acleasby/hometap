package com.hometap.providers.housecanary

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotest.MicronautKotestExtension.getMock
import io.micronaut.test.extensions.kotest.annotation.MicronautTest
import io.mockk.coEvery
import io.mockk.mockk


@MicronautTest
class HousecanaryPropertyDataProviderSpecs(
    private val client: HousecanaryClient,
    private val provider: HousecanaryPropertyDataProvider,
) : ShouldSpec({
    should("map septic sewer type correctly") {
        coEvery { getMock(client).getPropertyDetails(any(), any()) } returns PropertyDetails(
            PropertyDetailsResult(
                "", 0, Result(
                    assessment = Assessment(""),
                    property = Property(
                        roofCover = "Asphalt",
                        roofType = "Wood truss",
                        sewer = Sewer.SEPTIC
                    )
                )
            )
        )

        provider.getPropertyDetails("", "")!!.septic shouldBe true
    }
}) {
    @MockBean(HousecanaryClient::class)
    fun mockClient() = mockk<HousecanaryClient>()
}