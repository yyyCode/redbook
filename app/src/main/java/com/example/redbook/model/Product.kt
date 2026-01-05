package com.example.redbook.model

data class Product(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val price: Double,
    val originalPrice: Double?,
    val salesCount: Int,
    val tags: List<String> = emptyList()
)

val mockProducts = listOf(
    Product(1, "小米 15", "https://picsum.photos/300/400", 423.8, 460.9, 476, listOf("退货包运费")),
    Product(2, "谷歌 Pixel 6", "https://picsum.photos/300/500", 865.0, 881.5, 963, listOf("假一赔十")),
    Product(3, "苹果 iPhone 13", "https://picsum.photos/300/300", 695.0, 787.4, 886, emptyList()),
    Product(4, "索尼相机", "https://picsum.photos/300/450", 1200.0, 1500.0, 120, listOf("正品保证")),
    Product(5, "机械键盘", "https://picsum.photos/300/350", 150.0, 200.0, 300, listOf("极速退款")),
    Product(6, "无线鼠标", "https://picsum.photos/300/550", 50.0, 80.0, 1000, emptyList()),
    Product(7, "降噪耳机", "https://picsum.photos/300/400", 250.0, 300.0, 500, listOf("运费险")),
    Product(8, "智能手表", "https://picsum.photos/300/600", 300.0, 400.0, 200, listOf("七天无理由")),
    Product(9, "平板电脑", "https://picsum.photos/300/320", 800.0, 900.0, 150, emptyList()),
    Product(10, "移动电源", "https://picsum.photos/300/480", 80.0, 100.0, 600, listOf("一年质保"))
)
