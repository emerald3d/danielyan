package com.danielyan.fintech.screens.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.danielyan.fintech.R

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