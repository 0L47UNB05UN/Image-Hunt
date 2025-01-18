package com.example.imagehunt.ui.theme.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.imagehunt.R
import com.example.imagehunt.data.Question
import com.example.imagehunt.model.AppViewModel
import kotlinx.coroutines.delay
import com.example.imagehunt.data.SCORE_INCRE
import com.example.imagehunt.data.TIME
import com.example.imagehunt.model.AppUiState


@Composable
fun ScoreAndTimer(score: Int, timeLeft: Int, modifier: Modifier=Modifier){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(stringResource(R.string.score, score), fontSize = 24.sp)
        Text(stringResource(R.string.time_left, timeLeft), fontSize = 24.sp)
    }
}


@Composable
fun ImageCards(
    item: Question, onClick: ()->Unit, rotation: Float, modifier: Modifier = Modifier
){
    Card(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(onClick = onClick)
            .graphicsLayer {
                rotationY = rotation
            },
        elevation = CardDefaults.cardElevation(10.dp),
    ) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = modifier.background(
                if (item.correct != null) {
                    if (item.correct as Boolean) Color.Green else Color.Red
                }else{
                    MaterialTheme.colorScheme.background
                }
            )
        ) {
            Image(
                painter = painterResource(if (rotation > 90f) item.bimg else item.img),
                contentScale = ContentScale.FillBounds,
                contentDescription = stringResource(item.name),
                modifier = modifier
                    .padding(bottom = 28.dp)
                    .graphicsLayer {
                        rotationY = rotation
                    }
            )
            if (rotation<90f) {
                Text(stringResource(item.name), fontSize = 24.sp)
            }
        }
    }
}


@Composable
fun StartGame(
    appViewModel: AppViewModel, appUiState: AppUiState,
    onEndGame: ()->Unit,
    modifier: Modifier =Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier.fillMaxSize()
        ){
        Text(if(!appViewModel.started) stringResource(R.string.memo) else "",
            fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
            )
        ScoreAndTimer(appUiState.score, appViewModel.timeLeft)
        LazyVerticalGrid(
            columns = GridCells.Fixed(appViewModel.questionDifficulty(false)),
            modifier = modifier.background( MaterialTheme.colorScheme.primary )
        ) {
            items(appUiState.questions) { item ->
                val rotation by animateFloatAsState(
                    targetValue = if (item.flipped ) 180f else 0f,
                    label = "FloatAnimation" , animationSpec= tween(durationMillis = 500)
                )
                if (appUiState.gameOver and (rotation <=0f)){
                    GameOverDialog(
                        appUiState.score,
                        appUiState.questions.size,
                        { appViewModel.resetGame() },
                        { appViewModel.resetGame(); onEndGame() }
                    )
                }
                ImageCards(
                    item,
                    {
                        if ( appViewModel.started and (item.correct  == null) ) {
                            item.flipped = !item.flipped
                            item.correct = appUiState.question.name == item.name
                            appViewModel.updateScore(if (item.correct as Boolean) SCORE_INCRE else 0)
                            if (item.correct as Boolean) {
                                appViewModel.nextQuestion()
                            } else {
                                appViewModel.asked.add(item)
                            }
                            appViewModel.timeLeft = TIME
                        }
                    },
                    rotation
                )
            }
        }
        if (appViewModel.started) {
            LaunchedEffect(key1 = appViewModel.timeLeft) {
                if ((appViewModel.timeLeft > 0) and (!appUiState.gameOver)) {
                    delay(1000L)
                    appViewModel.timeLeft--
                }
                else{
                    if (!appUiState.gameOver) {
                        appUiState.question.correct = false
                        appUiState.question.flipped = false
                        appViewModel.nextQuestion()
                        appViewModel.timeLeft = TIME
                    }
                }
            }
            Text(
                stringResource(appUiState.question.question, stringResource(appUiState.question.name)),
                fontSize = 28.sp,
                modifier = modifier.padding(top = 28.dp)
            )
        } else{
            StartGameButton(
                {
                    appViewModel.started = true
                    appUiState.questions.forEach { it.flipped = true }
                }
            )
        }
    }
}


@Composable
fun StartGameButton( onClick: () -> Unit, modifier: Modifier =Modifier){
    Button(
        onClick = onClick
    ) {
        Text(
            stringResource(R.string.start), fontSize = 22.sp,
            modifier = modifier.padding(8.dp)
        )
    }
}


@Composable
fun GameOverDialog(
    score: Int, overall: Int, playAgain: ()->Unit,
    onExitGame: ()->Unit, modifier: Modifier = Modifier){
    AlertDialog(
        onDismissRequest = { },
        title = { Text(stringResource(R.string.game_over)) },
        text = { 
            Text(
                stringResource(R.string.your_score, score, overall),
                modifier.padding(end = 16.dp)
            )
        },
        modifier = modifier,
        dismissButton = {
            Button(
                onClick=onExitGame,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                )
            ){
                Text(stringResource(R.string.main_menu))
            }
        },
        confirmButton = {
            Button(
                onClick= playAgain
            ){
                Text(stringResource(R.string.play_again))
            }
        }
    )
}