package com.example.imagehunt.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.imagehunt.R


const val TIME = 5
const val  SCORE_INCRE = 1
data class Question(
    @DrawableRes val img: Int,
    @StringRes val name: Int,
    @DrawableRes val bimg: Int = R.drawable.card_back,
    @StringRes val question: Int = R.string.ques,
    var flipped: Boolean = false,
    var correct: Boolean? = null
)
