package com.altatec.myapplication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen() {

    var email by remember { mutableStateOf("") }
    var emailSuppText by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var passwordSuppText by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //Image(painter = , contentDescription = )
        Text(
            text = "Login",
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
            supportingText = {
                Text(text = emailSuppText)
            },
            isError = emailError
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
            supportingText = {
                Text(text = passwordSuppText)
            },
            isError = passwordError
        )
        Button(
            onClick = {
                /*TODO*/
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "LOGIN")
        }
        Button(
            onClick = {
                /*TODO*/
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "REGISTER")
        }
    }
}

@Preview (showBackground = true)
@Composable
fun LoginPrev() {
    LoginScreen()
}