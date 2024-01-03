package com.example.movieappcleanarichitecture.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingWidget(modifier: Modifier = Modifier, progressAlignment: Alignment = Alignment.Center){
    Box(modifier = modifier.fillMaxSize(), contentAlignment = progressAlignment) {
        CircularProgressIndicator(color = Color.White)
    }
}