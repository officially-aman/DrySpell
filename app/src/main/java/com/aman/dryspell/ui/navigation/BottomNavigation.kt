package com.aman.dryspell.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.aman.dryspell.R

sealed class BottomNavItem(val title: String, val icon: Int, val route: String) {
    object Home : BottomNavItem("Home", R.drawable.ic_home, "home")
    object Search : BottomNavItem("Search", R.drawable.ic_search, "search")
    object About : BottomNavItem("About", R.drawable.ic_about, "about")
}

@Composable
fun BottomNavigation(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.About
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title, fontSize = 12.sp) },
                selected = index == selectedIndex,
                onClick = { onItemSelected(index) }
            )
        }
    }
}
