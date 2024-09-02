package com.example.foodapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.foodapp.data.entity.FavoriteFoods
import com.example.foodapp.data.entity.User

@Database(entities = [FavoriteFoods::class, User::class], version = 1)
abstract class FavDatabase: RoomDatabase() {

    abstract fun getFfoodDao(): FFoodDao
    abstract fun getUsersDao(): UsersDao

}