package com.example.imagehunt.ui.theme.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.imagehunt.R
import com.example.imagehunt.model.AppViewModel
import com.example.imagehunt.ui.theme.ImageHuntTheme

enum class ImageHuntScreen {
    MainMenu,
    StartGame,
    Settings,
    About
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageHuntApp(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = viewModel()
){
    ImageHuntTheme(darkTheme = appViewModel.darkMode){
        val appUiState by appViewModel.uiState.collectAsState()
        val backStateEntry by navController.currentBackStackEntryAsState()
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = modifier.fillMaxWidth()
                        ) {
                            Image(
                                painterResource(R.drawable.app_icon),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                alpha = 0.2f
                            )
                            Text(
                                ImageHuntScreen.valueOf(
                                    backStateEntry?.destination?.route ?: ImageHuntScreen.MainMenu.name
                                ).name,
                                fontSize = 28.sp
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = ImageHuntScreen.MainMenu.name,
                modifier = modifier.padding(innerPadding)
            ) {
                composable(route = ImageHuntScreen.MainMenu.name) {
                    val actvt = LocalContext.current as Activity
                    MainMenu(
                        onStartClicked = {
                            navController.navigate(ImageHuntScreen.StartGame.name)
                            appViewModel.resetGame()
                        },
                        onExitClicked = { actvt.finish() },
                        onSettingsOnClicked = { navController.navigate(ImageHuntScreen.Settings.name) }
                    )
                }
                composable(route = ImageHuntScreen.StartGame.name) {
                    StartGame(
                        appViewModel,
                        appUiState,
                        { navController.popBackStack(ImageHuntScreen.MainMenu.name, false) }
                    )
                }
                composable(route = ImageHuntScreen.Settings.name) {
                    Settings(
                        appViewModel,
                        onAboutClick = { navController.navigate(ImageHuntScreen.About.name) })
                }
                composable(route = ImageHuntScreen.About.name) {
                    About()
                }
            }
        }
    }
}

