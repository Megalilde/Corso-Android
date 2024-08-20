package com.example.myquizapp.activities

import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myquizapp.R
import com.example.myquizapp.databinding.ActivityResultBinding
import com.example.myquizapp.utils.Constants

class ResultActivity : AppCompatActivity() {

    private var binding: ActivityResultBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityResultBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

        binding?.tvName?.text = intent.getStringExtra(Constants.USER_NAME)
        binding?.tvSurname?.text = intent.getStringExtra(Constants.USER_SURNAME)

        val totalQuestions = intent.getIntExtra(Constants.TOTAL_QUESTIONS,0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWER,0)
        val wrongAnswers = intent.getIntExtra(Constants.WRONG_ANSWER,0)
        val totalSeconds = intent.getIntExtra(Constants.TOTAL_TIME, 0)

        val finalScore = calculateYourScore(correctAnswers,wrongAnswers)
        binding?.tvScore?.text = "Your Score is $finalScore out of $totalQuestions Questions"
        binding?.tvScoreSeconds?.text = "You take $totalSeconds seconds to complete!"
        binding?.btFinish?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    private fun calculateYourScore(correct: Int, wrong: Int) :Int {
        return (correct * 3) - (wrong * 1)
    }
}