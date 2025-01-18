package com.example.imagehunt.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.imagehunt.R
import com.example.imagehunt.model.AppViewModel

@Composable
fun MainMenu(
    onStartClicked: ()->Unit,
    onExitClicked: ()->Unit,
    onSettingsOnClicked: ()->Unit,
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel=viewModel()
){
    Box(
        contentAlignment = Alignment.Center
    ){
        Image(
            painterResource(R.drawable.welcome),
            contentDescription=null,
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxSize()
        )
        if (appViewModel.displayInstruction) {
            AlertDialog(
                onDismissRequest = { },
                title = { Text(stringResource(R.string.instr)) },
                text = {
                    Text(
                        stringResource(R.string.instruction),
                        modifier = modifier.verticalScroll(rememberScrollState())
                    )
                },
                confirmButton = {
                    Button(onClick = { appViewModel.displayInstruction = false }) {
                        Text(stringResource(R.string.ok))
                    }
                }
            )
        }
        if (appViewModel.closeApp) {
            CloseAppDialog({ appViewModel.closeApp = false }, onExitClicked)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = modifier.fillMaxSize()
        ) {
            Button(
                onClick = onStartClicked,
                modifier = modifier.padding(bottom = 15.dp)
            ) {
                Text(text = stringResource(R.string.start), fontSize = 24.sp)
            }
            Button(
                onClick = { appViewModel.displayInstruction = true },
                modifier = modifier.padding(bottom = 15.dp)
            ) {
                Text(text = stringResource(R.string.instr), fontSize = 24.sp)
            }
            Button(
                onClick = onSettingsOnClicked,
                modifier = modifier.padding(bottom = 15.dp)
            ) {
                Text(text = stringResource(R.string.settings), fontSize = 24.sp)
            }
            Button(
                onClick = { appViewModel.closeApp = true },
                modifier = modifier.padding(bottom = 150.dp)
            ) {
                Text(text = stringResource(R.string.exit), fontSize = 24.sp)
            }
        }
    }
}

@Composable
fun CloseAppDialog(
    onResumeClicked: ()->Unit,
    onExitClicked: () -> Unit,
    ){
    AlertDialog(
        onDismissRequest = {  },
        title = {Text(stringResource(R.string.close))},
        text = {Text( stringResource( R.string.close_game ) ) },
        dismissButton = {
            Button(onClick = onResumeClicked){
                Text(stringResource(R.string.no))
            }
                        },
        confirmButton = {
            Button(onClick = onExitClicked){
                Text(stringResource(R.string.yes))
            }
        }
    )
}