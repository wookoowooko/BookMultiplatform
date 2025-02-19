package io.wookoo.bookapp.features.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("docs")
    val results: List<SearchedBookDto>
)