package com.example.data.dataSource

import android.util.Log
import com.example.domain.entity.AppUser
import com.example.domain.repository.AuthOnlineDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthOnlineDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFireStore: FirebaseFirestore
) : AuthOnlineDataSource {
    override suspend fun login(
        email: String,
        password: String,
        onSuccess: (uid: String) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            if (result.user != null) {
                onSuccess(result.user?.uid ?: "")
            } else {
                onFailure(Throwable("User is Null"))
            }
        } catch (e: Exception) {
            Log.e("TAG", "login: ${e.message}")
            onFailure(e)
        }
    }

    override suspend fun register(
        user: AppUser,
        password: String,
        onSuccess: (uid: String) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(user.email!!, password).await()
            if (result.user != null) {
                onSuccess(result.user?.uid ?: "")
            } else {
                onFailure(Throwable("User is Null"))
            }
        } catch (e: Exception) {
            Log.e("TAG", "register: ${e.message}")
            onFailure(e)
        }

    }

    override suspend fun addUserToFireStore(
        appUser: AppUser,
        onSuccess: () -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        try {


            val result =
                firebaseFireStore.collection(AppUser.COLLECTION_NAME).document(appUser.uid ?: "")
                    .set(appUser)
                    .await()
            onSuccess()
        } catch (e: Exception) {
            Log.e("TAG", "getUser: ${e.message}")
            onFailure(e)
        }
    }

    override suspend fun getUser(
        uid: String,
        onSuccess: (appUser:AppUser) -> Unit,
        onFailure: (throwable: Throwable) -> Unit
    ) {
        try {
            val result = firebaseFireStore.collection(AppUser.COLLECTION_NAME)
                .document(uid)
                .get()
                .await()
            val user = result.toObject(AppUser::class.java)
            if (user != null)
                onSuccess(user)
            else
                onFailure(Throwable("user==null"))
        } catch (e: Exception) {
            Log.e("TAG", "getUser: ${e.message}")
            onFailure(e)
        }
    }
}