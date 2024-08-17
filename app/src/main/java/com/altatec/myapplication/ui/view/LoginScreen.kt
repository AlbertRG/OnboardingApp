package com.altatec.myapplication.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.altatec.myapplication.R
import com.altatec.myapplication.ui.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    navigateToRegister: () -> Unit,
    navigateToScaffold: () -> Unit
) {

    val context = LocalContext.current
    val versionName = if (LocalInspectionMode.current) {
        "Demo"
    } else {
        remember {
            context.packageManager
                .getPackageInfo(context.packageName, 0).versionName
        }
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val emailSuppText by loginViewModel::emailSuppText
    val emailError by loginViewModel::emailError
    val passwordSuppText by loginViewModel::passwordSuppText
    val passwordError by loginViewModel::passwordError
    val goToScaffold by loginViewModel::goToScaffold

    LaunchedEffect(goToScaffold) {
        if (goToScaffold) {
            navigateToScaffold()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = R.drawable.android_logo),
            contentDescription = "Android_Logo"
        )
        Card(
            elevation = CardDefaults.cardElevation(24.dp),
            border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Login",
                    modifier = Modifier
                        .padding(vertical = 16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { newText ->
                        email = newText
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(text = "Email")
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "Email"
                        )
                    },
                    supportingText = {
                        Text(
                            text = emailSuppText
                        )
                    },
                    isError = emailError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    maxLines = 1
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { newText ->
                        if (newText.length <= 10) {
                            password = newText
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(text = "Password")
                    },
                    trailingIcon = {
                        val toggle = if (passwordVisibility)
                            R.drawable.baseline_visibility_24
                        else
                            R.drawable.baseline_visibility_off_24
                        IconButton(
                            onClick = {
                                passwordVisibility = !passwordVisibility
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = toggle),
                                contentDescription = if (passwordVisibility) "Hide password"
                                else "Show password"
                            )
                        }
                    },
                    supportingText = {
                        Row {
                            Text(passwordSuppText)
                            Spacer(Modifier.weight(1f))
                            Text("${password.length}/10")
                        }
                    },
                    isError = passwordError,
                    visualTransformation = if (passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    maxLines = 1,
                )
                Button(
                    onClick = {
                        loginViewModel.onLoginClick(email, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    enabled = areAllFieldsFilled(email, password)
                ) {
                    Text(text = "LOGIN")
                }
            }
        }
        Spacer(
            modifier = Modifier
                .height(80.dp)
        )
        Button(
            onClick = {
                navigateToRegister()
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "REGISTER")
        }
        Text(text = "v.$versionName")
    }
}

fun areAllFieldsFilled(
    email: String,
    password: String
): Boolean {
    return email.isNotBlank() && password.isNotBlank()
}