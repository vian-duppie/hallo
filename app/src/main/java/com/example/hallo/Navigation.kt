package com.example.hallo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hallo.ui.TestScreen
import com.example.hallo.ui.screens.RegisterScreen

enum class AuthRoutes {
    Login,
    Register,
    Test
}

enum class Routes {
    Home
}

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AuthRoutes.Register.name
    ) {
        composable(
            route = AuthRoutes.Test.name
        ) {
            TestScreen()
        }

        composable(
            route = AuthRoutes.Register.name
        ) {
            RegisterScreen()
        }
    }
}