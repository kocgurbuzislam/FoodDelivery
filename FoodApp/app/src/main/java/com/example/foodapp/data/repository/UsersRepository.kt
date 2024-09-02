package com.example.foodapp.data.repository


import com.example.foodapp.data.datasource.UsersDataSource
import com.example.foodapp.data.entity.User
import javax.inject.Inject

class UsersRepository @Inject constructor(var usersDataSource: UsersDataSource) {

    suspend fun userSave(user_name: String, user_surname: String, user_email: String, user_password: String, user_password_again: String){
        usersDataSource.userSave(user_name, user_surname, user_email, user_password, user_password_again)

    }

    suspend fun userCheck(user_email: String, user_password: String): User?{
        return usersDataSource.userCheck(user_email, user_password)

    }

}