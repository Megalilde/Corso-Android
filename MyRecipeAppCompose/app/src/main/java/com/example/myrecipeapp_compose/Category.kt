package com.example.myrecipeapp_compose

data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)



// Serializable non è necessario se utiliziamo Gson , per esempio inserire i dati nelle shared

data class CategoriesResponse(
    val categories: List<Category>
)
