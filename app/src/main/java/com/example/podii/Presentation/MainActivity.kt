package com.example.podii

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.podii.Presentation.*
import com.example.podii.ui.theme.PodiiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PodiiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var viewModel  : Mainviewmodel  = hiltViewModel()
                    Navigate(viewModel)
                }
            }
        }
    }
}


@Composable
fun Navigate(viewModel: Mainviewmodel){
    var navController  = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.Splashscreen.route ){
        composable(Screens.Splashscreen.route){
            Splashscreen(navController = navController)
        }
        composable(Screens.Foods.route){
             Foods(viewModel, navController)
        }

        composable(Screens.Foodinfo.route){
              Foodinfo(navController)
        }

        composable(Screens.Upload.route){
            Upload(viewModel)
        }

    }

}