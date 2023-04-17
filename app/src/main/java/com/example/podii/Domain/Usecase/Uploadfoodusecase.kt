package com.example.podii.Domain.Usecase

import android.net.Uri
import com.example.podii.Domain.Entity.Foodentity
import com.example.podii.Domain.Repository.Repository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class Uploadfoodusecase @Inject constructor (private val repository: Repository) {



    suspend fun addfood(image : Uri?, name : String, description : String, author : String, likes : Int) : Boolean{
        return repository.addfood(image, name, description, author, likes)
    }

    suspend fun fetchfoods () : StateFlow<List<Foodentity>> {
        return   repository.fetchfoods()
    }

}