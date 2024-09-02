package com.example.foodapp.data.entity

import com.google.gson.annotations.SerializedName

data class CRUDAnswer(@SerializedName("success") var success: Int,
                      @SerializedName("message") var message: String) {
}