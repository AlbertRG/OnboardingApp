package com.altatec.myapplication.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.altatec.myapplication.R
import com.altatec.myapplication.ui.theme.AppTheme

@Composable
fun HomeScreen() {

    var name by remember { mutableStateOf("") }
    var nameSuppText by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var birthdaySuppText by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var addressSuppText by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf(false) }
    var phoneSuppText by remember { mutableStateOf("") }
    var hobbies by remember { mutableStateOf("") }
    var hobbiesSuppText by remember { mutableStateOf("Optional") }
    var addContactEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(
            onClick = {
                /*TODO Take Photo*/
            },
            modifier = Modifier
                .height(120.dp)
                .width(120.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_add_a_photo_48),
                contentDescription = "Photo",
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
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
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Name"
                )
            },
            supportingText = {
                Text(text = nameSuppText)
            },
            maxLines = 1
        )
        OutlinedTextField(
            value = birthday,
            onValueChange = { newText ->
                birthday = newText
            },
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = true,
            label = {
                Text(text = "Birthday")
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        /*TODO Open DatePickerDialog*/
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Email"
                    )
                }
            },
            supportingText = {
                Text(text = birthdaySuppText)
            },
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
                    imageVector = Icons.Filled.Place,
                    contentDescription = "Password"
                )
            },
            supportingText = {
                Text(text = addressSuppText)
            },
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
                Text(text = "Hobby")
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
            maxLines = 1,
        )
        Button(
            onClick = {
                /*TODO Add Contact*/
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            enabled = addContactEnabled
        ) {
            Text(text = "Add Contact")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomePrev() {
    AppTheme {
        HomeScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DatePickerPrev() {
    AppTheme {
        DatePickerModal({}, {})
    }
}