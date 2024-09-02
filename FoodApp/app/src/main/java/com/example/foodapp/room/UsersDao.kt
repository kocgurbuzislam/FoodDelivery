package com.example.foodapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.foodapp.data.entity.User

@Dao
interface UsersDao {

    @Insert
    suspend fun userSave(user: User)

    @Query("SELECT * FROM users WHERE user_email = :user_email and user_password = :user_password")
    suspend fun userCheck(user_email: String, user_password: String): User?
}