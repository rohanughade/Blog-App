package com.rohan.assignment.ui.uie.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rohan.assignment.ui.uie.screen.mainScreen
import com.rohan.assignment.ui.uie.screen.webView
import com.rohan.assignment.ui.uie.viewmodel.MainViewModel

@Composable
fun navHost(navController: NavHostController,startDestination: String = Screen.mainScreen.route,viewModel: MainViewModel) {
    NavHost(navController=navController, startDestination = startDestination){
        composable(Screen.mainScreen.route) {
            mainScreen(
                viewModel = viewModel,
                onClick = {url->
                    navController.navigate(Screen.webView.createRoute(url))
                }
            )
        }

        composable (route = Screen.webView.route,
            arguments = listOf(navArgument ("url"){ type= NavType.StringType })){
            val url = it.arguments?.getString("url")?:"https://drive.google.com/file/d/1nOUIqnuwPw1tdiohZ-8bZaWJArrXNjRb/view"
            webView(url)
        }

    }

}