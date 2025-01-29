package com.aman.dryspell

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.aman.dryspell.ui.navigation.BottomNavigation
import com.aman.dryspell.ui.screens.AboutScreen
import com.aman.dryspell.ui.screens.HomeScreen
import com.aman.dryspell.ui.screens.SearchScreen
import com.aman.dryspell.ui.theme.DrySpellTheme
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

class MainApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrySpellTheme {
                AppContent()
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AppContent() {
    val pagerState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope()
    val pages = listOf("home", "search", "about")

    Scaffold(
        bottomBar = {
            BottomNavigation(selectedIndex = pagerState.currentPage) { index ->
                scope.launch {
                    pagerState.animateScrollToPage(index)
                }
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) { page ->
            when (page) {
                0 -> HomeScreen()
                1 -> SearchScreen()
                2 -> AboutScreen()
            }
        }
    }
}
