package com.example.redbook.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.redbook.model.mockPosts
import com.example.redbook.ui.components.PostItem
import kotlinx.coroutines.launch

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.redbook.viewmodel.HomeViewModel
import com.example.redbook.model.Post

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val titles = listOf("关注", "发现", "附近")
    val pagerState = rememberPagerState(pageCount = { titles.size }, initialPage = 1)
    val scope = rememberCoroutineScope()
    
    val followPosts by viewModel.followPosts.collectAsState()
    val explorePosts by viewModel.explorePosts.collectAsState()
    val nearbyPosts by viewModel.nearbyPosts.collectAsState()

    Scaffold(
        topBar = {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.White,
                contentColor = Color.Black,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = Color.Red
                    )
                }
            ) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { 
                            Text(
                                text = title,
                                style = if (pagerState.currentPage == index) MaterialTheme.typography.titleMedium else MaterialTheme.typography.bodyMedium,
                                color = if (pagerState.currentPage == index) Color.Black else Color.Gray
                            ) 
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(innerPadding)
        ) { page ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                when (page) {
                    0 -> FollowTab(posts = followPosts)
                    1 -> ExploreTab(posts = explorePosts)
                    2 -> NearbyTab(posts = nearbyPosts)
                }
            }
        }
    }
}

@Composable
fun FollowTab(posts: List<Post>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(8.dp)
    ) {
        items(posts) { post ->
            com.example.redbook.ui.components.PostItem(post = post)
        }
    }
}

@Composable
fun ExploreTab(posts: List<Post>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().padding(4.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(4.dp),
        verticalItemSpacing = 4.dp
    ) {
        items(posts) { post ->
            PostItem(post = post)
        }
    }
}

@Composable
fun NearbyTab(posts: List<Post>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().padding(4.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(4.dp),
        verticalItemSpacing = 4.dp
    ) {
        items(posts) { post ->
            com.example.redbook.ui.components.PostItem(post = post)
        }
    }
}
