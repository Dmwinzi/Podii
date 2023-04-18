package com.example.podii.Presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.podii.Domain.Entity.Foodentity
import com.example.podii.R
@Composable
fun Foods(viewModel: Mainviewmodel,navController: NavHostController){

    var foods  = viewModel._foods.collectAsState()
    var searchtext  by remember { mutableStateOf("")}
    var filteredfoods  by remember { mutableStateOf(listOf<Foodentity>()) }
    var context = LocalContext.current
    var showsearchbar  by remember { mutableStateOf(false)}
    var showtitle by remember { mutableStateOf(false)}
    if (searchtext.isEmpty()){
        filteredfoods  = foods.value
    }  else {
        filteredfoods  = foods.value.filter { 
            it.name.contains(searchtext,ignoreCase = true)
        }
    }
    var lifecycleOwner  = LocalLifecycleOwner.current

    var listsize  = filteredfoods.size
    var leftlist = filteredfoods.subList(0,listsize/2)
    var rightlist = filteredfoods.subList(listsize/2,listsize)


    DisposableEffect(key1 = lifecycleOwner){
        var LifecycleEventObserver  = LifecycleEventObserver {_, event ->
            if (event == Lifecycle.Event.ON_START){
                showtitle = true
            } else{
                showtitle = true
            }
        }
        lifecycleOwner.lifecycle.addObserver(LifecycleEventObserver)
        onDispose { lifecycleOwner.lifecycle.removeObserver(LifecycleEventObserver) }
    }

  Scaffold(modifier = Modifier.fillMaxSize(),
      topBar = { TopAppBar(
          title = {
              AnimatedVisibility(visible = showtitle) {
                  Text(
                      text = "Meal Time",
                      color = Color.White,
                      fontFamily = MaterialTheme.typography.body1.fontFamily
                  )
              }
              AnimatedVisibility(visible = showsearchbar) {
                  OutlinedTextField(modifier = Modifier
                      .height(50.dp)
                      .fillMaxWidth()
                      ,value = searchtext, onValueChange = {searchtext = it}, placeholder = { Text(text = "Search", color = Color.White)}, trailingIcon = {
                          IconButton(onClick = { searchtext = ""
                              showtitle  = true
                              showsearchbar = false
                          }) {
                              Icon(painter = painterResource(id = R.drawable.baseline_cancel_24) , contentDescription = null, tint = Color.White)
                          }
                      },singleLine = true, shape = RoundedCornerShape(15.dp), colors = TextFieldDefaults.outlinedTextFieldColors(
                          focusedBorderColor = Color.Transparent,
                          unfocusedBorderColor = Color.Transparent,
                          cursorColor = Color.White
                      ))
              }
          },

          actions = {
              IconButton(onClick = { showsearchbar = true
                     showtitle  = false
              }) {
                  Icon(painter = painterResource(id = R.drawable.baseline_search_24), tint = Color.White,contentDescription = null)
              }
          },
          backgroundColor = Color("#D14D72".toColorInt()),
      )},
      floatingActionButton = { FloatingActionButton(onClick = { navController.navigate(Screens.Upload.route)},
          backgroundColor = Color("#D14D72".toColorInt())
      ) {
         FabPosition.End
         Icon(painter = painterResource(id = R.drawable.baseline_add_24), contentDescription = null , tint = Color.White)
      }}
  ) {
      var windowinfo  = rememberwindowinfo()
      if (windowinfo.screenWidthinfo is Windowinfo.Windowtype.Compact){
          LazyColumn(modifier = Modifier.padding(it)){
              item {
                  Column(modifier = Modifier.fillMaxWidth()) {
                      Text(text = "WELCOME,",modifier = Modifier.padding(start = 15.dp, top = 15.dp),style = TextStyle(fontFamily = MaterialTheme.typography.body1.fontFamily, fontSize = 25.sp))
                      Text(text = "Find your favourite meal today",
                          modifier = Modifier.padding(start = 15.dp),style = TextStyle(fontFamily = MaterialTheme.typography.body1.fontFamily, fontSize = 20.sp), color = Color.Gray)
                  }
              }

              items(filteredfoods){item ->
                  Row(modifier = Modifier.fillMaxWidth().clickable(onClick = {
                      var foodParcelable  = FoodParcelable(item.image,item.name,item.description,item.author,item.likes)
                      navController.currentBackStackEntry?.savedStateHandle?.set(
                          "foodinfo",
                          foodParcelable
                      )
                      navController.navigate(Screens.Foodinfo.route)
                  })
                  ) {
                      Card(modifier = Modifier
                          .padding(10.dp)
                          .fillMaxWidth()
                          .height(90.dp), shape = RoundedCornerShape(10.dp)) {
                          Row(modifier = Modifier.padding(start = 15.dp, top = 10.dp)) {
                              AsyncImage(model = ImageRequest.Builder(context).data(item.image).build(), contentDescription = null,
                                  error = painterResource(id = R.drawable.food),
                                  contentScale = ContentScale.Crop,
                                  modifier = Modifier
                                      .clip(CircleShape)
                                      .width(60.dp)
                                      .height(60.dp)
                              )
                              Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.padding(start = 20.dp, end = 10.dp)) {
                                  Text(text = item.name, fontFamily = MaterialTheme.typography.body1.fontFamily, fontSize = 25.sp)
                                  Spacer(modifier = Modifier.height(5.dp))
                                  Text(text = item.author, style = TextStyle(fontFamily = MaterialTheme.typography.body1.fontFamily, fontStyle = FontStyle.Italic))
                              }

                          }
                      }
                  }
              }

          }
      } else if (windowinfo.screenWidthinfo is Windowinfo.Windowtype.Medium){
          LazyColumn(modifier = Modifier.padding(it)){
              item {
                  Column(modifier = Modifier.fillMaxWidth()) {
                      Text(text = "WELCOME,",modifier = Modifier.padding(start = 15.dp, top = 15.dp),style = TextStyle(fontFamily = MaterialTheme.typography.body1.fontFamily, fontSize = 25.sp))
                      Text(text = "Find your favourite meal today",
                          modifier = Modifier.padding(start = 15.dp),style = TextStyle(fontFamily = MaterialTheme.typography.body1.fontFamily, fontSize = 20.sp), color = Color.Gray)
                  }
              }

              items(filteredfoods){item ->
                  Row(modifier = Modifier.fillMaxWidth().clickable(onClick = {
                      var foodParcelable  = FoodParcelable(item.image,item.name,item.description,item.author,item.likes)
                      navController.currentBackStackEntry?.savedStateHandle?.set(
                          "foodinfo",
                          foodParcelable
                      )
                      navController.navigate(Screens.Foodinfo.route)
                  })) {
                      Card(modifier = Modifier
                          .padding(10.dp)
                          .fillMaxWidth()
                          .height(90.dp), shape = RoundedCornerShape(10.dp)) {
                          Row(modifier = Modifier.padding(start = 15.dp, top = 10.dp)) {
                              AsyncImage(model = ImageRequest.Builder(context).data(item.image).build(), contentDescription = null,
                                  error = painterResource(id = R.drawable.food),
                                  contentScale = ContentScale.Crop,
                                  modifier = Modifier
                                      .clip(CircleShape)
                                      .width(60.dp)
                                      .height(60.dp)
                              )
                              Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.padding(start = 20.dp, end = 10.dp)) {
                                  Text(text = item.name, fontFamily = MaterialTheme.typography.body1.fontFamily, fontSize = 25.sp)
                                  Spacer(modifier = Modifier.height(5.dp))
                                  Text(text = item.author, style = TextStyle(fontFamily = MaterialTheme.typography.body1.fontFamily, fontStyle = FontStyle.Italic))
                              }

                          }
                      }
                  }
              }

          }
      } else  {
          Column(modifier = Modifier.fillMaxSize()) {
                  Column(modifier = Modifier.fillMaxWidth()) {
                      Text(
                          text = "WELCOME,",
                          modifier = Modifier.padding(start = 15.dp, top = 15.dp),
                          style = TextStyle(
                              fontFamily = MaterialTheme.typography.body1.fontFamily,
                              fontSize = 25.sp
                          )
                      )
                      Text(
                          text = "Find your favourite meal today",
                          modifier = Modifier.padding(start = 15.dp),
                          style = TextStyle(
                              fontFamily = MaterialTheme.typography.body1.fontFamily,
                              fontSize = 20.sp
                          ),
                          color = Color.Gray
                      )
                  }
              Row() {
                  LazyColumn(modifier = Modifier.fillMaxHeight()){
                      items(leftlist){ item ->
                          Row(modifier = Modifier.fillMaxWidth()
                              .clickable(onClick = {
                                  var foodParcelable  = FoodParcelable(item.image,item.name,item.description,item.author,item.likes)
                                  navController.currentBackStackEntry?.savedStateHandle?.set(
                                      "foodinfo",
                                      foodParcelable
                                  )
                                  navController.navigate(Screens.Foodinfo.route)
                              })
                          ) {
                              Card(modifier = Modifier
                                  .padding(10.dp)
                                  .fillMaxWidth()
                                  .height(90.dp), shape = RoundedCornerShape(10.dp)) {
                                  Row(modifier = Modifier.padding(start = 15.dp, top = 10.dp)) {
                                      AsyncImage(model = ImageRequest.Builder(context).data(item.image).build(), contentDescription = null,
                                          error = painterResource(id = R.drawable.food),
                                          contentScale = ContentScale.Crop,
                                          modifier = Modifier
                                              .clip(CircleShape)
                                              .width(60.dp)
                                              .height(60.dp)
                                      )
                                      Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.padding(start = 20.dp, end = 10.dp)) {
                                          Text(text = item.name, fontFamily = MaterialTheme.typography.body1.fontFamily, fontSize = 25.sp)
                                          Spacer(modifier = Modifier.height(5.dp))
                                          Text(text = item.author, style = TextStyle(fontFamily = MaterialTheme.typography.body1.fontFamily, fontStyle = FontStyle.Italic))
                                      }

                                  }
                              }
                          }
                   }
                  }
              }
              Row() {
                  LazyColumn(){
                        items(rightlist){item ->
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Card(modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                                    .height(90.dp), shape = RoundedCornerShape(10.dp)) {
                                    Row(modifier = Modifier.padding(start = 15.dp, top = 10.dp)) {
                                        AsyncImage(model = ImageRequest.Builder(context).data(item.image).build(), contentDescription = null,
                                            error = painterResource(id = R.drawable.food),
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .clip(CircleShape)
                                                .width(60.dp)
                                                .height(60.dp)
                                        )
                                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.padding(start = 20.dp, end = 10.dp)) {
                                            Text(text = item.name, fontFamily = MaterialTheme.typography.body1.fontFamily, fontSize = 25.sp)
                                            Spacer(modifier = Modifier.height(5.dp))
                                            Text(text = item.author, style = TextStyle(fontFamily = MaterialTheme.typography.body1.fontFamily, fontStyle = FontStyle.Italic))
                                        }

                                    }
                                }
                            }
                        }
                  }
              }
          }
      }

  }


}