package com.example.redbook.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class SuggestedUser(
    val id: Int,
    val name: String,
    val avatarUrl: String,
    val subtitle: String,
    val tag: String? = null,
    val followed: Boolean = false
)

val mockSuggestedUsers = listOf(
    SuggestedUser(1, "宠物小伙伴", "https://picsum.photos/80/80?random=11", "你的专属聊天搭子", "要找你", false),
    SuggestedUser(2, "睡眠好（三里陪拍）", "https://picsum.photos/80/80?random=12", "你近期赞过", null, false),
    SuggestedUser(3, "小陈超爱零条", "https://picsum.photos/80/80?random=13", "你近期赞过", null, false),
    SuggestedUser(4, "ice拿铁", "https://picsum.photos/80/80?random=14", "你近期赞过", null, false)
)

@Composable
fun MessageScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191919))
    ) {
        TopBar()
        ShortcutRow()
        EmptyState()
        SuggestionSection()
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            Text(text = "消息", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "", tint = Color.White, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Icon(imageVector = Icons.Filled.Add, contentDescription = "", tint = Color.White, modifier = Modifier.size(22.dp))
        }
    }
}

@Composable
fun ShortcutRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ShortcutItem(icon = Icons.Filled.Favorite, label = "赞和收藏", bg = Color(0xFF2A2A2A), tint = Color(0xFFFF5A5A))
        ShortcutItem(icon = Icons.Filled.PersonAdd, label = "新增关注", bg = Color(0xFF2A2A2A), tint = Color(0xFF4FB7FF))
        ShortcutItem(icon = Icons.Filled.ChatBubble, label = "评论和@", bg = Color(0xFF2A2A2A), tint = Color(0xFF76D572))
    }
}

@Composable
fun ShortcutItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, bg: Color, tint: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(bg),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = label, tint = tint, modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(imageVector = Icons.Filled.ChatBubble, contentDescription = "", tint = Color(0xFF3A3A3A), modifier = Modifier.size(48.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "暂时没有消息", color = Color(0xFF8A8A8A), fontSize = 12.sp)
    }
}

@Composable
fun SuggestionSection() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "你可能感兴趣的人", color = Color(0xFFB0B0B0), fontSize = 12.sp)
            Text(text = "关闭", color = Color(0xFFB0B0B0), fontSize = 12.sp)
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(mockSuggestedUsers) { user ->
                SuggestionItem(user)
            }
        }
    }
}

@Composable
fun SuggestionItem(user: SuggestedUser) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user.avatarUrl,
            contentDescription = user.name,
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = user.name, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                if (user.tag != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A)),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = user.tag,
                            color = Color(0xFFB0B0B0),
                            fontSize = 10.sp,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = user.subtitle, color = Color(0xFF8A8A8A), fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4D6A)),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(text = if (user.followed) "已关注" else "关注", color = Color.White, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Icon(imageVector = Icons.Filled.Close, contentDescription = "", tint = Color(0xFF6A6A6A), modifier = Modifier.size(18.dp))
    }
}
