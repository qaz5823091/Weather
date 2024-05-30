package com.cppdesign.weather.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleCard(textState: String) {
    Text(
        text = textState,
        textAlign = TextAlign.Center,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp)
            .fillMaxWidth()
    )
}

@Composable
fun SubtitleCard(textState: String) {
    Text(
        text = textState,
        fontSize = 25.sp,
        modifier = Modifier.padding(start = 20.dp,bottom = 10.dp)
    )
}