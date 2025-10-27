package com.tuorg.notasmultimedia.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tuorg.notasmultimedia.ui.screens.DetailScreen
import com.tuorg.notasmultimedia.ui.screens.EditScreen
import com.tuorg.notasmultimedia.ui.screens.HomeScreen
import com.tuorg.notasmultimedia.ui.screens.SettingsScreen   // ⬅️ IMPORT NECESARIO

object Routes {
    const val HOME = "home"
    const val EDIT = "edit"
    const val DETAIL = "detail/{id}"
    const val SETTINGS = "settings"
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(nav = navController)
        }
        composable(Routes.EDIT) {
            EditScreen(nav = navController)
        }
        composable(Routes.DETAIL) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            DetailScreen(nav = navController, id = id)
        }
        composable(Routes.SETTINGS) {
            SettingsScreen()
        }
    }
}
