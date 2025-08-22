package com.happymesport.merchant.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.happymesport.merchant.data.dto.AvailableFacility
import com.happymesport.merchant.domain.repository.CommonSettingsRepository
import com.happymesport.merchant.utils.FirestoreCollections
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CommonSettingsRepositoryImpl
    @Inject
    constructor(
        private val firestore: FirebaseFirestore,
    ) : CommonSettingsRepository {
        private fun getFacilityDocumentRef() =
            firestore
                .collection(FirestoreCollections.COMMON_SETTINGS)

        override suspend fun getAvailableFacilities(): List<AvailableFacility> {
            val snapshot =
                runCatching {
                    firestore
                        .collection(FirestoreCollections.COMMON_SETTINGS)
                        .document(FirestoreCollections.AVAILABLE_SETTINGS)
                        .get()
                        .await()
                }.onFailure {
                    throw it
                }.getOrNull() ?: return emptyList()

            val facilityList = snapshot.get("list") as? List<*> ?: return emptyList()

            return facilityList.mapNotNull { item ->
                (item as? Map<*, *>)?.let { map ->
                    AvailableFacility(
                        name = map["name"] as? String,
                        description = map["description"] as? String,
                        icon = map["icon"] as? String,
                    )
                }
            }
        }
    }
