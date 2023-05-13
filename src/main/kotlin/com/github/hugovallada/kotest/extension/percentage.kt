package com.github.hugovallada.kotest.extension

import java.math.BigDecimal

fun BigDecimal.percentage(percentage: Int): BigDecimal {
	return multiply(percentage.toBigDecimal()).divide(BigDecimal(100))
}
