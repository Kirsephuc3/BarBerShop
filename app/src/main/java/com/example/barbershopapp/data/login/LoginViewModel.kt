package com.example.barbershopapp.data.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.barbershopapp.app.BarBerShopApp
import com.example.barbershopapp.navigation.BarBerShopAppRoute
import com.example.barbershopapp.navigation.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel()  {
    private val TAG = LoginViewModel::class.simpleName

    var loginUIState = mutableStateOf(LoginUIState())

    var allValidationsPassed = mutableStateOf(false)

    var loginInProgress = mutableStateOf(false)

    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginButtonClicked -> {
                if (allValidationsPassed.value) {
                    login()
                } else {
                    Log.d(TAG, "Validation failed")
                }
            }
        }
        validateData()
    }

    private fun login() {

        loginInProgress.value = true
        val email = loginUIState.value.email
        val password = loginUIState.value.password

        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG,"Inside_login_success")
                Log.d(TAG,"${it.isSuccessful}")

                if(it.isSuccessful){
                    loginInProgress.value = false
                    BarBerShopAppRoute.navigateTo(Screen.HomeScreen)
                }
            }
            .addOnFailureListener {
                Log.d(TAG,"Inside_login_failure")
                Log.d(TAG,"${it.localizedMessage}")

                loginInProgress.value = false

            }

    }


    private fun validateData() {
        val passwordResult = Validator.validatePassword(
            password = loginUIState.value.password
        )
        val mailResult = Validator.validateEmail(
            email = loginUIState.value.email
        )

        Log.d(TAG, "Inside_validateDataWithRules")
        Log.d(TAG, "passwordResult= ${passwordResult}")
        Log.d(TAG, "mailResult= ${mailResult}")

        allValidationsPassed.value = passwordResult.status && mailResult.status

        loginUIState.value = loginUIState.value.copy(
            passwordError = passwordResult.status,
            emailError = mailResult.status
        )
    }

}