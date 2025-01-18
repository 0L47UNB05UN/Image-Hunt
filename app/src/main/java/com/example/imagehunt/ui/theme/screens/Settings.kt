package com.example.imagehunt.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.imagehunt.R
import com.example.imagehunt.model.AppViewModel


@Composable
fun Settings(appViewModel: AppViewModel, onAboutClick: ()->Unit, modifier: Modifier= Modifier){
    Box(
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(R.drawable.app_icon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.3f,
            modifier = modifier.fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Text(
                    text = stringResource(R.string.dark_mode),
                    fontSize = 24.sp
                )
                Switch(
                    checked = appViewModel.darkMode,
                    onCheckedChange = { appViewModel.darkMode = !appViewModel.darkMode })
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Text(
                    stringResource(R.string.diff),
                    fontSize = 24.sp
                )
                TextButton(onClick = { appViewModel.onDifficultyExpanded = true }) {
                    Text(
                        appViewModel.gameDifficulty,
                        fontSize = 16.sp
                    )
                    DropdownMenu(
                        expanded = appViewModel.onDifficultyExpanded,
                        onDismissRequest = { appViewModel.onDifficultyExpanded = false }
                    ) {
                        appViewModel.difficulty.keys.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option, fontSize = 16.sp) },
                                onClick = {
                                    appViewModel.gameDifficulty = option
                                    appViewModel.onDifficultyExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            TextButton(
                onClick = onAboutClick,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                Text(
                    text = "About",
                    fontSize = 24.sp
                )
            }
        }
    }
}
