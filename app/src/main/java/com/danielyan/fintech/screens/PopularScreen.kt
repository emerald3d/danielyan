package com.danielyan.fintech.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.danielyan.fintech.network.*
import com.danielyan.fintech.network.data.MiniFilmResponse
import com.danielyan.fintech.network.data.Service
import com.danielyan.fintech.network.observer.ConnectivityObserver
import com.danielyan.fintech.screens.composable.FilmCard
import com.danielyan.fintech.screens.composable.InternetError
import com.danielyan.fintech.screens.composable.ProgressIndicator
import io.ktor.client.*




@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PopularScreen(
    navController: NavController,
    connectivityObserver: ConnectivityObserver
) {
    val service = Service.create()
    val status by connectivityObserver.observe().collectAsState(
        initial = ConnectivityObserver.Status.Unavailable
    )

    val films = rememberSaveable { mutableStateOf(listOf<MiniFilmResponse>()) }

    if(status == ConnectivityObserver.Status.Available)
        if (films.value.isEmpty())
            films.value = produceState<List<MiniFilmResponse>>(
                initialValue = emptyList(),
                producer = {
                    value = service.getFilmsTop(1).films //стандартный запрос API (TOP_100_POPULAR_FILMS) выдает первую страницу
                }
            ).value

    Scaffold(
        topBar = { TopAppBar("Популярные") },
        bottomBar = {
            BottomAppBar(
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(vertical = 16.dp, horizontal = 4.dp)
            ) {
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(50),
                    elevation = null,
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = MaterialTheme.colors.primaryVariant
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(horizontal = 8.dp)
                ){
                    Text("Популярные")
                }
                Button(
                    onClick = {
                        navController.navigate("favorite")
                    },
                    shape = RoundedCornerShape(50),
                    elevation = null,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(horizontal = 8.dp),
                ){
                    Text(text = "Избранное")
                }
            }
        },
        backgroundColor = MaterialTheme.colors.background
    ) {
        if(films.value.isEmpty()) {
            if(status == ConnectivityObserver.Status.Unavailable)
                InternetError()
            else
                ProgressIndicator()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp)
            ) {
                item{
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = Color.Transparent,
                    ) {
                    }
                }
                //.filter { it.nameRu!!.contains("Фа") }
                itemsIndexed(films.value) { _,   item ->
                    FilmCard(
                        navController = navController,
                        title = "${ item.nameRu }",
                        genre = "${ item.genres[0].genre
                            .replaceFirstChar{ it.uppercaseChar() }}",
                        year = "${ item.year }",
                        url = "${ item.posterUrlPreview }",
                        id = "${ item.filmId }")
                }
                item{
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(82.dp),
                        color = Color.Transparent,
                    ) {}
                }
            }
        }
    }
}

@Composable
fun TopAppBar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold)
                },
        elevation = 0.dp,
        actions = {
            IconButton(onClick = { /* поиск :) */ }) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Localized description",
                    tint = MaterialTheme.colors.primary)
            }
        },
        backgroundColor = Color.Transparent
    )
}
