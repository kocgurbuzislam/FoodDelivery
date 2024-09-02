package com.example.foodapp.data.datasource

import com.example.foodapp.data.entity.FavoriteFoods
import com.example.foodapp.data.entity.Food
import com.example.foodapp.data.entity.FoodGetCart
import com.example.foodapp.retrofit.FoodDao
import com.example.foodapp.room.FFoodDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodDataSource(var fdao: FoodDao,var ffoodDao: FFoodDao) {


    suspend fun foodGet(): List<Food> =
        withContext(Dispatchers.IO){
            return@withContext fdao.foodGetAll().foods
        }
    suspend fun ffoodGet(): List<FavoriteFoods> =
        withContext(Dispatchers.IO){
            return@withContext ffoodDao.favoriteFoodsLoad()
        }

    suspend fun addFavoriteFoods(food_id : Int, food_name:String,food_image_name:String,food_price:Int){
        val newFavoriteFood = FavoriteFoods(0,food_id,food_name,food_image_name,food_price)
        ffoodDao.addFavorite(newFavoriteFood)
    }

    suspend fun deleteFavorite(food_id: Int) {
        ffoodDao.deleteFavorite(food_id)
    }

    suspend fun isFavorite(food_id: Int): Int {
        return ffoodDao.isFavorite(food_id)
    }

    suspend fun addToCart(yemek_adi: String, yemek_resim_adi: String,
                          yemek_fiyat: Int, yemek_siparis_adet: Int,
                          kullanici_adi: String){
        fdao.addToCart(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    }

    suspend fun getCart(kullanici_adi: String): List<FoodGetCart> =
        withContext(Dispatchers.IO){
            return@withContext fdao.getCart(kullanici_adi).sepet_yemekler
        }

    suspend fun foodDelete(sepet_yemek_id: Int, kullanici_adi: String){
        fdao.foodDelete(sepet_yemek_id,kullanici_adi)
    }

}