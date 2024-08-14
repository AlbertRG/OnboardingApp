package com.altatec.myapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    var name by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }
    var nameSuppText by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var emailSuppText by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    var passwordSuppText by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var confirmPasswordSuppText by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Register",
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "backNavigation"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )
            Text(
                text = "Register New Account",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(
                modifier = Modifier
                    .height(40.dp)
            )
            OutlinedTextField(
                value = name,
                onValueChange = { newText ->
                    name = newText
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "Name")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Create,
                        contentDescription = "Name"
                    )
                },
                supportingText = {
                    Text(text = nameSuppText)
                },
                isError = nameError,
                maxLines = 1
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
                    Text(text = emailSuppText)
                },
                isError = emailError,
                maxLines = 1,
            )
            OutlinedTextField(
                value = password,
                onValueChange = { newText ->
                    password = newText
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "Password")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Password"
                    )
                },
                supportingText = {
                    Text(text = passwordSuppText)
                },
                isError = passwordError,
                maxLines = 1
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { newText ->
                    confirmPassword = newText
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "Confirm Password")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Password"
                    )
                },
                supportingText = {
                    Text(text = confirmPasswordSuppText)
                },
                isError = confirmPasswordError,
                maxLines = 1,
            )
            Spacer(
                modifier = Modifier
                    .height(80.dp)
            )
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
}

@Preview
@Composable
fun RegisterPrev() {
    RegisterScreen()
}