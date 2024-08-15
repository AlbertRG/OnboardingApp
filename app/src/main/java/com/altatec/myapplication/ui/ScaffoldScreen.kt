package com.altatec.myapplication.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.altatec.myapplication.ScaffoldViewModel
import com.altatec.myapplication.screensInBottom
import com.altatec.myapplication.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldScreen(navController: NavHostController) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val scaffoldViewModel: ScaffoldViewModel = viewModel()
    val currentScreen by remember { scaffoldViewModel.currentScreen }
    val title by remember { mutableStateOf(currentScreen.bTitle) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var currentRoute: String? = "home_screen"

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
                        text = title,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.primaryContainer),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                screensInBottom.forEach { item ->
                    BottomBarItem(
                        selected = currentRoute == item.bRoute,
                        clicked = {
                            navController.navigate(item.bRoute)
                            currentRoute = navBackStackEntry?.destination?.route
                        },
                        title = item.bTitle,
                        drawable = item.icon
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            ScaffoldNavigation(
                navController = navController
            )
        }
    }
}

@Composable
fun BottomBarItem(selected: Boolean, clicked: () -> Unit, title: String, drawable: Int) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = { clicked() }) {
            Icon(
                painter = painterResource(id = drawable),
                contentDescription = title,
                tint = if (selected)
                    MaterialTheme.colorScheme.surface
                else
                    MaterialTheme.colorScheme.secondary
            )
        }
        Text(
            text = title,
            color = if (selected)
                MaterialTheme.colorScheme.surface
            else
                MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ScaffoldPrev(navController: NavHostController = rememberNavController()) {
    AppTheme {
        ScaffoldScreen(navController = navController)
    }
}

