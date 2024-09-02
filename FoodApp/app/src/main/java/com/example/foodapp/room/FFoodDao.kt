package com.example.foodapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.foodapp.data.entity.FavoriteFoods

@Dao
interface FFoodDao {

    @Query("SELECT * FROM favorites")
    suspend fun favoriteFoodsLoad() : List<FavoriteFoods>

    @Insert
    suspend fun addFavorite(favoriteFoods: FavoriteFoods)

    @Query("DELETE FROM favorites WHERE food_id = :food_id")
    suspend fun  deleteFavorite(food_id:Int)

    @Query("SELECT COUNT(*) FROM favorites WHERE food_id = :food_id")
    suspend fun isFavorite(food_id: Int): Int

}