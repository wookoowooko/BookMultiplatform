package io.wookoo.bookapp.book.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_books")
data class BookEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val description: String?,
    val firstPublishYear: String?,
    val ratingAverage: Double?,
    val ratingsCount: Int?,
    val numPagesMedian: Int?,
    val numEditions: Int,
    val imgUrl: String?,
)


