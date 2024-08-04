package com.example.a7minutesworkout

object Constants {

    fun defaultExerciseList(): MutableList<ExerciseModel>{
        val exerciseList : MutableList<ExerciseModel>  = mutableListOf()

        val jumpingJacks = ExerciseModel(1,"Jumping Jacks", R.drawable.ic_jumping_jacks)
        exerciseList.add(jumpingJacks)

        val crunch = ExerciseModel(2,"Crunch", R.drawable.ic_abdominal_crunch)
        exerciseList.add(crunch)

        val highKnees = ExerciseModel(3,"High Knees Running in place", R.drawable.ic_high_knees_running_in_place)
        exerciseList.add(highKnees )

        val lunge = ExerciseModel(4,"Lunge", R.drawable.ic_lunge)
        exerciseList.add(lunge)

        val plank = ExerciseModel(5,"Plank", R.drawable.ic_plank)
        exerciseList.add(plank)

        val pushUp = ExerciseModel(6,"Push Up", R.drawable.ic_push_up)
        exerciseList.add(pushUp)

        val pushUpAndRotation = ExerciseModel(7,"Push Up Rotation", R.drawable.ic_push_up_and_rotation)
        exerciseList.add(pushUpAndRotation)

        val sidePlank = ExerciseModel(8,"Side Plank", R.drawable.ic_side_plank)
        exerciseList.add(sidePlank)

        val squat = ExerciseModel(9,"Squat", R.drawable.ic_squat)
        exerciseList.add(squat)

        val stepUpOnChair = ExerciseModel(10,"Step Up On Chair", R.drawable.ic_step_up_onto_chair)
        exerciseList.add(stepUpOnChair)

        val tricepsDipOnChair = ExerciseModel(11,"Dips On Chair", R.drawable.ic_triceps_dip_on_chair)
        exerciseList.add(tricepsDipOnChair)

        val wallSit = ExerciseModel(12,"Wall Sit", R.drawable.ic_wall_sit)
        exerciseList.add(wallSit)



        return exerciseList
    }
}