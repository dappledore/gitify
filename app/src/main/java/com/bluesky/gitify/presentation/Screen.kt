package com.bluesky.gitify.presentation

sealed class Screen(val route: String) {
    object UserListScreen: Screen("userList")
    object UserDetailScreen: Screen("userDetail")
    object WebViewScreen: Screen("webview")
}
