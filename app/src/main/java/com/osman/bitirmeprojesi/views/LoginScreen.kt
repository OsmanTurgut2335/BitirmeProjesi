package com.osman.bitirmeprojesi.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.osman.bitirmeprojesi.views.customviews.AnimatedPreloader
import com.osman.bitirmeprojesi.views.customviews.TopBarText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, loginScreenViewModel: LoginScreenViewModel) {
    val tfUsername = remember { mutableStateOf("") }
    val tfPassword = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val auth = remember { FirebaseAuth.getInstance() }
    val context = LocalContext.current

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
        topBar = { TopAppBar(title ={ Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Giriş Ekranı")
        } },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.White // Set the TopAppBar background color
            ) ) },
        snackbarHost = { androidx.compose.material3.SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues).background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Add the animated preloader at the top
            AnimatedPreloader(
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 16.dp)
                    .background(Color.White),
                animationResId = R.raw.login_screen_animation
            )

            Spacer(modifier = Modifier.padding(top = 50.dp))
            Column {
                TextField(
                    colors = TextFieldDefaults.textFieldColors(containerColor = colorResource(id = R.color.custombuttonText),
                        focusedIndicatorColor =colorResource(id = R.color.buttonBackground),
                        unfocusedIndicatorColor = colorResource(id = R.color.buttonBackground)),
                    value = tfUsername.value,
                    onValueChange = { tfUsername.value = it },
                    label = { Text(text = "E-mail") }
                )
                Spacer(modifier = Modifier.padding(top = 30.dp))

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
            }
            Spacer(modifier = Modifier.padding(top = 30.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = {  loginScreenViewModel.login(tfUsername.value, tfPassword.value) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.custombuttonText),  // Color inside the circle
                        contentColor = colorResource(id = R.color.buttonBackground)    // Text color
                    ),
                    shape = CircleShape,

                    ) {
                    Text(text = "Giriş Yap")
                }
                Spacer(modifier = Modifier.padding(top = 6.dp))
                Button(
                    onClick = {

                        auth.createUserWithEmailAndPassword(tfUsername.value, tfPassword.value)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {

                                    Toast.makeText(context, "Kullanıcı başarıyla oluşturuldu!", Toast.LENGTH_SHORT).show()
                                } else {

                                    Toast.makeText(context, "Kullanıcı oluşturma başarısız", Toast.LENGTH_SHORT).show()
                                }
                            }

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.custombuttonText),  // Color inside the circle
                        contentColor = colorResource(id = R.color.buttonBackground)    // Text color
                    ),
                    shape = CircleShape,

                    ) {
                    Text(text = "Kayıt Ol")
                }

            }




            // Handle the result
            loginResult?.let { result ->
                when {
                    result.isSuccess -> {
                        navController.navigate("homeScreen") {
                            popUpTo(navController.graph.startDestinationId) {

                                inclusive = true
                            }
                        }


                        loginScreenViewModel.clearLoginResult()
                    }
                    result.isFailure -> {
                        LaunchedEffect(snackbarHostState) {
                            snackbarHostState.showSnackbar(
                                message = "Giriş Başarısız.Lütfen bilgilerinizi kontrol ederek tekrar deneyin",
                                duration = androidx.compose.material3.SnackbarDuration.Short
                            )
                        }
                    }
                }
            }


        }
    }
}