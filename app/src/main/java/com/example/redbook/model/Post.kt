package com.example.redbook.model

data class Post(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val authorName: String,
    val authorAvatarUrl: String,
    val likeCount: Int,
    val isLiked: Boolean = false
)

val mockPosts = listOf(
    Post(1, "https://picsum.photos/300/400", "Mountain View", "Traveler", "https://picsum.photos/50/50", 120),
    Post(2, "https://picsum.photos/300/500", "Delicious Food", "Foodie", "https://picsum.photos/50/50", 85),
    Post(3, "https://picsum.photos/300/300", "Cute Cat", "CatLover", "https://picsum.photos/50/50", 200),
    Post(4, "https://picsum.photos/300/450", "Coding Life", "Dev", "https://picsum.photos/50/50", 50),
    Post(5, "https://picsum.photos/300/350", "Art Gallery", "Artist", "https://picsum.photos/50/50", 150),
    Post(6, "https://picsum.photos/300/550", "Fashion Style", "Fashionista", "https://picsum.photos/50/50", 300),
    Post(7, "https://picsum.photos/300/400", "Nature", "NatureLover", "https://picsum.photos/50/50", 90),
    Post(8, "https://picsum.photos/300/600", "Architecture", "Arch", "https://picsum.photos/50/50", 110),
    Post(9, "https://picsum.photos/300/320", "Coffee Time", "Barista", "https://picsum.photos/50/50", 75),
    Post(10, "https://picsum.photos/300/480", "Book Club", "Reader", "https://picsum.photos/50/50", 45)
)
