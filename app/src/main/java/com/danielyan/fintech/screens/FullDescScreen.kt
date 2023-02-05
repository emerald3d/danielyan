package com.danielyan.fintech.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
//import androidx.navigation.NavController
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
import coil.compose.AsyncImage
import com.danielyan.fintech.InternetError
import com.danielyan.fintech.network.data.FilmResponse
import com.danielyan.fintech.network.data.Service
import com.danielyan.fintech.network.observer.ConnectivityObserver

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FullDescScreen(
    navController: NavController,
    connectivityObserver: ConnectivityObserver,
    filmId: Int) {

    val scrollState = rememberScrollState()
    val service = Service.create()
    val status by connectivityObserver.observe().collectAsState(
        initial = ConnectivityObserver.Status.Unavailable
    )

    var film = FilmResponse(
        -1,
        "",
        "",
        "",
        0,
        "",
        arrayOf(),
        arrayOf()
    )

    if (status == ConnectivityObserver.Status.Available) {
        film = produceState<FilmResponse>(
            initialValue = FilmResponse(
                -1,
                "",
                "",
                "",
                0,
                "",
                arrayOf(),
                arrayOf()
            ),
            producer = {
                value = service.getFilm(filmId)
            }
        ).value
    } else {
        FilmResponse(
            -1,
            "",
            "",
            "",
            0,
            "",
            arrayOf(),
            arrayOf()
        )
    }

    Surface(modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxHeight()) {
        Surface {
            if (film.year == 0) {
                if(status == ConnectivityObserver.Status.Unavailable) {
                    Scaffold(
                        topBar = { TopAppBar(navController) }
                    ) {
                        InternetError()
                    }
                }
                else
                    Scaffold(
                        topBar = { TopAppBar(navController) }
                    ) {
                        ProgressIndicator()
                    }
            } else {
                Box() {
                    Column(
                        modifier = Modifier
                            .verticalScroll(scrollState)
                    ) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(561.dp)
                        ) {
                            AsyncImage(
                                model =  (film.posterUrl),
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth,
                                onLoading = {

                                },
                            )
                        }
                        Column(modifier = Modifier
                            .padding(horizontal = 32.dp)
                        ) {
                            Surface(modifier = Modifier.height(21.dp))  {}
                            Text(film.nameRu.toString(), fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colors.onBackground)
                            Surface(modifier = Modifier.height(12.dp))  {}
                            Text(film.description.toString(), color = MaterialTheme.colors.secondary)
                            var genreList =  listOf<String>()
                            film.genres.forEach { genreList += it.genre }
                            Text("Жанры: ${genreList.toString().replace("[", "").replace("]", "")}", color = MaterialTheme.colors.secondary, modifier = Modifier.padding(vertical = 4.dp))
                            var countriesList =  listOf<String>()
                            film.countries.forEach { countriesList += it.country }
                            Text("Страны: ${countriesList.toString().replace("[", "").replace("]", "")}", color = MaterialTheme.colors.secondary, modifier = Modifier.padding(vertical = 4.dp))
                            Surface(modifier = Modifier.height(21.dp))  {}
                        }
                    }
                    TopAppBar(navController)
                }
            }
        }
    }
}

@Composable
fun TopAppBar(navController: NavController)  {
    TopAppBar(
        title = { Text("") },
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null, tint = MaterialTheme.colors.primary)
            }
        },
        backgroundColor = Color.Transparent
    )
}