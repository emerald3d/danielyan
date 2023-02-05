package com.danielyan.fintech

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import io.ktor.client.*

//import com.google.accompanist.navigation.animation.AnimatedNavHost
//import com.google.accompanist.navigation.animation.rememberAnimatedNavController
//import com.google.accompanist.navigation.animation.composable

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)

    private lateinit var connectivityObserver: ConnectivityObserver

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        setContent {
            FintechTheme {
                val navController = rememberNavController()
                val screens = listOf("popular", "favorite", "fulldesc")
                // A surface container using the 'background' color from the t heme
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


    @Composable
    fun InternetError() {
        Column(
            modifier  = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_internet_error),
                contentDescription = "Internet Error Image",
                modifier = Modifier.width(103.dp),
                tint = MaterialTheme.colors.primary
            )
            Text(
                text = "Произошла ошибка при загрузке данных, проверьте подключение к сети",
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
            Button(
                onClick = {},
                shape = RoundedCornerShape(50),
                elevation = null,
                modifier = Modifier
                    .padding(16.dp)
                    .height(50.dp)
                    .width(135.dp)
            ) {
                Text(text = "Повторить")
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        FintechTheme {
            Scaffold(
                topBar = { },
                bottomBar = { }
            ) {
                Text("dfgdfgdfg")
            }
        }
    }