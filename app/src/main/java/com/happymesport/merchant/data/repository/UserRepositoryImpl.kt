package com.happymesport.merchant.data.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.happymesport.merchant.common.Resources
import com.happymesport.merchant.data.dto.UserDto
import com.happymesport.merchant.data.mapper.toDomain
import com.happymesport.merchant.data.mapper.toDto
import com.happymesport.merchant.domain.model.UserModel
import com.happymesport.merchant.domain.repository.UserRepository
import com.happymesport.merchant.utils.FirestoreCollections
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl
    @Inject
    constructor(
        private val firestore: FirebaseFirestore,
    ) : UserRepository {
        private fun getUserProfileDocumentRef(uid: String) =
            firestore
                .collection(FirestoreCollections.OWNERS)
                .document(uid)

        override suspend fun getUser(uid: String): Resources<UserModel?> =
            try {
                val documentSnapshot = getUserProfileDocumentRef(uid).get().await()
                if (documentSnapshot.exists()) {
                    val userDto = documentSnapshot.toObject(UserDto::class.java)
                    Resources.Success(userDto?.toDomain())
                } else {
                    Resources.Success(null) // User not found
                }
            } catch (e: Exception) {
                Resources.Error("Failed to get user: ${e.localizedMessage}")
            }

        override suspend fun addUser(user: UserModel): Resources<Unit> =
            try {
                val userDto = user.toDto()
                getUserProfileDocumentRef(user.uuid).set(userDto).await()
                Resources.Success(Unit)
            } catch (e: Exception) {
                Resources.Error("Failed to add user: ${e.localizedMessage}")
            }

        override suspend fun updateLastLoginTime(
            uid: String,
            timestamp: Timestamp,
        ): Resources<Unit> =
            try {
                val updates = mapOf(FirestoreCollections.lastLoginTime to timestamp)
                getUserProfileDocumentRef(uid).set(updates, SetOptions.merge()).await()
                Resources.Success(Unit)
            } catch (e: Exception) {
                Resources.Error("Failed to update last login time: ${e.localizedMessage}")
            }
    }
