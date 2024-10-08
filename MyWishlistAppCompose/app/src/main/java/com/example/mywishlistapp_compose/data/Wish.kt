package com.example.mywishlistapp_compose.data

data class Wish(
    val id: Long = 0,
    val title: String = "",
    val description: String = ""
)


object DummyWish{
    val wishList = listOf(
        Wish(title="Google Watch 2", description = "An Android watch"),
        Wish(title="Oculus Quest 2 ", description = "A VR headset for playing games"),
        Wish(title="A Sci-fi, Book", description = "A science friction book from any best seller")
    )
}
