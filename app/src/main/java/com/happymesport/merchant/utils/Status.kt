package com.happymesport.merchant.utils

enum class Status(
    val statusName: String,
) {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    PENDING("Pending"),
    SUSPENDED("Suspended"),
    STAGE_01("STAGE_01"),
    STAGE_02("STAGE_02"),
    STAGE_03("STAGE_03");

    override fun toString(): String = statusName
}
