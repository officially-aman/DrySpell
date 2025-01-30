package com.aman.dryspell

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.aman.dryspell.ui.theme.DrySpellTheme
import com.aman.dryspell.ui.navigation.AppNavigation
import com.aman.dryspell.ui.navigation.BottomNavigation
import com.aman.dryspell.ui.screens.AboutScreen
import com.aman.dryspell.ui.screens.HomeScreen
import com.aman.dryspell.ui.screens.SearchScreen
import kotlinx.coroutines.launch

class MainApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrySpellTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(drawerState, navController)
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
                BottomNavigation(selectedIndex = 0) { index -> }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                AppNavigation(navController)
            }
        }
    }
}

@Composable
fun DrawerContent(drawerState: DrawerState, navController: androidx.navigation.NavController) {
    val scope = rememberCoroutineScope()

    val menuItems = listOf(
        DrawerMenuItem(
            id = "home",
            title = stringResource(id = R.string.menu_home),
            icon = R.drawable.ic_home
        ),
        DrawerMenuItem(
            id = "settings",
            title = stringResource(id = R.string.action_settings),
            icon = R.drawable.baseline_settings_24
        ),
        DrawerMenuItem(
            id = "logout",
            title = stringResource(id = R.string.menu_LogOut),
            icon = R.drawable.baseline_logout_24
        )
    )

    ModalDrawerSheet {
        Text(
            text = "DrySpell",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp),
            fontSize = 30.sp
        )

        LazyColumn {
            items(menuItems) { item ->
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        when (item.id) {
                            "home" -> navController.navigate("home")
                            "settings" -> navController.navigate("settings")
                            "logout" -> {
                                // Handle logout logic here
                            }
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    }
}

data class DrawerMenuItem(
    val id: String,
    val title: String,
    val icon: Int
)
