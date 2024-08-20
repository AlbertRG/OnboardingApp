package com.altatec.myapplication.ui.view

import android.util.Patterns
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.altatec.myapplication.R
import com.altatec.myapplication.data.local.entity.User
import com.altatec.myapplication.ui.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    registerViewModel: RegisterViewModel,
    navigateToLogin: () -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val snackHostState = remember { SnackbarHostState() }
    var snackMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var emailSuppText by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var passwordSuppText by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPassword by remember { mutableStateOf("") }
    var confirmPasswordSuppText by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    fun resetFields() {
        name = ""
        email = ""
        emailSuppText = ""
        emailError = false
        password = ""
        passwordSuppText = ""
        passwordError = false
        passwordVisibility = false
        confirmPassword = ""
        confirmPasswordSuppText = ""
        confirmPasswordError = false
        confirmPasswordVisibility = false
    }

    fun areAllFieldsFilled(): Boolean =
        name.isNotBlank() && email.isNotBlank()
                && password.isNotBlank() && confirmPassword.isNotBlank()

    fun areNoError(): Boolean =
        (!emailError && !passwordError && !confirmPasswordError)

    fun isValidEmail(email: String): Boolean =
        email.isEmpty() || Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String): Boolean =
        password.isEmpty() || password.length >= 5

    fun isValidConfirmPassword(password: String, confirmPassword: String): Boolean =
        confirmPassword.isEmpty() || password == confirmPassword

    fun showSnackbar(message: String) {
        scope.launch {
            snackHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

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
                            contentDescription = "Back Navigation"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        snackbarHost = { SnackbarHost(snackHostState) }
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
                    if (newText.length <= 15) {
                        name = newText
                    }
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
                    Row {
                        Spacer(Modifier.weight(1f))
                        Text("${name.length}/15")
                    }
                },
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
                    Text(
                        text = emailSuppText
                    )
                },
                isError = if (isValidEmail(email)) {
                    emailSuppText = ""
                    emailError = false
                    false
                } else {
                    emailSuppText = "Please enter a valid email"
                    emailError = true
                    true
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                maxLines = 1,
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
                isError = if (isValidPassword(password)) {
                    passwordSuppText = ""
                    passwordError = false
                    false
                } else {
                    passwordSuppText = "Please enter a valid password"
                    passwordError = true
                    true
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                maxLines = 1
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { newText ->
                    if (newText.length <= 10) {
                        confirmPassword = newText
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = !passwordError && password.isNotEmpty(),
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
                            confirmPasswordVisibility = !confirmPasswordVisibility
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
                    Row {
                        Text(confirmPasswordSuppText)
                        Spacer(Modifier.weight(1f))
                        Text("${confirmPassword.length}/10")
                    }
                },
                isError = if (isValidConfirmPassword(password, confirmPassword)) {
                    confirmPasswordSuppText = ""
                    confirmPasswordError = false
                    false
                } else {
                    confirmPasswordSuppText = "Confirm password is not equal to password"
                    confirmPasswordError = true
                    true
                },
                visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                maxLines = 1,
            )
            Spacer(
                modifier = Modifier
                    .height(96.dp)
            )
            Button(
                onClick = {
                    try {
                        val user =
                            User(email = email, name = name, password = password)
                        registerViewModel.insertUser(user)
                        snackMessage = "User Added Successfully"
                        resetFields()
                    } catch (e: Exception) {
                        snackMessage = "Something went wrong"
                    }
                    showSnackbar(snackMessage)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = areAllFieldsFilled() && areNoError()
            ) {
                Text(text = "REGISTER")
            }
        }
    }
}