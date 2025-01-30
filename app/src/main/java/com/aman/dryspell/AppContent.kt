package com.aman.dryspell

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.aman.dryspell.ui.navigation.AppNavigation
import com.aman.dryspell.ui.navigation.BottomNavigation
import com.aman.dryspell.ui.screens.AboutScreen
import com.aman.dryspell.ui.screens.HomeScreen
import com.aman.dryspell.ui.screens.SearchScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
    val pagerState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope()
    val pages = listOf("home", "search", "about")
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Surface(color = MaterialTheme.colorScheme.surface) {
                Text("Settings", modifier = Modifier.padding(16.dp))
                Button(
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("settings")
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Settings")
                }
                Text("Profile", modifier = Modifier.padding(16.dp))
                Button(
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("profile")
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Profile")
                }
                Text("Log Out", modifier = Modifier.padding(16.dp))
                Button(
                    onClick = {
                        scope.launch { drawerState.close() }
                        // Handle log out logic here
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Log Out")
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("DrySpell") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_menu),
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            },
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
            AppNavigation(navController)
        }
    }
}