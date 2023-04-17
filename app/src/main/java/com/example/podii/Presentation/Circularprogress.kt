package com.example.podii.Presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

@Composable
fun Circularprogress(isloading : Boolean){
    if (isloading){
        Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp), horizontalArrangement = Arrangement.Center) {
            CircularProgressIndicator(
                strokeWidth = 5.dp,
                color = Color("#D14D72".toColorInt()),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}