package com.example.podii.Presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun Foodinfo(navController: NavHostController){

    var result  = navController.previousBackStackEntry?.savedStateHandle?.get<FoodParcelable>("foodinfo")
    var name  by remember { mutableStateOf("") }
    var author  by remember { mutableStateOf("") }
    var description  by remember { mutableStateOf("") }
    var image  by remember { mutableStateOf("") }
    var likes  by remember { mutableStateOf(0) }
    var context  = LocalContext.current
    var isliked by remember { mutableStateOf(false)}

    if (result != null) {
        name  = result.name
    }
    if (result != null) {
        author  = result.author
    }
    if (result != null) {
        description  = result.description
    }
    if (result != null) {
        image  = result.image
    }
    if (result != null) {
        likes = result.likes
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = {  Text(
            text = name,
            color = Color.White,
            fontFamily = MaterialTheme.typography.body1.fontFamily
        )},backgroundColor = Color("#D14D72".toColorInt()))}
    ) {

       Column(modifier = Modifier
           .fillMaxSize()
           .padding(it)
           .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
           Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 20.dp)) {
               AsyncImage(model = ImageRequest.Builder(context).data(image).build(),
                   error = painterResource(id = com.example.podii.R.drawable.serve),
                   contentDescription = "Upload Image",
                   contentScale = ContentScale.Crop,
                   modifier = Modifier
                       .clip(CircleShape)
                       .width(100.dp)
                       .height(100.dp))
               Spacer(modifier = Modifier.height(15.dp))
               Text(text = "Food Name: $name",style = TextStyle(fontFamily = MaterialTheme.typography.body1.fontFamily, fontSize = 20.sp))
           }


           Column(modifier = Modifier.padding(top = 40.dp)) {
           Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
               Text(text = "Chef: $author", modifier = Modifier.padding(top = 15.dp),style = TextStyle(fontFamily = MaterialTheme.typography.body1.fontFamily, fontSize = 20.sp))

               Row() {
                   Text(text = likes.toString(), modifier = Modifier.padding(top = 15.dp),style = TextStyle(fontFamily = MaterialTheme.typography.body1.fontFamily, fontSize = 20.sp))

                   IconToggleButton(checked = isliked, onCheckedChange ={isliked = it
                     if (isliked){
                         likes++
                     } else {
                         likes--
                     }
                   } ) {
                       if (isliked == false){
                           Icon(painter = painterResource(id = com.example.podii.R.drawable.baseline_favorite_border_24), tint = Color.Gray ,contentDescription = null)
                       } else {
                           Icon(painter = painterResource(id = com.example.podii.R.drawable.baseline_favorite_24), tint = Color.Red ,contentDescription = null)
                       }
                   }

               }

           }
               Spacer(modifier = Modifier.padding(top = 30.dp))
           Text(text = "Description",modifier = Modifier.padding(start = 15.dp),style = TextStyle(fontFamily = MaterialTheme.typography.body1.fontFamily, fontStyle = FontStyle.Italic,fontSize = 20.sp), color = Color.Gray)
           Text(text = description,modifier = Modifier.padding(start = 15.dp),style = TextStyle(fontFamily = MaterialTheme.typography.body1.fontFamily, fontStyle = FontStyle.Italic,fontSize = 20.sp))
       }
       }


    }


}