package com.example.podii.Domain.Repository

import android.net.Uri
import com.example.podii.Domain.Entity.Foodentity
import kotlinx.coroutines.flow.StateFlow

interface Repository {

    suspend fun addfood (image : Uri?, name : String, description : String, author : String, likes : Int) : Boolean

    suspend fun fetchfoods () : StateFlow<List<Foodentity>>

}