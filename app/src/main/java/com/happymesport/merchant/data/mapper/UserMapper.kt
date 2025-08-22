package com.happymesport.merchant.data.mapper

import com.happymesport.merchant.data.dto.UserDto
import com.happymesport.merchant.domain.model.UserModel
import com.happymesport.merchant.utils.Utils

fun UserDto.toDomain(): UserModel =
    UserModel(
        mobileNumber = this.mobileNumber,
        uuid = this.uid,
        lastLoginTime = this.lastLoginTime?.toDate().toString(),
        createdAt = this.createdAt,
        imageUrl = this.imageUrl,
        name = this.name,
        dob = this.dateOfBirth,
        email = this.email,
    )

fun UserModel.toDto(): UserDto =
    UserDto(
        uid = this.uuid,
        mobileNumber = this.mobileNumber,
        createdAt = this.createdAt,
        lastLoginTime =
            Utils.convertStringToTimestamp(
                this.lastLoginTime ?: "",
                "yyyy-MM-dd HH:mm:ss",
            ),
        imageUrl = this.imageUrl,
        name = this.name,
        dateOfBirth = this.dob,
        email = this.email,
    )
