package com.github.hugovallada.kotest.domain

@JvmInline
value class ClientDocument(val documentNumber: String) {
	init {
		check(documentNumber.length > 10) { "O documentNumber não está com os padrões corretos" }
	}
}