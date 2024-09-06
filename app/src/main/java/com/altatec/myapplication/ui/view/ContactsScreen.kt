package com.altatec.myapplication.ui.view

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.altatec.myapplication.R
import com.altatec.myapplication.data.local.entity.Contact
import com.altatec.myapplication.data.local.entity.DummyContact
import com.altatec.myapplication.data.local.entity.DummyContactList
import com.altatec.myapplication.ui.theme.AppTheme
import com.altatec.myapplication.ui.viewmodel.ContactsViewModel
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun ContactsScreen(
    contactsViewModel: ContactsViewModel
) {

    val viewState by contactsViewModel.contactsState

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        if (LocalInspectionMode.current) {
            ContactListView(responseContact = DummyContactList.contactList)
        } else {
            when {
                viewState.loading -> {
                    LoadingContactView()
                }

                viewState.error != null -> {
                    ErrorContactView(viewState.error.toString())
                }

                else -> {
                    ContactListView(viewState.contacts)

                }
            }
        }
    }
}

@Composable
fun ContactListView(responseContact: List<Contact>) {

    var showContactInfo by remember { mutableStateOf(false) }
    var selectedContact by remember { mutableStateOf<Contact?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(800.dp)
            .padding(8.dp)
    ) {
        Text(
            text = "Total contacts: ${responseContact.size}",
            modifier = Modifier
                .padding(16.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            items(responseContact) { contact ->
                ContactItem(contact = contact) {
                    selectedContact = contact
                    showContactInfo = true
                }
            }
        }
    }
    if (showContactInfo) {
        ContactInfoDialog(
            contact = selectedContact,
            onDismiss = { showContactInfo = false }
        )

    }
}

@Composable
fun ContactItem(contact: Contact, onClickContact: () -> Unit) {

    fun calculateAge(birthday: String, pattern: String = "MM-dd-yyyy"): String {
        return try {
            val formatter = DateTimeFormatter.ofPattern(pattern)
            val birthDate = LocalDate.parse(birthday, formatter)
            val currentDate = LocalDate.now()
            val age = Period.between(birthDate, currentDate).years
            age.toString()
        } catch (e: DateTimeParseException) {
            "Error"
        }
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClickContact() },
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (LocalInspectionMode.current) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = contact.name,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onSecondary)
                )
            } else {
                Image(
                    bitmap = contact.photo.asImageBitmap(),
                    contentDescription = contact.name,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onSecondary)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = contact.name,

                    )
                Text(
                    text = "${calculateAge(contact.birthday)} years",
                )
            }
        }
    }
}

@Composable
fun LoadingContactView() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    }
}

@Composable
fun ErrorContactView(error: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.error),
            contentDescription = "Error Connection"
        )
        Text(
            text = "Error Connection",
            modifier = Modifier
                .padding(16.dp),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = error,
            fontSize = 12.sp,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun ContactInfoDialog(contact: Contact?, onDismiss: () -> Unit) {
    if (contact == null) return
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .width(400.dp)
                .wrapContentHeight(),
            elevation = CardDefaults.cardElevation(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Contact Info",
                    modifier = Modifier
                        .padding(end = 65.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = onDismiss
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Cancel",
                        tint = Color.Black
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (LocalInspectionMode.current) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_person_24),
                        contentDescription = contact.name,
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onSecondary)
                    )
                } else {
                    Image(
                        bitmap = contact.photo.asImageBitmap(),
                        contentDescription = contact.name,
                        modifier = Modifier
                            .size(160.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onSecondary)
                    )
                }
                Text(
                    text = contact.name,
                    fontSize = 24.sp
                )
                Text(
                    text = contact.birthday,
                    fontSize = 18.sp
                )
                Text(
                    text = contact.address,
                    fontSize = 18.sp
                )
                Text(
                    text = contact.phone,
                    fontSize = 18.sp
                )
                if (contact.hobby != ""){
                    Text(
                        text = contact.hobby,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ContactsItemPrev() {
    AppTheme {
        ContactItem(DummyContact.contact) {}
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingContactPrev() {
    AppTheme {
        LoadingContactView()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorContactPrev() {
    AppTheme {
        ErrorContactView("Error fetching ContactResponse")
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ContactDialogPrev() {
    AppTheme {
        ContactInfoDialog(DummyContact.contact) {}
    }
}
