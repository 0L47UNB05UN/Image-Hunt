package com.example.imagehunt.model


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.imagehunt.data.DataSource
import com.example.imagehunt.data.Question
import com.example.imagehunt.data.TIME
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class AppViewModel : ViewModel() {
    val difficulty = mapOf(
        "easy" to Pair(4, 2),
        "medium" to Pair(6, 3),
        "hard" to Pair(9, 3)
    )
    var darkMode by mutableStateOf(false)
    var gameDifficulty by mutableStateOf("medium")
    var onDifficultyExpanded by mutableStateOf(false)
    var displayInstruction by mutableStateOf(false)
    var closeApp by mutableStateOf(false)

    var asked: MutableList<Question> = mutableListOf()
    private var questions: List<Question> by mutableStateOf(DataSource().loadQuestions(questionDifficulty()))
    private val _uiState = MutableStateFlow(AppUiState(questions, grabInitialQuestion()))
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    var started: Boolean by mutableStateOf(false)
    var timeLeft: Int by mutableIntStateOf(TIME)

    init{
        resetGame()
    }

    fun resetGame(){
        asked.clear()
        questions = DataSource().loadQuestions(questionDifficulty())
        _uiState.value = AppUiState(questions, grabInitialQuestion())
        started = false
        timeLeft = TIME
    }

    fun nextQuestion() {
        _uiState.update {currentState->
            currentState.copy(
                question = grabQuestion().also { asked.add(it) }
            )
        }
    }


    fun updateScore(newScore: Int){
        _uiState.update { currentState->
            currentState.copy(
                score = _uiState.value.score.plus(newScore)
            )
        }
    }


    private fun grabInitialQuestion(): Question {
        return  questions.random().also { asked.add(it) }
    }


    private fun grabQuestion(): Question {
        val newQuestion =  _uiState.value.questions.random()
        if (asked.size < _uiState.value.questions.size){
            if (asked.contains(newQuestion)){
                return grabQuestion()
            }else{
                return newQuestion
            }
        }else{
            _uiState.update {currentState ->
                currentState.copy(
                    gameOver = true
                )
            }
            return _uiState.value.question
        }
    }


    fun questionDifficulty(first: Boolean = true): Int {
        return if (first) difficulty[gameDifficulty]!!.first else difficulty[gameDifficulty]!!.second
    }
}
