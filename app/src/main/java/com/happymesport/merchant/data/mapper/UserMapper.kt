package com.happymesport.merchant.data.mapper

import com.happymesport.merchant.data.dto.UserDto
import com.happymesport.merchant.domain.model.UserModel

fun UserDto.toDomain(): UserModel =
    UserModel(
        mobileNumber = this.mobileNumber,
        uuid = this.uid,
        lastLoginTime = this.lastLoginTime,
        createdAt = this.createdAt,
    )

fun UserModel.toDto(): UserDto =
    UserDto(
        uid = this.uuid,
        mobileNumber = this.mobileNumber,
        createdAt = this.createdAt,
        lastLoginTime = this.lastLoginTime,
    )
