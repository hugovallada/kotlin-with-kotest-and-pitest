package com.github.hugovallada.kotest.domain

import com.github.hugovallada.kotest.shared.Values.MIN_SIZE

@JvmInline
value class ClientDocument(val documentNumber: String) {
    init {
        check(documentNumber.length > MIN_SIZE) { "The document is invalid" }
    }
}
