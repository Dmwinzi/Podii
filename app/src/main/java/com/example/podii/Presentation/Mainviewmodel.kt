package com.example.podii.Presentation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.podii.Data.RepositoryImpl
import com.example.podii.Domain.Entity.Foodentity
import com.example.podii.Domain.Usecase.Uploadfoodusecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Mainviewmodel @Inject constructor( private var  uploadfoodusecase: Uploadfoodusecase) : ViewModel() {

    private val foods  = MutableStateFlow<List<Foodentity>>(listOf())
    var _foods = foods.asStateFlow()

    init {
        getfoods()
    }

    fun getfoods(){
        viewModelScope.launch {
            uploadfoodusecase.fetchfoods().collect{
                foods.value  = it
            }
        }
    }


    suspend fun uploadfoods (image : Uri?, name : String, description : String, author : String, likes : Int) : Boolean{
        return uploadfoodusecase.addfood(image, name, description, author, likes)
    }

}