package com.example.mywishlistapp_compose.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "wish-table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name="wish-title")
    val title: String = "",
    @ColumnInfo(name="wish-desc")
    val description: String = ""
)


object DummyWish{
    val wishList = listOf(
        Wish(title="Google Watch 2", description = "An Android watch"),
        Wish(title="Oculus Quest 2 ", description = "A VR headset for playing games"),
        Wish(title="A Sci-fi, Book", description = "A science friction book from any best seller")
    )
}
