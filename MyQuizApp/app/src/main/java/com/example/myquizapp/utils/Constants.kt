package com.example.myquizapp.utils

import com.example.myquizapp.model.Question
import com.example.myquizapp.R

object Constants {

    const val USER_NAME : String = "user_name"
    const val TOTAL_QUESTIONS:  String = "total_questions"
    const val CORRECT_ANSWER: String = "correct_answer"

    fun getQuestions(): MutableList<Question>{
        val questionsList : MutableList<Question> = mutableListOf()
        val que1 = Question(1,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_argentina,
            "Argentina",
            "Australia",
            "Austria",
            "Armenia",
            1
        )

        questionsList.add(que1)

        val que2 = Question(2,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_australia,
            "Argentina",
            "Australia",
            "Austria",
            "Armenia",
            2
        )

        questionsList.add(que2)

        val que3 = Question(3,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_belgium,
            "Bielorussia",
            "Sudan",
            "Venezuela",
            "Belgium",
            4
        )

        questionsList.add(que3)

        val que4 = Question(4,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_brazil,
            "Bielorussia",
            "Turkmenistan",
            "Brazil",
            "Belgium",
            3
        )

        questionsList.add(que4)

        val que5 = Question(5,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_denmark,
            "Italy",
            "Denmark",
            "Brazil",
            "Belgium",
            2
        )

        questionsList.add(que5)

        val que6 = Question(6,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_fiji,
            "fiji",
            "Denmark",
            "Brazil",
            "Belgium",
            1
        )

        questionsList.add(que6)

        val que7 = Question(7,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_germany,
            "fiji",
            "China",
            "Germany",
            "Russia",
            3
        )

        questionsList.add(que7)

        val que8 = Question(8,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_india,
            "Pakistan",
            "Sudan",
            "Chile",
            "India",
            4
        )

        questionsList.add(que8)

        val que9 = Question(9,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_kuwait,
            "Kuwait",
            "Iran",
            "Iraq",
            "Italy",
            1
        )

        questionsList.add(que9)

        return questionsList
    }
}