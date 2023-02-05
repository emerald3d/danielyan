package com.danielyan.fintech.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(
    navController: NavController
) {
    Scaffold(
        topBar = { TopAppBar("Избранное") },
        bottomBar = {
            BottomAppBar(
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(vertical = 16.dp, horizontal = 4.dp)
            ) {
                Button(
                    onClick = {
                        navController.navigate("popular")
                    },
                    shape = RoundedCornerShape(50),
                    elevation = null,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(horizontal = 8.dp)
                ){
                    Text("Популярные")
                }
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
                    .padding(horizontal = 8.dp),
                ){
                    Text(text = "Избранное")
                }
            }
        },
        backgroundColor = MaterialTheme.colors.background
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 8.dp)
        ) {
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = Color.Transparent,
                ) {}

                //FilmCard(navController)

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
