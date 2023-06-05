package com.example.hallo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hallo.ui.TestScreen
import com.example.hallo.ui.screens.ConversationScreen
import com.example.hallo.ui.screens.LoginScreen
import com.example.hallo.ui.screens.ProfileScreen
import com.example.hallo.ui.screens.RegisterScreen

enum class AuthRoutes {
    Login,
    Register,
    Test
}

enum class Routes {
    Profile,
    Conversation
}

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Conversation.name
    ) {
        composable(
            route = AuthRoutes.Register.name
        ) {
            RegisterScreen(
                navigateToLogin = {
                    navController.navigate(AuthRoutes.Login.name) {
                        // If the screen is in the stack it will not be loaded again
                        launchSingleTop = true
                        popUpTo(route = AuthRoutes.Login.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = AuthRoutes.Login.name
        ) {
            LoginScreen(
                navigateToRegister = {
                    navController.navigate(AuthRoutes.Register.name) {
                        // If the screen is in the stack it will not be loaded again
                        launchSingleTop = true
                        popUpTo(route = AuthRoutes.Register.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = Routes.Profile.name
        ) {
            ProfileScreen()
        }

        composable(
            route = Routes.Conversation.name
        ) {
            ConversationScreen()
        }
    }
}