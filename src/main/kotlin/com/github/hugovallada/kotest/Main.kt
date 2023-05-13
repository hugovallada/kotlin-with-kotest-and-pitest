package com.github.hugovallada.kotest

import com.github.hugovallada.kotest.domain.Client
import com.github.hugovallada.kotest.domain.ClientDocument
import com.github.hugovallada.kotest.domain.PersonalTrainer
import java.math.BigDecimal
import java.time.ZonedDateTime

fun main() {
	val client = Client("José", ClientDocument("1928882328"))
	val personal = PersonalTrainer("Hugo", 1, BigDecimal(200))

	personal.addClient(client)
	personal.addNewEntryOnCalendar(client, ZonedDateTime.now().plusDays(2))
	personal.addNewEntryOnCalendar(client, ZonedDateTime.now().plusDays(3))

	println(personal.seeClients())
	println(personal.seeCalendar())
}