package com.altatec.myapplication.ui.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.altatec.myapplication.ui.Screen
import com.altatec.myapplication.ui.screensInBottom
import com.altatec.myapplication.ui.viewmodel.ScaffoldViewModel
import com.altatec.myapplication.ui.theme.AppTheme
import com.altatec.myapplication.utils.CameraUtils
import com.altatec.myapplication.utils.LocationUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldScreen() {

    val scaffoldViewModel: ScaffoldViewModel = viewModel()

    val navController: NavHostController = rememberNavController()
    var currentRoute by remember { mutableStateOf("home_screen") }
    var title by remember { mutableStateOf("Home") }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

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
                        title = item.bTitle,
                        drawable = item.icon,
                        clicked = {
                            title = item.bTitle
                            currentRoute = item.bRoute
                            navController.navigate(item.bRoute)
                        }
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScaffoldNavigation(navController)
        }
    }
}

@Composable
fun BottomBarItem(selected: Boolean, title: String, drawable: Int, clicked: () -> Unit) {
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
                    MaterialTheme.colorScheme.secondary
                else
                    MaterialTheme.colorScheme.surface
            )
        }
        Text(
            text = title,
            color = if (selected)
                MaterialTheme.colorScheme.secondary
            else
                MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
fun ScaffoldNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.BottomScreen.Home.bRoute
    ) {
        composable(route = Screen.BottomScreen.Home.bRoute) {
            val context = LocalContext.current
            val cameraUtils = CameraUtils(context)
            val locationUtils = LocationUtils(context)
            HomeScreen(cameraUtils, locationUtils, context)
        }
        composable(route = Screen.BottomScreen.Contacts.bRoute) {
            ContactsScreen()
        }
        composable(route = Screen.BottomScreen.Api.bRoute) {
            ApiScreen()
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ScaffoldLightPrev() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ScaffoldScreen()
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ScaffoldDarkPrev() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ScaffoldScreen()
        }
    }
}
