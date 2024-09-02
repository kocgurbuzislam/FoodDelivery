package com.example.foodapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.data.entity.FoodGetCart
import com.example.foodapp.data.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CartViewModel @Inject constructor(var frepo: FoodRepository): ViewModel() {

    var totalPrice = MutableLiveData<Int>()

    var cartList = MutableLiveData<List<FoodGetCart>?>()

    init {
        getCart("ahmet")
        ctotalPrice()

    }

    fun getCart(kullanici_adi: String){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                cartList.value = frepo.getCart(kullanici_adi)
                ctotalPrice()
            }catch (e: Exception){
                cartList.value = null
                ctotalPrice()
                e.printStackTrace()
            }

        }

    }

        fun foodDelete(sepet_yemek_id: Int, kullanici_adi: String){
        CoroutineScope(Dispatchers.Main).launch {
                frepo.foodDelete(sepet_yemek_id,kullanici_adi)
                ctotalPrice()
                getCart(kullanici_adi)
        }
    }


    fun ctotalPrice(){
        var total = 0
        if(cartList.value == null){
            totalPrice.value = 0

        }else{
            for(food in cartList.value!!){
                total += food.yemek_fiyat * food.yemek_siparis_adet
            }
            totalPrice.value = total
        }
    }


}