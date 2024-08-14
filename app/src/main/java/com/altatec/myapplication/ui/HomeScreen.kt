package com.altatec.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    var name by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }
    var nameSuppText by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var birthdayError by remember { mutableStateOf(false) }
    var birthdaySuppText by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var addressError by remember { mutableStateOf(false) }
    var addressSuppText by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf(false) }
    var phoneSuppText by remember { mutableStateOf("") }
    var hobbies by remember { mutableStateOf("") }
    var hobbiesError by remember { mutableStateOf(false) }
    var hobbiesSuppText by remember { mutableStateOf("") }

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
                        "Home",
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
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
            IconButton(
                onClick = {
                /*TODO*/
                },
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
            ) {
                Image(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "Photo",
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp)
                )
            }
            Spacer(
                modifier = Modifier
                    .height(8.dp)
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
                        imageVector = Icons.Filled.Face,
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
                value = birthday,
                onValueChange = { newText ->
                    birthday = newText
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "Birthday")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Email"
                    )
                },
                supportingText = {
                    Text(text = birthdaySuppText)
                },
                isError = birthdayError,
                maxLines = 1,
            )
            OutlinedTextField(
                value = address,
                onValueChange = { newText ->
                    address = newText
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "Address")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Password"
                    )
                },
                supportingText = {
                    Text(text = addressSuppText)
                },
                isError = addressError,
                maxLines = 1
            )
            OutlinedTextField(
                value = phone,
                onValueChange = { newText ->
                    phone = newText
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "Phone")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Call,
                        contentDescription = "Password"
                    )
                },
                supportingText = {
                    Text(text = phoneSuppText)
                },
                isError = phoneError,
                maxLines = 1,
            )
            OutlinedTextField(
                value = hobbies,
                onValueChange = { newText ->
                    hobbies = newText
                },
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(text = "Hobbies")
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Password"
                    )
                },
                supportingText = {
                    Text(text = hobbiesSuppText)
                },
                isError = hobbiesError,
                maxLines = 1,
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
fun HomePrev() {
    HomeScreen()
}