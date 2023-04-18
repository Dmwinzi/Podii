package com.example.podii.Presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodParcelable(
    var image : String ="",
    var name : String = "",
    var description : String = "",
    var author : String = "",
    var likes : Int = 0
) : Parcelable
