package com.example.myquizapp.model

import com.example.myquizapp.R

class QuestionList {

    private var questionsList: MutableList<Question> = mutableListOf()

    fun getQuestions(): MutableList<Question>{

        var questionsListTemp: MutableList<Question> = mutableListOf()

        val que1 = Question(1,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_argentina,
            "Argentina",
            "Australia",
            "Austria",
            "Armenia",
            1
        )

        questionsListTemp.add(que1)

        val que2 = Question(2,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_australia,
            "Argentina",
            "Australia",
            "Austria",
            "Armenia",
            2
        )

        questionsListTemp.add(que2)

        val que3 = Question(3,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_belgium,
            "Bielorussia",
            "Sudan",
            "Venezuela",
            "Belgium",
            4
        )

        questionsListTemp.add(que3)

        val que4 = Question(4,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_brazil,
            "Bielorussia",
            "Turkmenistan",
            "Brazil",
            "Belgium",
            3
        )

        questionsListTemp.add(que4)

        val que5 = Question(5,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_denmark,
            "Italy",
            "Denmark",
            "Brazil",
            "Belgium",
            2
        )

        questionsListTemp.add(que5)

        val que6 = Question(6,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_fiji,
            "fiji",
            "Denmark",
            "Brazil",
            "Belgium",
            1
        )

        questionsListTemp.add(que6)

        val que7 = Question(7,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_germany,
            "fiji",
            "China",
            "Germany",
            "Russia",
            3
        )

        questionsListTemp.add(que7)

        val que8 = Question(8,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_india,
            "Pakistan",
            "Sudan",
            "Chile",
            "India",
            4
        )

        questionsListTemp.add(que8)

        val que9 = Question(9,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_kuwait,
            "Kuwait",
            "Iran",
            "Iraq",
            "Italy",
            1
        )

        questionsListTemp.add(que9)

        val que10 = Question(10,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_poland,
            "Fiji",
            "Poland",
            "France",
            "Italy",
            2
        )
        questionsListTemp.add(que10)

        val que11 = Question(11,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_armenia,
            "Denmark",
            "Brazil",
            "France",
            "Armenia",
            4
        )
        questionsListTemp.add(que11)

        val que12 = Question(12,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_france,
            "France",
            "Armenia",
            "kuwait",
            "Italy",
            1
        )
        questionsListTemp.add(que12)

        val que13 = Question(13,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_portugal,
            "Fiji",
            "Portugal",
            "Germany",
            "Belgium",
            2
        )
        questionsListTemp.add(que13)

        val que14 = Question(14,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_spain,
            "Italy",
            "Poland",
            "Argentina",
            "Spain",
            4
        )
        questionsListTemp.add(que14)

        val que15 = Question(15,"What country does this flag belong to ?",
            R.drawable.ic_flag_of_ucraine,
            "Russia",
            "Poland",
            "Ukraine",
            "Austrialia",
            2
        )
        questionsListTemp.add(que15)

        // Scambia le domande e prende solo 10 domande
        questionsList = questionsListTemp.shuffled().take(10).toMutableList()

        return questionsList
    }
}