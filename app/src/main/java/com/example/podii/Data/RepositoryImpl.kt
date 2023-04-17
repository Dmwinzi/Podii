
package com.example.podii.Data

import android.net.Uri
import com.example.podii.Domain.Entity.Foodentity
import com.example.podii.Domain.Repository.Repository
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RepositoryImpl : Repository {

    var database : DatabaseReference  = FirebaseDatabase.getInstance().getReference()
    var storagereference : StorageReference  = FirebaseStorage.getInstance().getReference().child("Foodimages/" + System.currentTimeMillis())
    var foodsstate = MutableStateFlow<List<Foodentity>>(listOf())
    var foods = mutableListOf<Foodentity>()

    override suspend fun addfood(image : Uri?,name : String, description : String, author : String,likes : Int): Boolean {
         return suspendCoroutine { continuation ->
               try {
                   if(image != null){
                       storagereference.putFile(image).continueWithTask { task ->
                           if (!task.isSuccessful){
                               task.exception?.let {
                                   throw it
                               }
                           }
                           storagereference.downloadUrl
                       }.addOnCompleteListener {task ->
                           if (task.isSuccessful){
                               var imageurl  = task.result.toString()
                               var foodentity  = Foodentity(imageurl,name, description, author, likes)
                               database.child("Foods").push().setValue(foodentity)
                               continuation.resume(true)
                           }
                       }.addOnFailureListener {
                           continuation.resume(false)
                       }
                   }
               } catch (e : java.lang.Exception){
                   continuation.resumeWithException(e)
               }
         }
    }

    override suspend fun fetchfoods(): StateFlow<List<Foodentity>> {
           var valueeventlistener = object : ValueEventListener{
               override fun onDataChange(snapshot: DataSnapshot) {
                   foods.clear()
                  for (snap : DataSnapshot in snapshot.children){
                      var allfoods  = snap.getValue(Foodentity::class.java)
                      if(allfoods != null){
                          foods.add(allfoods)
                      }
                  }
                   foodsstate.value  = foods
               }
               override fun onCancelled(error: DatabaseError) {

               }

           }

        database.child("Foods").addValueEventListener(valueeventlistener)

        return foodsstate
    }


}