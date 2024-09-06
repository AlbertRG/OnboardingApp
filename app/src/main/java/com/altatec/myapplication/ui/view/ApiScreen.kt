package com.altatec.myapplication.ui.view

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.altatec.myapplication.R
import com.altatec.myapplication.data.remote.DummyCharacter
import com.altatec.myapplication.data.remote.DummyCharacterList
import com.altatec.myapplication.data.remote.Result
import com.altatec.myapplication.ui.theme.AppTheme
import com.altatec.myapplication.ui.viewmodel.ApiViewModel

@Composable
fun ApiScreen() {
    val apiViewModel: ApiViewModel = viewModel()

    val viewState by apiViewModel.characterState
    val currentPage by apiViewModel::currentPage
    val totalPages by apiViewModel::totalPages

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        if (LocalInspectionMode.current) {
            CharacterListView({}, 1, {}, 20, DummyCharacterList.characterList
            )
        } else {
            when {
                viewState.loading -> {
                    LoadingApiView()
                }

                viewState.error != null -> {
                    ErrorApiView(viewState.error.toString())
                }

                else -> {
                    CharacterListView(
                        previousPage = { apiViewModel.fetchPreviousPage() },
                        currentPage = currentPage,
                        nextPage = { apiViewModel.fetchNextPage() },
                        totalPages = totalPages,
                        viewState.responseCharacters
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterListView(
    previousPage: () -> Unit,
    currentPage: Int,
    nextPage: () -> Unit,
    totalPages: Int,
    responseCharacters: List<Result>
) {

    var search by remember { mutableStateOf("") }

    val filterCharacterList = remember(search, responseCharacters) {
        if (search.isNotEmpty()) {
            responseCharacters.filter {
                it.name.contains(search, ignoreCase = true)
            }
        } else {
            responseCharacters
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(800.dp)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = search,
                onValueChange = { newText ->
                    search = newText
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                label = {
                    Text(text = "Search")
                },
                maxLines = 1,
            )
        }
        Text(
            text = "Total characters: ${filterCharacterList.size}",
            modifier = Modifier
                .padding(8.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(filterCharacterList) { character ->
                ApiItem(character = character)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(
                onClick = { previousPage() },
                enabled = currentPage > 1
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Previous Page",
                    modifier = Modifier
                        .size(32.dp)
                )
            }
            Text(text = "${currentPage}/${totalPages}")
            IconButton(
                onClick = { nextPage() },
                enabled = currentPage < totalPages
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Next Page",
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        }
    }
}

@Composable
fun ApiItem(character: Result) {

    Card(
        modifier = Modifier
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = if (LocalInspectionMode.current) {
                    painterResource(id = R.drawable.rickytest)
                } else {
                    rememberAsyncImagePainter(character.image)
                },
                contentDescription = character.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onSecondary)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = character.name)
            }
        }
    }
}

@Composable
fun LoadingApiView() {

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
fun ErrorApiView(error: String) {
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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ApiScreenPrev() {
    AppTheme {
        ApiScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ApiItemPrev() {
    AppTheme {
        ApiItem(DummyCharacter.character)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingPrev() {
    AppTheme {
        LoadingApiView()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorPrev() {
    AppTheme {
        ErrorApiView("Error fetching CharactersResponse")
    }
}