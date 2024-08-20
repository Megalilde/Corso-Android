package com.example.myquizapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myquizapp.R
import com.example.myquizapp.utils.Constants

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvName: TextView = findViewById(R.id.tvName)
        val tvSurname: TextView = findViewById(R.id.tvSurname)
        val tvScore: TextView = findViewById(R.id.tvScore)
        val btFinish: TextView = findViewById(R.id.btFinish)

        tvName.text = intent.getStringExtra(Constants.USER_NAME)
        tvSurname.text = intent.getStringExtra(Constants.USER_SURNAME)
        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS,0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWER,0)
        val wrongAnswers = intent.getIntExtra(Constants.WRONG_ANSWER,0)

        val finalScore = calcolateYourScore(correctAnswers,wrongAnswers)
        tvScore.text = "Your Score is $finalScore out of $totalQuestions Questions"
        btFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    private fun calcolateYourScore(correct: Int,wrong: Int) :Int {
        return (correct * 3) - (wrong * 1)
    }
}