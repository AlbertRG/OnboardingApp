package com.altatec.myapplication.ui.view

import android.content.res.Configuration
import android.text.TextUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import com.altatec.myapplication.ui.theme.AppTheme
import com.altatec.myapplication.ui.viewmodel.ApiViewModel
import com.altatec.myapplication.data.remote.Result

@Composable
fun ApiScreen() {

    val apiViewModel: ApiViewModel = viewModel()
    val viewState by apiViewModel.characterState

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        if (LocalInspectionMode.current) {
            CharacterListView(DummyCharacterList.characterList)
        } else {
            when {
                viewState.loading -> {
                    LoadingApiView()
                }

                viewState.error != null -> {
                    ErrorApiView(viewState.error.toString())
                }

                else -> {
                    CharacterListView(viewState.responseCharacters)

                }
            }
        }
    }
}

@Composable
fun CharacterListView(responseCharacters: List<Result>) {

    var search by remember { mutableStateOf("") }
    var filterCharacterList = responseCharacters

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(800.dp)
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
                    filterCharacterList = if (!TextUtils.isEmpty(search)) {
                        responseCharacters.filter {
                            it.name.contains(
                                search,
                                ignoreCase = true
                            )
                        }
                    } else {
                        responseCharacters
                    }
                }
            ) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        }
        Text(
            text = "Total characters: ${responseCharacters.size}",
            modifier = Modifier
                .padding(16.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            items(filterCharacterList) { character ->
                ApiItem(character = character)
                Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
            }
        }
    }
}

@Composable
fun ApiItem(character: Result) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
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
                .height(80.dp)
                .width(80.dp)
                .padding(8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = character.name)
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