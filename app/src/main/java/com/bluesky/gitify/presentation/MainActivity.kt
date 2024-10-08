package com.bluesky.gitify.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bluesky.gitify.presentation.theme.GitifyTheme
import com.bluesky.gitify.presentation.ui.screens.user_detail.UserDetailScreen
import com.bluesky.gitify.presentation.ui.screens.user_detail.WebViewScreen
import com.bluesky.gitify.presentation.ui.screens.user_list.UserListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitifyTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.UserListScreen.route
                    ) {
                        composable(
                            route = Screen.UserListScreen.route
                        ) {
                            UserListScreen(navController)
                        }
                        composable(
                            route = Screen.UserDetailScreen.route + "/{id}"
                        ) {
                            UserDetailScreen(navController)
                        }
                        composable(Screen.WebViewScreen.route + "/{url}") { backStackEntry ->
                            val url = backStackEntry.arguments?.getString("url") ?: ""
                            WebViewScreen(url = url, navController) {

                            }
                        }
                    }
                }
            }
        }
    }
}
