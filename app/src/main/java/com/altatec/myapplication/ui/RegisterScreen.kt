package com.altatec.myapplication.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.altatec.myapplication.R
import com.altatec.myapplication.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navigateToLogin: () -> Unit) {

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
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var confirmPasswordSuppText by remember { mutableStateOf("") }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }
    var registerEnabled by remember { mutableStateOf(false) }

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
                    IconButton(onClick = {
                        navigateToLogin()
                    }) {
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .height(16.dp)
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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
                    val toggle = if (confirmPasswordVisibility)
                        R.drawable.baseline_visibility_24
                    else
                        R.drawable.baseline_visibility_off_24
                    IconButton(
                        onClick = {
                            confirmPasswordVisibility = confirmPasswordVisibility != true
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = toggle),
                            contentDescription = if (confirmPasswordVisibility) "Hide password"
                            else "Show password"
                        )
                    }
                },
                supportingText = {
                    Text(text = confirmPasswordSuppText)
                },
                isError = confirmPasswordError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                maxLines = 1,
            )
            Spacer(
                modifier = Modifier
                    .height(96.dp)
            )
            Button(
                onClick = {
                    /*TODO register*/
                },
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = registerEnabled
            ) {
                Text(text = "REGISTER")
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RegisterPrev() {
    AppTheme {
        RegisterScreen {}
    }
}