package com.example.foodapp.data.entity

import com.google.gson.annotations.SerializedName

data class FoodAnswer(@SerializedName("yemekler") var foods: List<Food>,
                      @SerializedName("success") var success: Int) {
}