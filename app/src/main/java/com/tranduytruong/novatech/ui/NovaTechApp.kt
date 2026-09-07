package com.tranduytruong.novatech.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tranduytruong.novatech.core.domain.model.ThemeMode
import com.tranduytruong.novatech.feature.auth.AccountScreen
import com.tranduytruong.novatech.feature.home.StoreViewModel
import com.tranduytruong.novatech.ui.components.glass.GlassBottomBar
import com.tranduytruong.novatech.ui.components.glass.GlassBottomNavItem
import com.tranduytruong.novatech.ui.screens.CartScreen
import com.tranduytruong.novatech.ui.screens.CategoryScreen
import com.tranduytruong.novatech.ui.screens.DetailScreen
import com.tranduytruong.novatech.ui.screens.HomeScreen

private val bottomNavItems = listOf(
    GlassBottomNavItem("home", "Trang chủ", Icons.Rounded.Home),
    GlassBottomNavItem("categories", "Danh mục", Icons.Rounded.Category),
    GlassBottomNavItem("cart", "Giỏ hàng", Icons.Rounded.ShoppingCart),
    GlassBottomNavItem("account", "Tài khoản", Icons.Rounded.Person),
)

@Composable
fun NovaTechApp(
    themeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit,
    vm: StoreViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val navItemsWithBadge = bottomNavItems.map { item ->
        if (item.route == "cart") item.copy(badgeCount = vm.cartCount)
        else item
    }

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            if (currentRoute != null && !currentRoute.startsWith("detail/")) {
                GlassBottomBar(
                    items = navItemsWithBadge,
                    currentRoute = currentRoute,
                    onItemClick = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding),
        ) {
            composable("home") { HomeScreen(navController, vm) }
            composable("categories") { CategoryScreen(navController, vm) }
            composable("cart") { CartScreen(navController, vm) }
            composable("account") {
                AccountScreen(
                    themeMode = themeMode,
                    onThemeModeChange = onThemeModeChange,
                )
            }
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
