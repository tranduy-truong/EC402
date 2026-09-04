package com.example.techstore.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.techstore.StoreViewModel
import com.example.techstore.ui.screens.AccountScreen
import com.example.techstore.ui.screens.CartScreen
import com.example.techstore.ui.screens.CategoryScreen
import com.example.techstore.ui.screens.DetailScreen
import com.example.techstore.ui.screens.HomeScreen

private data class BottomDestination(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
)

private val bottomDestinations = listOf(
    BottomDestination("home", "Trang chủ", Icons.Default.Home),
    BottomDestination("categories", "Danh mục", Icons.Default.Category),
    BottomDestination("cart", "Giỏ hàng", Icons.Default.ShoppingCart),
    BottomDestination("account", "Tài khoản", Icons.Default.Person),
)

@Composable
fun TechStoreApp(vm: StoreViewModel = viewModel()) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != null && !currentRoute.startsWith("detail/")) {
                TechStoreBottomBar(navController, currentRoute, vm.cartCount)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding),
        ) {
            composable("home") { HomeScreen(navController, vm) }
            composable("categories") { CategoryScreen(navController, vm) }
            composable("cart") { CartScreen(navController, vm) }
            composable("account") { AccountScreen() }
            composable(
                route = "detail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType }),
            ) { entry ->
                vm.product(entry.arguments?.getInt("id") ?: 0)?.let {
                    DetailScreen(navController, vm, it)
                }
            }
        }
    }
}

@Composable
private fun TechStoreBottomBar(
    navController: NavHostController,
    currentRoute: String,
    cartCount: Int,
) {
    NavigationBar {
        bottomDestinations.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = {
                    Text(if (item.route == "cart" && cartCount > 0) "Giỏ ($cartCount)" else item.label)
                },
            )
        }
    }
}
