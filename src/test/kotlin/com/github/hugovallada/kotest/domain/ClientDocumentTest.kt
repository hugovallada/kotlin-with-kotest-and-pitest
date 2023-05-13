package com.github.hugovallada.kotest.domain

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ClientDocumentTest : BehaviorSpec({
    given("A ClientDocument") {
        `when`("It is created with less than 10 digits") {
            then("It should thrown an IllegalStateException") {
                shouldThrowExactly<IllegalStateException> { ClientDocument("123") }
            }
        }
        `when`("It is created with more than 10 digits") {
            val documentNumber = "09129399201092"
            then("It should not thrown any exception") {
                val sut = shouldNotThrowAny {
                    ClientDocument(documentNumber)
                }
                sut.documentNumber shouldBe documentNumber
            }
        }
    }
})

