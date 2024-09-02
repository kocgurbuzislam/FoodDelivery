package com.example.foodapp.di

import android.content.Context
import androidx.room.Room
import com.example.foodapp.data.datasource.FoodDataSource
import com.example.foodapp.data.datasource.UsersDataSource
import com.example.foodapp.data.repository.FoodRepository
import com.example.foodapp.retrofit.ApiUtils
import com.example.foodapp.retrofit.FoodDao
import com.example.foodapp.room.FFoodDao
import com.example.foodapp.room.FavDatabase
import com.example.foodapp.room.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFoodDataSource(fdao: FoodDao, ffoodDao: FFoodDao): FoodDataSource {
        return FoodDataSource(fdao, ffoodDao)
    }

    @Provides
    @Singleton
    fun provideFFoodDao(@ApplicationContext context: Context): FFoodDao {
        val vt = Room.databaseBuilder(context, FavDatabase::class.java,"favoriteFood.sqlite")
            .createFromAsset("favoriteFood.sqlite").build()
        return vt.getFfoodDao()
    }


    @Provides
    @Singleton
    fun provideFoodDao(): FoodDao{
        return ApiUtils.getFoodDao()
    }

    @Provides
    @Singleton
    fun provideFoodRepository(fds: FoodDataSource): FoodRepository {
        return FoodRepository(fds)
    }

    @Provides
    @Singleton
    fun provideUsersDataSource(userDao: UsersDao): UsersDataSource {
        return UsersDataSource(userDao)
    }

    @Provides
    @Singleton
    fun provideuserDao(@ApplicationContext context: Context): UsersDao {
        val vt = Room.databaseBuilder(context, FavDatabase::class.java,"favoriteFood.sqlite")
            .createFromAsset("favoriteFood.sqlite").build()
        return vt.getUsersDao()
    }


}