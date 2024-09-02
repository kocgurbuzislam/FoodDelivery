package com.example.foodapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(var frepo: FoodRepository): ViewModel() {
    var favState = MutableLiveData<String>()

    init {
        favState = MutableLiveData<String>("0")
    }

    fun addToCart(yemek_adi: String, yemek_resim_adi: String, yemek_fiyat: Int, yemek_siparis_adet: Int, kullanici_adi: String){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                frepo.addToCart(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)

            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun deleteFavorite(food_id: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            frepo.deleteFavorite(food_id)
        }
    }

    fun addFavoriteFoods(food_id : Int, food_name:String,food_image_name:String,food_price:Int) {
        CoroutineScope(Dispatchers.Main).launch {
            frepo.addFavoriteFoods(food_id,food_name,food_image_name,food_price)
        }
    }

    suspend fun isFavorite(food_id: Int): Int{
        var result = 0
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO){
                result = frepo.isFavorite(food_id)
            }
        }.join()
        return result
    }

}