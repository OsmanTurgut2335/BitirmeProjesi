package com.osman.bitirmeprojesi.views

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.osman.bitirmeprojesi.R
import com.osman.bitirmeprojesi.viewmodels.LoginScreenViewModel
import kotlin.math.log

import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp
import com.osman.bitirmeprojesi.views.customviews.TopBarText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, loginScreenViewModel: LoginScreenViewModel) {
    val tfUsername = remember { mutableStateOf("") }
    val tfPassword = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Observe the login result from the ViewModel
    val loginResult by loginScreenViewModel.loginResult.observeAsState()

    // Snackbar state
    val snackbarHostState = remember { androidx.compose.material3.SnackbarHostState() }

    // Check if the user is already logged in (only run on first composition)
    LaunchedEffect(key1 = true) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            // If user is logged in, navigate directly to the home screen
            navController.navigate("homeScreen") {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { TopBarText(title = "Giriş Ekranı") }) },
        snackbarHost = { androidx.compose.material3.SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                colors = TextFieldDefaults.textFieldColors(containerColor = colorResource(id = R.color.custombuttonText),
                    focusedIndicatorColor =colorResource(id = R.color.buttonBackground),
                    unfocusedIndicatorColor = colorResource(id = R.color.buttonBackground)),
                value = tfUsername.value,
                onValueChange = { tfUsername.value = it },
                label = { Text(text = "E-mail") }
            )

            TextField(
                colors = TextFieldDefaults.textFieldColors(containerColor = colorResource(id = R.color.custombuttonText),
                    focusedIndicatorColor =colorResource(id = R.color.buttonBackground),
                    unfocusedIndicatorColor = colorResource(id = R.color.buttonBackground)),
                value = tfPassword.value,
                onValueChange = { tfPassword.value = it },
                label = { Text(text = "Parola") },
                visualTransformation = if (passwordVisible) androidx.compose.ui.text.input.VisualTransformation.None
                else androidx.compose.ui.text.input.PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = if (passwordVisible) painterResource(id = R.drawable.baseline_visibility_24)
                            else painterResource(id = R.drawable.baseline_visibility_off_24),
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            )


            Button(
                onClick = {  loginScreenViewModel.login(tfUsername.value, tfPassword.value) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.custombuttonText),  // Color inside the circle
                    contentColor = colorResource(id = R.color.buttonBackground)    // Text color
                ),
                shape = CircleShape,  // Makes the button circular
             
            ) {
                Text(text = "Giriş Yap")
            }


            // Handle the result
            loginResult?.let { result ->
                when {
                    result.isSuccess -> {
                        // Navigate to another screen on successful login
                        navController.navigate("homeScreen") {
                            popUpTo(navController.graph.startDestinationId) {

                                inclusive = true
                            }
                        }


                        loginScreenViewModel.clearLoginResult()
                    }
                    result.isFailure -> {
                        // Show error message
                        androidx.compose.runtime.LaunchedEffect(snackbarHostState) {
                            snackbarHostState.showSnackbar(
                                message = "Login failed: ${result.exceptionOrNull()?.localizedMessage ?: "Unknown error"}",
                                duration = androidx.compose.material3.SnackbarDuration.Short
                            )
                        }
                    }
                }
            }


        }
    }
}

