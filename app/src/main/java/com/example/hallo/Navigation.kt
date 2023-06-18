package com.example.hallo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hallo.ui.screens.ConversationScreen
import com.example.hallo.ui.screens.IndividualChatScreen
import com.example.hallo.ui.screens.LoginScreen
import com.example.hallo.ui.screens.ProfileScreen
import com.example.hallo.ui.screens.RegisterScreen
import com.example.hallo.viewModels.AuthViewModel

enum class AuthRoutes {
    Login,
    Register,
    Test
}

enum class Routes {
    Profile,
    Conversation,
    Chat,
    ChatsLayout
}

@Composable
fun Navigation(
    authViewModel: AuthViewModel,
    navController: NavHostController = rememberNavController()
) {
    val startScreen =
        if(authViewModel.hasUser) {
            Routes.Conversation.name
        } else {
            AuthRoutes.Login.name
        }

    NavHost(
        navController = navController,
        startDestination = startScreen
    ) {
        composable(
            route = AuthRoutes.Register.name
        ) {
            RegisterScreen(
                authViewModel = authViewModel,
                navigateToLogin = {
                    navController.navigate(AuthRoutes.Login.name) {
                        // If the screen is in the stack it will not be loaded again
                        launchSingleTop = true
                        popUpTo(route = AuthRoutes.Register.name) {
                            inclusive = true
                        }
                    }
                },
                navigateToConversation = {
                    navController.navigate(Routes.Conversation.name) {
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
            route = AuthRoutes.Login.name
        ) {
            LoginScreen(
                authViewModel = authViewModel,
                navigateToRegister = {
                    navController.navigate(AuthRoutes.Register.name) {
                        // If the screen is in the stack it will not be loaded again
                        launchSingleTop = true
                        popUpTo(route = AuthRoutes.Login.name) {
                            inclusive = true
                        }
                    }
                },
                navigateToConversation = {
                    navController.navigate(Routes.Conversation.name) {
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
            route = Routes.Profile.name
        ) {
            ProfileScreen(
                navigateOnSignOut = {
                    navController.navigate(AuthRoutes.Login.name){
                        launchSingleTop = true
                        popUpTo(route = Routes.Conversation.name) {
                            inclusive = true
                        }
                    }
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Routes.Conversation.name
        ) {
            LaunchedEffect(key1 = true) {
                if (!authViewModel.hasUser) {
                    navController.navigate(AuthRoutes.Login.name) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            }
            ConversationScreen(

                navigateToProfile = {
                    navController.navigate(Routes.Profile.name){
                        launchSingleTop = true
                    }
                },
                navigateToChat = { chatId, title ->
                    navController.navigate("${Routes.Chat.name}/$chatId/$title"){
                        launchSingleTop = true
                    }
                }

            )
        }

        composable(
            route = "${Routes.Chat.name}/{chatId}/{title}",
            arguments = listOf(
                navArgument("chatId"){
                    type = NavType.StringType;
                    defaultValue = "chat1"
                },
                navArgument("title") {
                    type = NavType.StringType
                    defaultValue = "defaultTitle"
                }
            )
        ) {
            IndividualChatScreen(
                chatId = it.arguments?.getString("chatId"),
                title = it.arguments?.getString("title"),
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

    }
}