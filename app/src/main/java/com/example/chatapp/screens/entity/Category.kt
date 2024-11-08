package com.example.chatapp.screens.entity

import com.example.chatapp.R

data class Category(
    val categoryId: String? = null,
    val categoryName: String? = null,
    val categoryImage: Int? = null,
) {
    companion object {
        const val MUSIC = "MUSIC"
        const val SPORT = "SPORT"
        const val MOVIES = "MOVIES"


        fun getCategoriesList(): List<Category> {
            return listOf(
                fromId(MUSIC),
                fromId(SPORT),
                fromId(MOVIES),
            )
        }

        fun fromId(id: String): Category {
            return when (id) {
                MUSIC -> Category(MUSIC, "Music", categoryImage = R.drawable.music)
                SPORT -> Category(SPORT, "Sport", categoryImage = R.drawable.sports)
                MOVIES -> Category(MOVIES, "Movies", categoryImage = R.drawable.movies)
                else -> Category(MOVIES, "Movies", categoryImage = R.drawable.movies)

            }
        }
    }
}
