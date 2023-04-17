package com.example.podii.DI

import com.example.podii.Data.RepositoryImpl
import com.example.podii.Domain.Repository.Repository
import com.example.podii.Domain.Usecase.Uploadfoodusecase
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@dagger.Module
@InstallIn(SingletonComponent::class)
object Module {

 @Singleton
 @Provides
 fun providerepository() : Repository{
    return RepositoryImpl()
 }

 @Singleton
 @Provides
 fun provideusecase (repository: Repository) : Uploadfoodusecase{
     return Uploadfoodusecase(repository)
 }



}