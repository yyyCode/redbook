package com.example.redbook.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.redbook.viewmodel.ShoppingViewModel
import com.example.redbook.model.Product

@Composable
fun ShoppingScreen(viewModel: ShoppingViewModel = viewModel()) {
    val products by viewModel.products.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Light gray background
    ) {
        // Top Search Bar
        SearchBar(query = searchQuery, onQueryChange = viewModel::onSearchQueryChange)

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalItemSpacing = 8.dp
        ) {
            // Header Section (Icons + Promo Cards)
            item(span = StaggeredGridItemSpan.FullLine) {
                Column {
                    IconNavigation()
                    Spacer(modifier = Modifier.height(16.dp))
                    PromoCards()
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Product Waterfall Grid
            items(products) { product ->
                ProductItem(product)
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Search Input Area
        Box(
            modifier = Modifier
                .weight(1f)
                .height(36.dp)
                .background(Color.White, RoundedCornerShape(18.dp))
                .border(0.5.dp, Color.LightGray, RoundedCornerShape(18.dp))
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (query.isEmpty()) "男生秋冬穿搭" else query,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // More Icon
        Icon(
            imageVector = Icons.Default.MoreHoriz,
            contentDescription = "More",
            tint = Color.Gray
        )
    }
}

@Composable
fun IconNavigation() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NavIconItem("我的订单", Icons.Default.Description)
        NavIconItem("购物车", Icons.Default.ShoppingCart)
        NavIconItem("客服消息", Icons.Default.Face)
        NavIconItem("卡券", Icons.Default.ConfirmationNumber)
        NavIconItem("浏览记录", Icons.Default.Restore)
    }
}

@Composable
fun NavIconItem(title: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(28.dp),
            tint = Color.DarkGray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = title, fontSize = 12.sp, color = Color.DarkGray)
    }
}

@Composable
fun PromoCards() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Left Card: Live Selection
        Card(
            modifier = Modifier.weight(1f).height(120.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0F5)), // Pinkish background
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("直播精选", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("心动好物", color = Color.Gray, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Overlapping avatars
                    Box(contentAlignment = Alignment.Center) {
                         AsyncImage(
                            model = "https://picsum.photos/50/50?random=1",
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.White, CircleShape)
                        )
                    }
                     Spacer(modifier = Modifier.width((-10).dp)) // Overlap
                     Box(contentAlignment = Alignment.Center) {
                         AsyncImage(
                            model = "https://picsum.photos/50/50?random=2",
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .border(1.dp, Color.White, CircleShape)
                        )
                    }
                }
            }
        }

        // Right Card: No Price Comparison
        Card(
            modifier = Modifier.weight(1f).height(120.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF5EE)), // Light orange background
            shape = RoundedCornerShape(8.dp)
        ) {
             Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("不用比价", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("买贵必赔", color = Color.Gray, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                     AsyncImage(
                        model = "https://picsum.photos/60/60?random=3",
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp), // Fixed height for simplicity in staggered grid context, or remove for variable height
                contentScale = ContentScale.Crop
            )
            
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = product.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "¥ ${product.price}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    if (product.originalPrice != null) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "¥${product.originalPrice}",
                            style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.LineThrough),
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "${product.salesCount}人买过",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                
                if (product.tags.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        product.tags.take(2).forEach { tag ->
                            Box(
                                modifier = Modifier
                                    .padding(end = 4.dp)
                                    .border(0.5.dp, Color(0xFF888888), RoundedCornerShape(2.dp))
                                    .padding(horizontal = 2.dp, vertical = 1.dp)
                            ) {
                                Text(text = tag, fontSize = 10.sp, color = Color(0xFF666666))
                            }
                        }
                    }
                }
            }
        }
    }
}
