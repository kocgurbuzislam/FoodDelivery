package com.example.foodapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.data.entity.FavoriteFoods
import com.example.foodapp.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(var frepo: FoodRepository) : ViewModel() {

    var ffoodList = MutableLiveData<List<FavoriteFoods>?>()

    init {
        ffoodGet()
    }

     fun ffoodGet(){
         CoroutineScope(Dispatchers.Main).launch {
             try {
                 ffoodList.value = frepo.favoriteFoodsLoad()
                 Log.e("guncelleme",ffoodList.value.toString())

             }catch (e: Exception){
                 ffoodList.value = null
             }

         }
    }

    fun deleteFavorite(food_id: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            frepo.deleteFavorite(food_id)
            ffoodGet()
        }
    }
}