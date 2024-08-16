package com.altatec.myapplication.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.altatec.myapplication.R
import com.altatec.myapplication.ui.theme.AppTheme

@Composable
fun ContactsScreen() {

    val dialogVisible = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "You have 10 contacts",
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        //LazyColumn(content =)
    }
}

@Composable
fun ContactItem(onClickContact:() -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_add_a_photo_48),
            contentDescription = "Photo",
            modifier = Modifier
                .padding(8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Name",

            )
            Text(
                text = "25 years",
                textAlign = TextAlign.End
            )
        }
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
                            .padding(8.dp)
                    )
                    IconButton(
                        onClick = {
                            dialogVisible.value = false
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Cancel"
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

@Preview(showBackground = true)
@Composable
fun ContactsPrev() {
    AppTheme {
        ContactsScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ContactsItemPrev() {
    AppTheme {
        ContactItem{}
    }
}

@Preview(showBackground = true)
@Composable
fun ContactDialogPrev() {
    AppTheme {
        val dialogVisible = remember { mutableStateOf(true) }
        ContactDialog(dialogVisible = dialogVisible)
    }
}
