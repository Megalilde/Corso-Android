package com.example.myquizapp.model

import android.content.Context
import com.example.myquizapp.R

class QuestionList (private val context: Context){

    private var questionsList: MutableList<Question> = mutableListOf()

    fun getQuestions(): MutableList<Question>{

        var questionsListTemp: MutableList<Question> = mutableListOf()

        val que1 = Question(1,context.getString(R.string.question_text),
            R.drawable.ic_flag_of_argentina,
            context.getString(R.string.Argentina),
            context.getString(R.string.Australia),
            context.getString(R.string.Austria),
            context.getString(R.string.Armenia),
            1
        )

        questionsListTemp.add(que1)

        val que2 = Question(2,context.getString(R.string.question_text),
            R.drawable.ic_flag_of_australia,
            context.getString(R.string.Argentina),
            context.getString(R.string.Australia),
            context.getString(R.string.Austria),
            context.getString(R.string.Armenia),
            2
        )

        questionsListTemp.add(que2)

        val que3 = Question(3,context.getString(R.string.question_text),
            R.drawable.ic_flag_of_belgium,
            context.getString(R.string.Bielorussia),
            context.getString(R.string.Sudan),
            context.getString(R.string.Venezuela),
            context.getString(R.string.Belgium),
            4
        )

        questionsListTemp.add(que3)

        val que4 = Question(4,context.getString(R.string.question_text),
            R.drawable.ic_flag_of_brazil,
            context.getString(R.string.Bielorussia),
            context.getString(R.string.Turkmenistan),
            context.getString(R.string.Brazil),
            context.getString(R.string.Belgium),
            3
        )

        questionsListTemp.add(que4)

        val que5 = Question(5,context.getString(R.string.question_text),
            R.drawable.ic_flag_of_denmark,
            context.getString(R.string.Italy),
            context.getString(R.string.Denmark),
            context.getString(R.string.Brazil),
            context.getString(R.string.Belgium),
            2
        )

        questionsListTemp.add(que5)

        val que6 = Question(6,context.getString(R.string.question_text),
            R.drawable.ic_flag_of_fiji,
            context.getString(R.string.Fiji),
            context.getString(R.string.Denmark),
            context.getString(R.string.Brazil),
            context.getString(R.string.Belgium),
            1
        )

        questionsListTemp.add(que6)

        val que7 = Question(7,context.getString(R.string.question_text),
            R.drawable.ic_flag_of_germany,
            context.getString(R.string.Fiji),
            context.getString(R.string.China),
            context.getString(R.string.Germany),
            context.getString(R.string.Russia),
            3
        )

        questionsListTemp.add(que7)

        val que8 = Question(8,context.getString(R.string.question_text),
            R.drawable.ic_flag_of_india,
            context.getString(R.string.Pakistan),
            context.getString(R.string.Sudan),
            context.getString(R.string.Chile),
            context.getString(R.string.India),
            4
        )

        questionsListTemp.add(que8)

        val que9 = Question(9,context.getString(R.string.question_text),
            R.drawable.ic_flag_of_kuwait,
            context.getString(R.string.Kuwait),
            context.getString(R.string.Iran),
            context.getString(R.string.Iraq),
            context.getString(R.string.Italy),
            1
        )

        questionsListTemp.add(que9)

        val que10 = Question(10,context.getString(R.string.question_text),
            R.drawable.ic_flag_of_poland,
            context.getString(R.string.Fiji),
            context.getString(R.string.Poland),
            context.getString(R.string.France),
            context.getString(R.string.Italy),
            2
        )
        questionsListTemp.add(que10)

        val que11 = Question(11,context.getString(R.string.question_text),
            R.drawable.ic_flag_of_armenia,
            context.getString(R.string.Denmark),
            context.getString(R.string.Brazil),
            context.getString(R.string.France),
            context.getString(R.string.Armenia),
            4
        )
        questionsListTemp.add(que11)

        val que12 = Question(12,context.getString(R.string.question_text),
            R.drawable.ic_flag_of_france,
            context.getString(R.string.France),
            context.getString(R.string.Armenia),
            context.getString(R.string.Kuwait),
            context.getString(R.string.Italy),
            1
        )
        questionsListTemp.add(que12)

        val que13 = Question(13,context.getString(R.string.question_text),
            R.drawable.ic_flag_of_portugal,
            context.getString(R.string.Fiji),
            context.getString(R.string.Poland),
            context.getString(R.string.Germany),
            context.getString(R.string.Belgium),
            2
        )
        questionsListTemp.add(que13)

        val que14 = Question(14,context.getString(R.string.question_text),
            R.drawable.ic_flag_of_spain,
            context.getString(R.string.Italy),
            context.getString(R.string.Poland),
            context.getString(R.string.Argentina),
            context.getString(R.string.Spain),
            4
        )
        questionsListTemp.add(que14)

        val que15 = Question(15,context.getString(R.string.question_text),
            R.drawable.ic_flag_of_ucraine,
            context.getString(R.string.Russia),
            context.getString(R.string.Poland),
            context.getString(R.string.Ukraine),
            context.getString(R.string.Australia),
            3
        )
        questionsListTemp.add(que15)

        // Scambia le domande e prende solo 10 domande
        questionsList = questionsListTemp.shuffled().take(10).toMutableList()

        return questionsList
    }
}