package com.github.hugovallada.kotest.domain

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.maps.shouldContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import io.kotest.property.checkAll
import org.instancio.Instancio
import java.math.BigDecimal
import java.time.ZonedDateTime

class PersonalTrainerTest : FeatureSpec({
    lateinit var personal: PersonalTrainer

    beforeEach {
        personal = PersonalTrainer("NomeTest", 2, BigDecimal(200))
    }

    feature("a coupon code") {
        scenario("with 10% of discount, than the hour should be 10% less") {
            val sut = personal.getCurrentPrice(true, 10)
            sut shouldBe BigDecimal(180)
        }
        scenario("with 20% of discount, than the hour should be 20% less") {
            val sut = personal.getCurrentPrice(true, 20)
            sut shouldBe BigDecimal(160)
        }
        scenario("with 100% of discount, than the hour should be free") {
            val sut = personal.getCurrentPrice(true, 100)
            sut shouldBe BigDecimal.ZERO
        }
        scenario("with more than 100% of discount, should ignore the coupon and return the full value") {
            val sut = personal.getCurrentPrice(true, 150)
            sut shouldBe personal.getCurrentPrice()
        }
        scenario("with less than 0% of discount, should ignore the coupon and return the full value") {
            val sut = personal.getCurrentPrice(true, -10)
            sut shouldBe personal.getCurrentPrice()
        }
    }

    feature("give discount") {
        scenario("given a discount value between 0 and 100, then return the new price with the discount calculated") {
            checkAll(iterations = 100, Arb.int(0..100)) {
                val sut = personal.giveDiscount(it)
                sut shouldBe BigDecimal((200 - ((200 * it) / 100)))
            }
        }
        scenario("given a discount value that is less than 0, then return the price without modification") {
            checkAll(iterations = 10, Arb.int(-100..-1)) {
                val sut = personal.giveDiscount(it)
                sut shouldBe personal.getCurrentPrice()
            }
        }
        scenario("given a discount value that is bigger than 100, then return the price without modification") {
            checkAll(iterations = 10, Arb.int(101..1000)) {
                val sut = personal.giveDiscount(it)
                sut shouldBe personal.getCurrentPrice()
            }
        }
    }

    feature("changePrice") {
        scenario(
            "When the changePrice is called " +
                "and the price is bigger than 0,then the getCurrentPrice should return the new value"
        ) {
            checkAll(iterations = 50, Arb.int(1..10000)) {
                personal.changePrice(BigDecimal(it))
                personal.getCurrentPrice() shouldBe BigDecimal(it)
            }
        }
        scenario(
            "When the changePrice is called and it's less than 0, then the getCurrentPrice should return the old price"
        ) {
            checkAll(iterations = 50, Arb.int(-1000..-1)) {
                personal.changePrice(BigDecimal(it))
                personal.getCurrentPrice() shouldNotBe BigDecimal(it)
            }
        }
    }

    feature("clients") {
        val client = Instancio.create(Client::class.java)
        scenario("When a client is added, then when the method seeClient is called, client should be there") {
            val newClient = Instancio.create(Client::class.java)
            personal.addClient(client)
            personal.addClient(newClient)
            personal.seeClients() shouldContainAll listOf(client, newClient)
        }
    }

    feature("calendar") {
        val client = Instancio.create(Client::class.java)

        scenario(
            "When a event is added on calendar, " +
                "then when the method see calendar is called, then should return the calendar"
        ) {
            val time = ZonedDateTime.now().plusDays(2)
            personal.addNewEntryOnCalendar(client, time)
            personal.seeCalendar() shouldContain Pair(client.documentNumber, listOf(time))
        }
        scenario(
            "When an event is added on calendar for an client that already has one, " +
                "then should return the calendar with the new time"
        ) {
            val time = ZonedDateTime.now().plusDays(2)
            personal.addNewEntryOnCalendar(client, time)
            personal.addNewEntryOnCalendar(client, time.plusDays(5))
            personal.seeCalendar() shouldContain Pair(client.documentNumber, listOf(time, time.plusDays(5)))
        }
    }

    afterSpec {
        println("Testes do PersonalTrainerTest foram executados com sucesso")
    }
})
