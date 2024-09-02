package com.example.foodapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.data.entity.FavoriteFoods
import com.example.foodapp.data.entity.Food
import com.example.foodapp.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomepageViewModel @Inject constructor(var frepo: FoodRepository): ViewModel() {

    var foodList = MutableLiveData<List<Food>?>()
    var ffoodList = MutableLiveData<List<FavoriteFoods>?>()

    init {
        ffoodGet()
        foodGetAll()

    }

    fun ffoodGet(){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                ffoodList.value = frepo.favoriteFoodsLoad()

            }catch (e: Exception){
                ffoodList.value = null
            }

        }
    }

    fun foodGetAll(){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                foodList.value = frepo.foodGetAll()

            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun addFavoriteFoods(food_id : Int, food_name:String,food_image_name:String,food_price:Int) {
        CoroutineScope(Dispatchers.Main).launch {
            frepo.addFavoriteFoods(food_id,food_name,food_image_name,food_price)
        }
    }

    fun deleteFavorite(food_id: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            frepo.deleteFavorite(food_id)
        }
    }

    fun search(query: String){
        val filteredList = foodList.value?.filter { food ->
            food.yemek_adi.toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()))
        }
        foodList.value = filteredList
    }

    suspend fun isFavorite(food_id: Int): Int {
        var result = 0
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                result = frepo.isFavorite(food_id)
            }
        }.join()
        return result
    }



}