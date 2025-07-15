package com.happymesport.merchant.common.extention

import android.util.Patterns

fun String.isValidSriLankaPhoneNumber(): Boolean {
    val sriLankanPhoneNumberRegex = Regex("^(0?7\\d{8})$")
    return this.matches(sriLankanPhoneNumberRegex)
}
fun String.isValidEmail(): Boolean {
    return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
