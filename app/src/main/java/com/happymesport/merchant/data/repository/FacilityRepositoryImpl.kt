package com.happymesport.merchant.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.happymesport.merchant.data.dto.FacilityDto
import com.happymesport.merchant.domain.repository.FacilityRepository
import com.happymesport.merchant.utils.FirestoreCollections
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

class FacilityRepositoryImpl
    @Inject
    constructor(
        private val firestore: FirebaseFirestore,
    ) : FacilityRepository {
        private fun getFacilityDocumentRef() =
            firestore
                .collection(FirestoreCollections.FACILITY)

        override suspend fun getFacilityByUserId(uid: String): FacilityDto? {
            try {
                val documentSnapshot = getFacilityDocumentRef().document(uid).get().await()
                if (documentSnapshot.exists()) {
                    val facilityDto = documentSnapshot.toObject(FacilityDto::class.java)
                    return facilityDto
                } else {
                    return null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Timber.e("check error: ${e.message}")
                throw e
            }
        }
    }
