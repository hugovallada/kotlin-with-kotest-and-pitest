package com.github.hugovallada.kotest.extension

import com.github.hugovallada.kotest.shared.Values.PERCENTAGE_MAX
import java.math.BigDecimal

fun BigDecimal.percentage(percentage: Int): BigDecimal =
    multiply(percentage.toBigDecimal()).divide(BigDecimal(PERCENTAGE_MAX))
