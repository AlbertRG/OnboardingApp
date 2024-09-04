package com.altatec.myapplication.ui.view

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.altatec.myapplication.R
import com.altatec.myapplication.data.local.entity.Contact
import com.altatec.myapplication.data.local.entity.DummyContact
import com.altatec.myapplication.data.local.entity.DummyContactList
import com.altatec.myapplication.ui.theme.AppTheme

@Composable
fun ContactsScreen() {

    //val viewState by contactViewModel.characterState
    val dialogVisible = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        if (LocalInspectionMode.current) {
            ContactListView(responseContact = DummyContactList.contactList)
        } else {
            /*when {
                viewState.loading -> {
                    LoadingContactView()
                }

                viewState.error != null -> {
                    ErrorContactView(viewState.error.toString())
                }

                else -> {
                    ContactListView(viewState.responseCharacters)

                }
            }*/
        }
    }
}

@Composable
fun ContactListView(responseContact: List<Contact>) {

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
                ContactItem(contact = contact){
                    //TODO Open Dialog
                }
                Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
            }
        }
    }
}

@Composable
fun ContactItem(contact: Contact, onClickContact:() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = if (LocalInspectionMode.current) {
                painterResource(id = R.drawable.baseline_add_a_photo_24)
            } else {
                rememberAsyncImagePainter(contact.photo)
            },
            contentDescription = contact.name,
            modifier = Modifier
                .height(80.dp)
                .width(80.dp)
                .padding(8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (LocalInspectionMode.current) {
                    "Alberto"
                } else {
                    contact.name
                },
            )
            Text(
                text = if (LocalInspectionMode.current) {
                    "30 years"
                } else {
                    contact.birthday
                },
                textAlign = TextAlign.End
            )
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
        LottieAnimation(composition = composition)
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
fun ContactDialog(dialogVisible: MutableState<Boolean>){
    if (dialogVisible.value){
        Dialog(
            onDismissRequest = {
                dialogVisible.value = false
            })
        {
            Card(
                modifier = Modifier
                    .height(180.dp)
                    .width(180.dp),
                elevation = CardDefaults.cardElevation(24.dp),
                border = BorderStroke(2.dp, color = MaterialTheme.colorScheme.primary)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Eduardo",
                        modifier = Modifier
                            .padding(8.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(
                        onClick = {
                            dialogVisible.value = false
                        }
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
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "22 years")
                    Text(text = "Tonala, Jalisco")
                    Text(text = "332142744")
                    Text(text = "Soccer")
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ContactsPrev() {
    AppTheme {
        ContactsScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ContactsItemPrev() {
    AppTheme {
        ContactItem(DummyContact.contact){}
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
        val dialogVisible = remember { mutableStateOf(true) }
        ContactDialog(dialogVisible = dialogVisible)
    }
}
