package com.example.foodapp.data.repository

import com.example.foodapp.data.datasource.FoodDataSource
import com.example.foodapp.data.entity.FavoriteFoods
import com.example.foodapp.data.entity.Food
import com.example.foodapp.data.entity.FoodGetCart

class FoodRepository(var fds: FoodDataSource) {

    suspend fun foodGetAll(): List<Food>{
        return fds.foodGet()
    }
    suspend fun favoriteFoodsLoad() : List<FavoriteFoods> = fds.ffoodGet()

    suspend fun addFavoriteFoods(food_id : Int, food_name:String,food_image_name:String,food_price:Int) = fds.addFavoriteFoods(food_id,food_name,food_image_name,food_price)

    suspend fun deleteFavorite(food_id: Int) = fds.deleteFavorite(food_id)

    suspend fun isFavorite(food_id: Int): Int = fds.isFavorite(food_id)

    suspend fun addToCart(yemek_adi: String, yemek_resim_adi: String, yemek_fiyat: Int,
                          yemek_siparis_adet: Int, kullanici_adi: String){
        return fds.addToCart(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    }

    suspend fun getCart(kullanici_adi: String): List<FoodGetCart>{
        return fds.getCart(kullanici_adi)
    }

    suspend fun foodDelete(sepet_yemek_id: Int, kullanici_adi: String){
        return fds.foodDelete(sepet_yemek_id,kullanici_adi)
    }

}