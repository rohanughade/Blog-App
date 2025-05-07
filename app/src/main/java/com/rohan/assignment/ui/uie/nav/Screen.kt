package com.rohan.assignment.ui.uie.nav

import android.net.Uri
import okio.ByteString.Companion.encode

sealed class Screen(val route: String) {
    object mainScreen: Screen("main")
    object webView: Screen("webview/{url}"){
        fun createRoute(url: String) = "webview/${Uri.encode(url)}"

    }
}