package com.happymesport.merchant.data.auth

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FirebaseAuthDataSource
    @Inject
    constructor(
        private val firebaseAuth: FirebaseAuth,
    ) {
//        suspend fun signInWithGoogle(idToken: String): UserModel? =
//            withContext(Dispatchers.IO) {
//                val credential = GoogleAuthProvider.getCredential(idToken, null)
//                val authResult = firebaseAuth.signInWithCredential(credential).await()
//                authResult.user?.let {
//                    UserModel(
//                        uuid = it.uid,
//                        name = "${it.displayName}",
//                        url = it.photoUrl.toString(),
//                        email = "${it.email}",
//                        phone = "${it.phoneNumber}",
//                    )
//                }
//            }

//        suspend fun signInWithFacebook(idToken: String): UserModel? =
//            withContext(Dispatchers.IO) {
//                val credential = FacebookAuthProvider.getCredential(idToken)
//                val authResult = firebaseAuth.signInWithCredential(credential).await()
//                authResult.user?.let {
//                    UserModel(
//                        uuid = it.uid,
//                        name = "${it.displayName}",
//                        url = it.photoUrl.toString(),
//                        email = "${it.email}",
//                        phone = "${it.phoneNumber}",
//                    )
//                }
//            }

        suspend fun loginWithMobileNumber(
            phoneNumber: String,
            onVerificationCompleted: (PhoneAuthCredential) -> Unit,
            onVerificationFailed: (FirebaseException) -> Unit,
            onCodeSent: (verificationId: String) -> Unit,
            activity: Activity,
        ) {
            withContext(Dispatchers.IO) {
                val options =
                    PhoneAuthOptions
                        .newBuilder(firebaseAuth)
                        .setActivity(activity)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setCallbacks(
                            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                                    Timber.e("LOGIN  onVerificationCompleted $credential ")
                                    onVerificationCompleted(credential)
                                }

                                override fun onVerificationFailed(e: FirebaseException) {
                                    Timber.e("LOGIN  onVerificationFailed ${e.message} ")
                                    onVerificationFailed(e)
                                }

                                override fun onCodeSent(
                                    verificationId: String,
                                    token: PhoneAuthProvider.ForceResendingToken,
                                ) {
                                    Timber.e("LOGIN  verificationId $verificationId ")
                                    onCodeSent(verificationId)
                                }
                            },
                        ).build()

                PhoneAuthProvider.verifyPhoneNumber(options)
            }
        }

        suspend fun verifyOtpCode(
            verificationId: String,
            code: String,
            onSuccess: () -> Unit,
            onError: (String) -> Unit,
        ) {
            withContext(Dispatchers.IO) {
                val credential = PhoneAuthProvider.getCredential(verificationId, code)
                firebaseAuth
                    .signInWithCredential(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            onSuccess()
                        } else {
                            onError("${it.exception?.localizedMessage}")
                        }
                    }
            }
        }
    }
