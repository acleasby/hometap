package com.hometap.providers.housecanary

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.micronaut.test.extensions.kotest.annotation.MicronautTest

@MicronautTest
class HousecanaryClientSpecs(
    val client: HousecanaryClient
) : ShouldSpec({
    should("parse the response") {
        val details = client.getPropertyDetails("", "1")
        details shouldNotBe null
        with(details.details.result.property) {
            roofCover shouldBe "Slate"
            roofType shouldBe "Wood truss"
            sewer shouldBe Sewer.MUNICIPAL
        }
    }
})