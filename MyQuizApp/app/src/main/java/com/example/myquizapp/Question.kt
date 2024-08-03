package com.example.myquizapp


// Modello su cui immagazzinare i dati.
data class Question(
    val id: Int,
    val question: String,
    val image:Int,
    val optionOne: String,
    val optionTwo: String,
    val optionTree: String,
    val optionFour: String,
    val correctAnswer: Int

)
