package com.bluesky.gitify.presentation.ui.screens.user_detail

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController


@Composable
fun WebViewScreen(url: String, navController: NavController, onClose: () -> Unit) {
    var showWebView by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(true) }

    if (showWebView) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background) // Set a background color
        ) {
            Column {

                // Close button
                Button(
                    onClick = {
                        showWebView = false // Hide the WebView
                        navController.popBackStack()
                        onClose() // Trigger the onClose action (if needed)
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Close")
                }


                // WebView
                AndroidView(
                    factory = { context ->
                        WebView(context).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            loadUrl(url)
                            // Set a WebViewClient to listen for page load events
                            webViewClient = object : WebViewClient() {
                                override fun onPageFinished(view: WebView?, url: String?) {
                                    super.onPageFinished(view, url)
                                    isLoading = false // Content is loaded, set loading to false
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }


            // Show loading indicator if content is loading
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

