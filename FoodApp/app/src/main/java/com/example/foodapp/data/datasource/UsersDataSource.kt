package com.example.foodapp.data.datasource


import com.example.foodapp.data.entity.User
import com.example.foodapp.room.UsersDao


class UsersDataSource(var udao: UsersDao) {

    suspend fun userSave(user_name: String, user_surname: String, user_email: String, user_password: String, user_password_again: String){
        val user = User(0, user_name, user_surname, user_email, user_password, user_password_again)
        udao.userSave(user)

    }
    suspend fun userCheck(user_email: String, user_password: String): User? {
        return udao.userCheck(user_email, user_password)

    }

}