package com.danielyan.fintech.screens.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.danielyan.fintech.R

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