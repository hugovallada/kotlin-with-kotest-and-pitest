package com.github.hugovallada.kotest.domain

import com.github.hugovallada.kotest.extension.percentage
import java.math.BigDecimal
import java.time.ZonedDateTime

typealias ClientClasses = MutableList<ZonedDateTime>

data class PersonalTrainer(
	val name: String,
	val experienceInMonths: Int,
	private var hourPrice: BigDecimal,
	private val clients: MutableSet<Client> = mutableSetOf(),
	private val calendar: MutableMap<ClientDocument, ClientClasses> = mutableMapOf()
) {
	fun changePrice(newPrice: BigDecimal) {
		if (newPrice.toInt() > 0) hourPrice = newPrice
	}

	fun giveDiscount(discountPercentage: Int): BigDecimal = if (discountPercentage in 0..100) {
		hourPrice - hourPrice.percentage(discountPercentage)
	} else {
		hourPrice
	}

	fun getCurrentPrice(couponCode: Boolean = false, discount: Int = 0) = if (couponCode && discount in 0..100) {
		giveDiscount(discount)
	} else {
		hourPrice
	}

	fun addClient(client: Client) {
		clients.add(client)
	}

	fun seeClients(): Set<Client> = clients

	fun addNewEntryOnCalendar(client: Client, time: ZonedDateTime) {
		if (calendar[client.documentNumber] == null) {
			calendar[client.documentNumber] = mutableListOf(time)
			return
		}
		calendar[client.documentNumber]?.add(time)
	}

	fun seeCalendar(): Map<ClientDocument, List<ZonedDateTime>> = calendar
}



