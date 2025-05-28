package com.happymesport.merchant.common.extention

fun String.isValidSriLankaPhoneNumber(): Boolean {
    val sriLankanPhoneNumberRegex = Regex("^(07\\d{8})$")
    return this.matches(sriLankanPhoneNumberRegex)
}
