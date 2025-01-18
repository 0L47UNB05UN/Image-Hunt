package com.example.imagehunt.data

import com.example.imagehunt.R

class DataSource {
    private val questions = listOf(
        Question(R.drawable.antelope, R.string.antelope),
        Question(R.drawable.bear, R.string.bear),
        Question(R.drawable.camel, R.string.camel),
        Question(R.drawable.crocodile, R.string.crocodile),
        Question(R.drawable.deer, R.string.deer),
        Question(R.drawable.duck, R.string.duck),
        Question(R.drawable.frog, R.string.frog),
        Question(R.drawable.giraffe, R.string.giraffe),
        Question(R.drawable.goat, R.string.goat),
        Question(R.drawable.hamster, R.string.hamster),
        Question(R.drawable.hippo, R.string.hippo),
        Question(R.drawable.koala, R.string.koala),
        Question(R.drawable.lynx, R.string.lynx),
        Question(R.drawable.parrot, R.string.parrot),
        Question(R.drawable.rabbit, R.string.rabbit),
        Question(R.drawable.racoon, R.string.racoon),
        Question(R.drawable.sheep, R.string.sheep),
        Question(R.drawable.squirrel, R.string.squirrel),
        Question(R.drawable.wolf, R.string.wolf),
        Question(R.drawable.zebra, R.string.zebra)
    )
    fun loadQuestions(count: Int): List<Question>{
        return questions.shuffled().take(count)
    }
}