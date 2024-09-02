package com.example.foodapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "users")
data class User(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "user_id")  @NotNull var user_id: Int,
               @ColumnInfo(name = "user_name") @NotNull var user_name: String,
               @ColumnInfo(name = "user_surname") @NotNull var user_surname: String,
               @ColumnInfo(name = "user_email") @NotNull var user_email: String,
               @ColumnInfo(name = "user_password") @NotNull var user_password:String,
               @ColumnInfo(name = "user_password_again") @NotNull var user_password_again: String,

                ): Serializable
