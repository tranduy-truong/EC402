package com.tranduytruong.novatech.util

import java.text.NumberFormat
import java.util.Locale

fun formatMoney(value: Long): String =
    NumberFormat.getCurrencyInstance(Locale("vi", "VN")).format(value)
