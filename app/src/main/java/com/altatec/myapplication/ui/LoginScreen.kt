package com.altatec.myapplication.ui

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.altatec.myapplication.R
import com.altatec.myapplication.ui.theme.AppTheme

@Composable
fun LoginScreen(navigateToRegister: () -> Unit, navigateToScaffold: () -> Unit) {

    var email by remember { mutableStateOf("") }
    var emailSuppText by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var passwordSuppText by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    var passwordVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = R.drawable.android_logo),
            contentDescription = "Android Logo"
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
                    maxLines = 1,
                    label = {
                        Text(text = "Email")
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = "Password"
                        )
                    },
                    supportingText = {
                        Text(text = emailSuppText)
                    },
                    isError = emailError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { newText ->
                        password = newText
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    maxLines = 1,
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
                                passwordVisibility = passwordVisibility != true
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
                        Text(text = passwordSuppText)
                    },
                    isError = passwordError,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Button(
                    onClick = {
                        /*TODO Check credentials*/
                        navigateToScaffold()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
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
        Text(text = "v.2.0")
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginPrev() {
    AppTheme {
        LoginScreen({},{})
    }
}