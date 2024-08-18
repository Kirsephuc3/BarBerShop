package com.example.barbershopapp.data.firebase

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.barbershopapp.data.user.User
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthRepo {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val uid: String? = auth.currentUser?.uid
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    init {
        if (!uid.isNullOrEmpty()) {
            getUserData()
        }
    }

    private fun getUserData() {
        uid?.let {
            firestore.collection("users").document(it).addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // Handle error
                    return@addSnapshotListener
                }
                _user.value = snapshot?.toObject(User::class.java)
            }
        }
    }

    fun getCurrentUser() : User? {
        return _user.value
    }

    fun logout(){
        auth.signOut()
        BarBerShopAppRoute.navigateTo(Screen.LoginScreen)

    }

    fun checkForActiveSession() {
        if (auth.currentUser != null) {
            Log.d(TAG, "Valid session")
        } else {
            Log.d(TAG, "User is not logged in")
        }
    }

    fun getCurrentUserUid() : String? {
        return auth.currentUser?.uid
    }
}
