package com.example.podii.Presentation

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.podii.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Upload(viewModel: Mainviewmodel){

    var imageuri by remember { mutableStateOf<Uri?>(null) }
    var name  by remember { mutableStateOf("")}
    var description  by remember { mutableStateOf("")}
    var author  by remember { mutableStateOf("")}
    var likes  by remember { mutableStateOf(0)}
    var permission  = rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
    var context  = LocalContext.current
    var isliked by remember { mutableStateOf(false)}
    var lifecycleOwner = LocalLifecycleOwner.current
    var scope = rememberCoroutineScope()
    var isloading by remember { mutableStateOf(false)}

    var launcher  = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
        imageuri  = it
    }



    DisposableEffect(key1 = lifecycleOwner, effect ={
        var lifecycleEventObserver = LifecycleEventObserver { _, event ->
            if(event == Lifecycle.Event.ON_START){
                permission.launchPermissionRequest()
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleEventObserver)
        onDispose { lifecycleOwner.lifecycle.removeObserver(lifecycleEventObserver)}
    } )

    Column(modifier = Modifier
        .background(Color("#D14D72".toColorInt()))
        .verticalScroll(rememberScrollState())) {
       Column(modifier = Modifier
           .fillMaxSize()
           ) {
           Text(text = "Hello chef,", color = Color.White,modifier = Modifier.padding(start = 15.dp, top = 15.dp),style = TextStyle(fontFamily = MaterialTheme.typography.body1.fontFamily, fontSize = 25.sp))
           Text(text = "Upload your favourite recipe", color = Color.White,modifier = Modifier.padding(start = 15.dp),style = TextStyle(fontFamily = MaterialTheme.typography.body1.fontFamily, fontSize = 20.sp))
       }
   
        Spacer(modifier = Modifier.height(20.dp))
        
      Card(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp), modifier = Modifier.fillMaxSize(),backgroundColor = Color.White) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(50.dp))
            AsyncImage(model = ImageRequest.Builder(context).data(imageuri).build(),
                error = painterResource(id = R.drawable.serve),
                contentDescription = "Upload Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clickable(onClick = { launcher.launch("image/*") })
                    .clip(CircleShape)
                    .width(100.dp)
                    .height(100.dp))
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(value = name, onValueChange ={name = it}, label = { Text(text = "Meal name")})
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(value = author, onValueChange ={author = it}, label = { Text(text = "author")})
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(value = description, modifier = Modifier.width(300.dp) ,onValueChange ={description = it}, maxLines = 1,label = { Text(text = "Meal description")})
            Spacer(modifier = Modifier.height(30.dp))
            Circularprogress(isloading = isloading)
            Spacer(modifier = Modifier.height(30.dp))
            Button(onClick = {
                isloading = true
                  if (imageuri== null || name.isEmpty() || author.isEmpty() || description.isEmpty()){
                      isloading = false
                      Toast.makeText(context,"All fields are required",Toast.LENGTH_LONG).show()
                  } else {
                     try {
                         scope.launch {
                             var result =  viewModel.uploadfoods(imageuri,name,description, author, likes)
                             if (result){
                                 isloading = false
                                 Toast.makeText(context,"Uploaded succesfully",Toast.LENGTH_LONG).show()
                             } else {
                                 isloading = true
                             }

                         }
                     }catch (e : Exception){
                         isloading  = false
                         Toast.makeText(context,"Error ${e.message}",Toast.LENGTH_LONG).show()
                     }
                  }
            }, colors =  ButtonDefaults.buttonColors(Color("#D14D72".toColorInt())), modifier = Modifier.width(150.dp),shape = RoundedCornerShape(10.dp) ) {
                Text(text = "Upload", style = TextStyle(color = Color.White),fontFamily = MaterialTheme.typography.button.fontFamily)
            }
            
        }
      }
        
    }

}