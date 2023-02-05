package com.danielyan.fintech

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.danielyan.fintech.network.observer.ConnectivityObserver
import com.danielyan.fintech.network.observer.NetworkConnectivityObserver
import com.danielyan.fintech.screens.FavoriteScreen
import com.danielyan.fintech.screens.FullDescScreen
import com.danielyan.fintech.screens.PopularScreen
import com.danielyan.fintech.ui.theme.FintechTheme

class MainActivity : ComponentActivity() {

    private lateinit var connectivityObserver: ConnectivityObserver
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        setContent {
            FintechTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "popular") {
                    composable("popular") { PopularScreen(navController, connectivityObserver)}
                    composable("favorite") { FavoriteScreen(navController) }
                    composable("fulldesc/{filmId}", arguments =
                    listOf(navArgument("filmId") {
                        type = NavType.IntType
                        defaultValue = 1

                    })) {
                        entry ->
                        entry.arguments?.getInt("filmId")
                            ?.let { FullDescScreen(navController, connectivityObserver, it) }
                    }
                }
            }
        }
    }
}