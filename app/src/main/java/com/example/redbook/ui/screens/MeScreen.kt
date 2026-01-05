package com.example.redbook.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
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

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.redbook.viewmodel.MeViewModel
import com.example.redbook.viewmodel.UserProfile

@Composable
fun MeScreen(viewModel: MeViewModel = viewModel()) {
    val userProfile by viewModel.userProfile.collectAsState()
    val selectedTab by viewModel.selectedTab.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item { MeTopBar() }
        item { ProfileHeader(userProfile) }
        item { StatRow(userProfile) }
        item { ActionButtons() }
        item { QuickCards() }
        item { TabsSection(selectedTab, viewModel::onTabSelected) }
        item { EmptyCollection() }
    }
}

@Composable
fun MeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Filled.Menu, contentDescription = "", tint = Color.Black, modifier = Modifier.size(22.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEDEDED)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "设置背景", color = Color.Black, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Icon(imageVector = Icons.Filled.Share, contentDescription = "", tint = Color.Black, modifier = Modifier.size(22.dp))
        }
    }
}

@Composable
fun ProfileHeader(userProfile: UserProfile) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            AsyncImage(
                model = userProfile.avatarUrl,
                contentDescription = "",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFF4D6A))
                    .align(Alignment.BottomEnd),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "", tint = Color.White, modifier = Modifier.size(12.dp))
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = userProfile.nickname, color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "小红书号：${userProfile.redbookId}", color = Color(0xFF666666), fontSize = 12.sp)
        }
        Icon(imageVector = Icons.Filled.MoreHoriz, contentDescription = "", tint = Color.Black, modifier = Modifier.size(22.dp))
    }
}

@Composable
fun StatRow(userProfile: UserProfile) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = userProfile.followingCount.toString(), color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(text = "关注", color = Color(0xFF666666), fontSize = 12.sp)
        }
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = userProfile.followersCount.toString(), color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(text = "粉丝", color = Color(0xFF666666), fontSize = 12.sp)
        }
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = userProfile.likesAndCollectionsCount.toString(), color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(text = "获赞与收藏", color = Color(0xFF666666), fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEDEDED)),
            shape = RoundedCornerShape(18.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Icon(imageVector = Icons.Filled.Edit, contentDescription = "", tint = Color.Black, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "编辑资料", color = Color.Black, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEDEDED)),
            shape = RoundedCornerShape(18.dp)
        ) {
            Icon(imageVector = Icons.Filled.Settings, contentDescription = "", tint = Color.Black, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
        }
    }
}

@Composable
fun ActionButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEDEDED)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = "创作灵感", color = Color.Black, fontSize = 14.sp)
                Text(text = "学创作的灵感", color = Color(0xFF666666), fontSize = 12.sp)
            }
        }
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEDEDED)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = "浏览记录", color = Color.Black, fontSize = 14.sp)
                Text(text = "看过的笔记", color = Color(0xFF666666), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun QuickCards() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        QuickCardItem(
            modifier = Modifier.weight(1f),
            icon = Icons.Outlined.Lightbulb,
            title = "创作灵感",
            subtitle = "学创作找灵感"
        )
        QuickCardItem(
            modifier = Modifier.weight(1f),
            icon = Icons.Outlined.History,
            title = "浏览记录",
            subtitle = "看过的笔记"
        )
    }
}

@Composable
fun QuickCardItem(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEDEDED)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = title, color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Text(text = subtitle, color = Color(0xFF666666), fontSize = 10.sp)
            }
        }
    }
}

@Composable
fun TabsSection(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    val titles = listOf("笔记", "收藏", "赞过")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.White,
            contentColor = Color.Black,
            indicator = {}
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) Color.Black else Color(0xFF8A8A8A),
                            fontSize = 14.sp,
                            fontWeight = if (selectedTabIndex == index) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "公开 0  |  私密 0  |  合集 0", color = Color(0xFF8A8A8A), fontSize = 12.sp)
            Icon(imageVector = Icons.Filled.Search, contentDescription = "", tint = Color(0xFF8A8A8A), modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
fun EmptyCollection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Color(0xFF3A3A3A), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Icons.Filled.MoreHoriz, contentDescription = "", tint = Color(0xFF3A3A3A), modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "小红书冲浪新发现", color = Color(0xFF666666), fontSize = 13.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEDEDED)),
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp)
        ) {
            Text(text = "去发布", color = Color.Black, fontSize = 12.sp)
        }
    }
}
