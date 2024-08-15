package com.altatec.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.altatec.myapplication.R
import com.altatec.myapplication.ui.theme.AppTheme

@Composable
fun ApiScreen() {

    var search by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = { newText ->
                    search = newText
                },
                label = {
                    Text(text = "Search")
                },
                maxLines = 1,
            )
            Button(
                onClick = {
                    /*TODO*/
                }
            ) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        }
        Text(
            text = "Total characters : 16",
            modifier = Modifier
                .padding(vertical = 8.dp)
        )
        //LazyColumn(content =)
    }
}

@Composable
fun ApiItem() {
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApiPrev() {
    AppTheme {
        ApiScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ApiItemPrev() {
    AppTheme {
        ApiItem()
    }
}