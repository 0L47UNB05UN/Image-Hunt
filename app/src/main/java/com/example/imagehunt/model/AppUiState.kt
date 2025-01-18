package com.example.imagehunt.model

import com.example.imagehunt.data.Question

data class AppUiState(
    val questions: List<Question>,
    val question: Question,
    val score: Int = 0,
    val gameOver: Boolean = false
)
