package com.example.podii.Presentation

import android.os.Build
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.delay
import com.example.podii.R
@Composable
fun Splashscreen(navController: NavHostController){

    var islaunched by remember { mutableStateOf(false)}
    var lifecycleOwner  = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect ={
        var lifecycleEventObserver = LifecycleEventObserver { _, event ->
            if(event == Lifecycle.Event.ON_START){
                islaunched = true
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleEventObserver)
        onDispose { lifecycleOwner.lifecycle.removeObserver(lifecycleEventObserver)}
    } )


    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {


        val context = LocalContext.current

        val imageLoader  = ImageLoader.Builder(context).components {

            if(Build.VERSION.SDK_INT >= 28){
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }

        }.build()

        Image(painter = rememberAsyncImagePainter(ImageRequest.Builder(context).data(data = R.drawable.meal).apply { size(
            Size.ORIGINAL)}.build(), imageLoader = imageLoader), contentDescription = null)
        LaunchedEffect(key1 = true){
            delay(5000L)
            navController.navigate(Screens.Foods.route)

        }

        Spacer(modifier = Modifier.width(20.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

            AnimatedVisibility(visible = islaunched, enter = slideInHorizontally(animationSpec = tween(500))) {
                Text(text = "Meal", style = TextStyle(fontSize = 30.sp, color = Color.Black,fontFamily = MaterialTheme.typography.body1.fontFamily, fontWeight = FontWeight.Bold))
            }

            Spacer(modifier = Modifier.width(10.dp))

            AnimatedVisibility(visible = islaunched, enter = slideInHorizontally(animationSpec = tween(1000),initialOffsetX = {200})) {
                Text(text = "attack", style = TextStyle(fontSize = 30.sp, fontFamily = MaterialTheme.typography.body1.fontFamily, fontStyle = FontStyle.Italic, color = Color("#D14D72".toColorInt())))
            }

        }

    }

}