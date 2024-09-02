package com.example.foodapp.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.data.entity.FoodGetCart
import com.example.foodapp.databinding.CardCartBinding
import com.example.foodapp.ui.viewmodel.CartViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartAdapter(var mContext: Context, var foodList: List<FoodGetCart>, var viewModel: CartViewModel): RecyclerView.Adapter<CartAdapter.cardCartDesignHolder>() {

    inner class cardCartDesignHolder(var binding: CardCartBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardCartDesignHolder {
        var binding = CardCartBinding.inflate(LayoutInflater.from(mContext),parent,false)
        return cardCartDesignHolder(binding)
    }

    override fun onBindViewHolder(holder: cardCartDesignHolder, position: Int) {
      var food = foodList.get(position)
        var t = holder.binding
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"

        if (foodList.isNotEmpty()){
            t.textViewFoodName.text = food.yemek_adi
            t.textViewPrice.text = "${food.yemek_fiyat}₺"
            t.textViewOrderCount.text = "Adet: ${food.yemek_siparis_adet}"
            t.textViewTotal.text = "${food.yemek_fiyat * food.yemek_siparis_adet}"
            Glide.with(mContext).load(url).override(100,100).into(t.imageViewFood)



            t.imageViewDelete.setOnClickListener {
                Snackbar.make(it,"${food.yemek_adi} silinsin mi?",Snackbar.LENGTH_LONG)
                    .setAction("Evet"){
                        viewModel.foodDelete(food.sepet_yemek_id, food.kullanici_adi)
                        viewModel.getCart("ahmet")
                    }
                    .show()
            }
        }else{
            t.textViewFoodName.text = "Sepet Boş"
        }



    }

    override fun getItemCount(): Int {
       return foodList.size
    }
}