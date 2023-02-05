package com.danielyan.fintech.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.danielyan.fintech.InternetError
import com.danielyan.fintech.R
import com.danielyan.fintech.network.*
import com.danielyan.fintech.network.data.MiniFilmResponse
import com.danielyan.fintech.network.data.Service
import com.danielyan.fintech.network.observer.ConnectivityObserver
import io.ktor.client.*




@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PopularScreen(
    navController: NavController,
    connectivityObserver: ConnectivityObserver
) {
    val status by connectivityObserver.observe().collectAsState(
        initial = ConnectivityObserver.Status.Unavailable
    )

    val service = Service.create()
    var films = rememberSaveable() { mutableStateOf(listOf<MiniFilmResponse>()) }
    //var isLoadingDone by remember { mutableStateOf(false) }
    //var isLoadingDone = rememberSaveable() { mutableStateOf(false) }

    if(status == ConnectivityObserver.Status.Available)
        if (films.value.size == 0)
            films.value = produceState<List<MiniFilmResponse>>(
                initialValue = emptyList(),
                producer = {
                    value = service.getFilmsTop(2).films
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
                    colors = ButtonDefaults.textButtonColors(backgroundColor = MaterialTheme.colors.primaryVariant),
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
        if(films.value.size == 0) {
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
                itemsIndexed(films.value) { index,   item ->
                    FilmCard(
                        navController = navController,
                        title = "${item.nameRu}",
                        genre = "${item.genres[0].genre.replaceFirstChar{ it.uppercaseChar() }}",
                        year = "${item.year}",
                        url = "${item.posterUrlPreview}",
                        id = "${item.filmId}")
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
        title = { Text(text = title, fontSize = 27.sp, fontWeight = FontWeight.Bold) },
        elevation = 0.dp,
        //navigationIcon = {
        //    IconButton(onClick = { /* doSomething() */ }) {
        //        Icon(Icons.Filled.ArrowBack, contentDescription = null)
        //    }
        //},
        actions = {
            // RowScope here, so these icons will be placed horizontally
            //IconButton(onClick = { /* doSomething() */ }) {
            //    Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
            //}
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Search, contentDescription = "Localized description", tint = MaterialTheme.colors.primary)
            }
        },
        backgroundColor = Color.Transparent
    )
}

@Composable
fun FilmCard(navController: NavController, title: String, genre: String, year: String, url: String, id: String) {
    Card(
        shape = RoundedCornerShape(15),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("fulldesc/$id")
            },
        elevation = 16.dp,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Surface(shape = RoundedCornerShape(15), modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = null,
                    modifier = Modifier
                        .height(66.dp)
                        .width(44.dp)
                )
            }
            Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(4.dp)) {
                Row(modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = title, maxLines = 1, fontWeight = FontWeight.Bold, overflow = TextOverflow.Ellipsis, modifier = Modifier.width(240.dp))
                    Surface(modifier = Modifier.size(16.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_favorite_star),
                            contentDescription = "Favorite Star",
                            modifier = Modifier.fillMaxSize(),
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
                Text(text = "$genre ($year)", color = MaterialTheme.colors.secondary, fontWeight = FontWeight.Bold, fontSize = 14.sp, modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@Composable
fun ProgressIndicator()  {
    Column(
        modifier  = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp, 50.dp)
        )
    }
}
