package io.wookoo.bookapp.book.domain

data class BookModel(
    val id: String,
    val title: String,
    val imgUrl: String?,
    val authors: List<String>,
    val description: String?,
    val languages: List<String>,
    val firstPublishYear: String?,
    val averageRating: Double?,
    val ratingCount: Int?,
    val numPages: Int?,
    val numEditions: Int,
)