package com.example.myrecipeapp_compose

sealed class Screen(val route: String) {
    object RecipeScreen: Screen("recipescreen")
    object DetailScreen: Screen("detailscreen")
}