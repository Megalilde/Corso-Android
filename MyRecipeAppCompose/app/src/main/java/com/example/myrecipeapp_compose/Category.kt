package com.example.myrecipeapp_compose

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
): Parcelable



// Serializable non è necessario se utiliziamo Gson , per esempio inserire i dati nelle shared

data class CategoriesResponse(
    val categories: List<Category>
)
