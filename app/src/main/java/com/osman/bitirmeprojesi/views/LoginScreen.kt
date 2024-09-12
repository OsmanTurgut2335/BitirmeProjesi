package com.osman.bitirmeprojesi.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, loginScreenViewModel: LoginScreenViewModel) {
    var tfUsername = remember { mutableStateOf("") }
    var tfPassword = remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) } // State to control password visibility
    
    // Observe the login result from the ViewModel
    val loginResult by loginScreenViewModel.loginResult.observeAsState()

    // Snackbar state
    val snackbarHostState = remember { androidx.compose.material3.SnackbarHostState() }

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Giriş Ekranına Hoşgeldiniz") }) },
        snackbarHost = { androidx.compose.material3.SnackbarHost(snackbarHostState) }

    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "adsdsa")
            TextField(
                colors = TextFieldDefaults.textFieldColors(containerColor = colorResource(id = R.color.logintfColor)),
                value = tfUsername.value,
                onValueChange = { tfUsername.value = it },
                label = { Text(text = "E mail  ") }
            )

            TextField(
                colors = TextFieldDefaults.textFieldColors(containerColor = colorResource(id = R.color.logintfColor)),
                value = tfPassword.value,
                onValueChange = { tfPassword.value = it },
                label = { Text(text = "Parola ") },
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



            Button(onClick = {
                loginScreenViewModel.login(tfUsername.value, tfPassword.value)
            }) {
                Text(text = "Giriş Yap")
            }

            // Handle the result
            loginResult?.let { result ->
                when {
                    result.isSuccess -> {
                        // Navigate to another screen on successful login
                        navController.navigate("homeScreen")
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
