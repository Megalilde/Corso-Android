package com.example.myquizapp.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myquizapp.R
import com.example.myquizapp.databinding.ActivityQuizQuestionsBinding
import com.example.myquizapp.model.Question
import com.example.myquizapp.model.QuestionList
import com.example.myquizapp.utils.Constants

/* E stata utilizzata View.OnClickListener come interfaccia per eseguire azioni specifiche al click su più view e
quindi un unico metodo per gestire i click sulle varie interfacce, invece di implementarle singolarmente per ogni
view. */
class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionList: MutableList<Question>? = null
    private var mSelectedOptionPosition: Int = 0

    // Dati passati dall'intent
    private var mUserName : String? = null
    private var mUserSurname : String? = null
    private var mCorrectAnswer : Int = 0
    private var mWrongAnswer : Int = 0


    private var binding: ActivityQuizQuestionsBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mUserName = intent.getStringExtra(Constants.USER_NAME)
        mUserSurname= intent.getStringExtra(Constants.USER_SURNAME)

        binding?.tvOptionOne?.setOnClickListener(this)
        binding?.tvOptionTwo?.setOnClickListener(this)
        binding?.tvOptionTree?.setOnClickListener(this)
        binding?.tvOptionFour?.setOnClickListener(this)
        binding?.btnSubmit?.setOnClickListener(this)

        mQuestionList = QuestionList().getQuestions()

        setQuestion()
        defaultOptionsView()


    }

    // Setta i dati sia al livello grafico che di dati.
    private fun setQuestion() {
        val question: Question = mQuestionList!![mCurrentPosition - 1]
        defaultOptionsView()
        binding?.tvQuestion?.text = question.question
        binding?.ivImage?.setImageResource(question.image)
        binding?.progressBar?.progress = mCurrentPosition
        binding?.tvProgress?.text = "$mCurrentPosition/${binding?.progressBar?.max}"
        binding?.tvOptionOne?.text = question.optionOne
        binding?.tvOptionTwo?.text = question.optionTwo
        binding?.tvOptionTree?.text = question.optionTree
        binding?.tvOptionFour?.text = question.optionFour

        if(mCurrentPosition == mQuestionList!!.size){
            binding?.btnSubmit?.text = "FINISH"
        }else{
            binding?.btnSubmit?.text = "SUBMIT"

        }
    }

    // Setta al livello grafico le TextView delle varie opzioni nel caso di default (Grafica)

    private fun defaultOptionsView(){
        val options = mutableListOf<TextView>()
        //Esegui una funzione sull'oggetto considerato se esso non è vuoto, alla posizione 0 aggiunti esso.
        binding?.tvOptionOne?.let {
            options.add(0,it)
        }
        binding?.tvOptionTwo?.let {
            options.add(1,it)
        }
        binding?.tvOptionTree?.let {
            options.add(2,it)
        }
        binding?.tvOptionFour?.let {
            options.add(3,it)
        }
        for(option in options){
            // Setta gli stili delle TextView e background
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }

    }


    // Setta al livello grafico le TextView delle varie opzioni nel caso di selezionamento. (Grafica)
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int){
        defaultOptionsView()

        // Setta gli stili delle TextView e background
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)


    }


    override fun onClick(view: View?) {
        when(view?.id){
            R.id.tvOptionOne -> {
                binding?.tvOptionOne?.let {
                    selectedOptionView(it,1)
                }
            }
            R.id.tvOptionTwo -> {
                binding?.tvOptionTwo?.let {
                    selectedOptionView(it,2)
                }
            }
            R.id.tvOptionTree -> {
                binding?.tvOptionTree?.let {
                    selectedOptionView(it,3)
                }
            }
            R.id.tvOptionFour -> {
                binding?.tvOptionFour?.let {
                    selectedOptionView(it,4)
                }
            }
            R.id.btnSubmit ->{

            if (mSelectedOptionPosition == 0) {

                mCurrentPosition++

                when {

                    mCurrentPosition <= mQuestionList!!.size -> {

                        setQuestion()
                    }
                    else -> {
                        val intent = Intent(this, ResultActivity::class.java)
                        intent.putExtra(Constants.USER_NAME, mUserName)
                        intent.putExtra(Constants.USER_SURNAME,mUserSurname)
                        intent.putExtra(Constants.CORRECT_ANSWER, mCorrectAnswer)
                        intent.putExtra(Constants.WRONG_ANSWER, mWrongAnswer)
                        intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList?.size)
                        startActivity(intent)
                        finish()
                    }
                }
            } else {
                val question = mQuestionList?.get(mCurrentPosition - 1)

                // Verifica se la domanda è quella corretta.
                if (question!!.correctAnswer != mSelectedOptionPosition) {
                    answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
                    mWrongAnswer++
                }else{
                    mCorrectAnswer++
                }

                // Nel caso della risposta corretta
                answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                if (mCurrentPosition == mQuestionList!!.size) {
                    binding?.btnSubmit?.text = "FINISH"
                } else {
                    binding?.btnSubmit?.text = "GO TO NEXT QUESTION"
                }

                mSelectedOptionPosition = 0
                }
            }
        }

    }

    private fun answerView(answer:Int, drawableView: Int){
        when(answer){
            1 -> {
                binding?.tvOptionOne?.background = ContextCompat.getDrawable(this,drawableView)

            }
            2 -> {
                binding?.tvOptionTwo?.background = ContextCompat.getDrawable(this,drawableView)

            }
            3 -> {
                binding?.tvOptionTree?.background = ContextCompat.getDrawable(this,drawableView)

            }
            4 -> {
                binding?.tvOptionFour?.background = ContextCompat.getDrawable(this,drawableView)

            }
        }
    }
}