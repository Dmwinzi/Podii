package com.example.podii.Presentation

sealed class Screens(var route : String){

    object Splashscreen : Screens("splashscreen")

    object Foods : Screens("foods")

    object Foodinfo : Screens("foodinfo")

    object Upload : Screens("upload")

}
