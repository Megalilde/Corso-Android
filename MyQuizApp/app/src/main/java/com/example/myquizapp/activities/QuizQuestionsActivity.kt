package com.example.myquizapp.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
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
    private var mTimer: CountDownTimer? = null

    // Dati passati dall'intent
    private var mUserName : String? = null
    private var mUserSurname : String? = null
    private var mCorrectAnswer : Int = 0
    private var mWrongAnswer : Int = 0
    private var mCounterSeconds: Int = 1


    private var binding: ActivityQuizQuestionsBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mUserName = intent.getStringExtra(Constants.USER_NAME)
        mUserSurname= intent.getStringExtra(Constants.USER_SURNAME)

        binding?.llOptionOne?.setOnClickListener(this)
        binding?.llOptionTwo?.setOnClickListener(this)
        binding?.llOptionThree?.setOnClickListener(this)
        binding?.llOptionFour?.setOnClickListener(this)
        binding?.btnSubmit?.setOnClickListener(this)

        mQuestionList = QuestionList(this).getQuestions()

        setQuestion()
        setupTimer()
        defaultOptionsView()


    }

    // Setta i dati sia al livello grafico che di dati.
    private fun setQuestion() {
        val question: Question = mQuestionList!![mCurrentPosition - 1]
        defaultOptionsView()
        binding?.tvQuestion?.text = question.question
        binding?.ivImage?.setImageResource(question.image)
        binding?.tvProgress?.text = resources.getString(R.string.Question)+ " " + mCurrentPosition + "/" + mQuestionList?.size
        binding?.tvOptionOneTemp?.text = question.optionOne
        binding?.tvOptionTwoTemp?.text = question.optionTwo
        binding?.tvOptionThreeTemp?.text = question.optionTree
        binding?.tvOptionFourTemp?.text = question.optionFour

        if(mCurrentPosition == mQuestionList!!.size){
            binding?.btnSubmit?.text = resources.getString(R.string.finish)
        }else{
            binding?.btnSubmit?.text = resources.getString(R.string.submit)

        }
    }

    // Setta al livello grafico le TextView delle varie opzioni nel caso di default (Grafica)

    private fun defaultOptionsView(){
        val optionsLinearLayout = mutableListOf<LinearLayout>()

        binding?.llOptionOne?.let {
            optionsLinearLayout.add(0,it)
        }
        binding?.llOptionTwo?.let {
            optionsLinearLayout.add(1,it)
        }
        binding?.llOptionThree?.let {
            optionsLinearLayout.add(2,it)
        }
        binding?.llOptionFour?.let {
            optionsLinearLayout.add(3,it)
        }
        for(option in optionsLinearLayout){
            // Setta gli stili delle  background
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }

        for(linearLayout in optionsLinearLayout){
            // First child
            val firstChild = linearLayout.getChildAt(0)

            // Last child
            val lastChild = linearLayout.getChildAt(linearLayout.childCount - 1)


            if (firstChild is TextView) {
                val optionTextViewFirst = mutableListOf<TextView>()
                binding?.tvOptionLetterA?.let {
                    optionTextViewFirst.add(0,it)
                }
                binding?.tvOptionLetterB?.let {
                    optionTextViewFirst.add(1,it)
                }
                binding?.tvOptionLetterC?.let {
                    optionTextViewFirst.add(2,it)
                }
                binding?.tvOptionLetterD?.let {
                    optionTextViewFirst.add(3,it)
                }
                for(option in optionTextViewFirst){
                    // Setta gli stili delle TextView e background
                    option.setTextColor(Color.parseColor("#7A8089"))
                    option.typeface = Typeface.DEFAULT
                }

            }

            if (lastChild is TextView) {
                val optionTextViewLast = mutableListOf<TextView>()
                binding?.tvOptionOneTemp?.let {
                    optionTextViewLast.add(0,it)
                }
                binding?.tvOptionTwoTemp?.let {
                    optionTextViewLast.add(1,it)
                }
                binding?.tvOptionThreeTemp?.let {
                    optionTextViewLast.add(2,it)
                }
                binding?.tvOptionFourTemp?.let {
                    optionTextViewLast.add(3,it)
                }
                for(option in optionTextViewLast){
                    // Setta gli stili delle TextView e background
                    option.setTextColor(Color.parseColor("#7A8089"))
                    option.typeface = Typeface.DEFAULT
                }
            }

        }

    }


    // Setta al livello grafico le TextView delle varie opzioni nel caso di selezionamento. (Grafica)
    private fun selectedOptionView(ll: LinearLayout, selectedOptionNum: Int){
        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum
        // First child
        val firstChild = ll.getChildAt(0)

        // Last child
        val lastChild = ll.getChildAt(ll.childCount - 1)

        (firstChild as TextView).setTextColor(Color.parseColor("#363A43"))
        firstChild.setTypeface(firstChild.typeface, Typeface.BOLD)

        (lastChild as TextView).setTextColor(Color.parseColor("#363A43"))
        lastChild.setTypeface(firstChild.typeface, Typeface.BOLD)
        ll.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)


    }


    override fun onClick(view: View?) {
        when(view?.id){
            R.id.llOptionOne -> {
                binding?.llOptionOne?.let {
                    selectedOptionView(it,1)
                }
            }
            R.id.llOptionTwo -> {
                binding?.llOptionTwo?.let {
                    selectedOptionView(it,2)
                }
            }
            R.id.llOptionThree -> {
                binding?.llOptionThree?.let {
                    selectedOptionView(it,3)
                }
            }
            R.id.llOptionFour -> {
                binding?.llOptionFour?.let {
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
                            intent.putExtra(Constants.TOTAL_TIME, mCounterSeconds)
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
                        binding?.btnSubmit?.text = resources.getString(R.string.finish)
                    } else {
                        binding?.btnSubmit?.text = resources.getString(R.string.go_to_next_question)
                    }

                    mSelectedOptionPosition = 0
                }
            }
        }

    }

    private fun answerView(answer:Int, drawableView: Int){
        when(answer){
            1 -> {
                binding?.llOptionOne?.background = ContextCompat.getDrawable(this,drawableView)


            }
            2 -> {
                binding?.llOptionTwo?.background = ContextCompat.getDrawable(this,drawableView)

            }
            3 -> {
                binding?.llOptionThree?.background = ContextCompat.getDrawable(this,drawableView)

            }
            4 -> {
                binding?.llOptionFour?.background = ContextCompat.getDrawable(this,drawableView)

            }
        }
    }


    private fun setupTimer(){
        mTimer = object : CountDownTimer(360 * 1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                mCounterSeconds++
                binding?.tvTimer?.text = mCounterSeconds.toString() + " S"

            }

            override fun onFinish() {
                Toast.makeText(this@QuizQuestionsActivity,"Tempo Scaduto", Toast.LENGTH_LONG).show()
            }
        }.start()
    }
}