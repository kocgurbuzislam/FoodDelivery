package com.example.foodapp.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.data.entity.Food
import com.example.foodapp.databinding.CardHomepageBinding
import com.example.foodapp.ui.fragment.HomepageFragmentDirections
import com.example.foodapp.ui.viewmodel.HomepageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodAdapter(
    var mContext: Context,
    var foodList: List<Food>,
    var viewModel: HomepageViewModel
) : RecyclerView.Adapter<FoodAdapter.cardDesignHolder>() {

    inner class cardDesignHolder(var binding: CardHomepageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardDesignHolder {
        val binding = CardHomepageBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return cardDesignHolder(binding)
    }

    override fun onBindViewHolder(holder: cardDesignHolder, position: Int) {
        val food = foodList[position]
        val t = holder.binding
        var fav = false

        t.textViewFoodName.text = food.yemek_adi
        t.textViewPrice.text = "${food.yemek_fiyat}₺"

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"
        Glide.with(mContext).load(url).override(500, 650).into(t.imageViewFoodPicture)

        t.cardView.setOnClickListener {
            val transition = HomepageFragmentDirections.detailTransition(food = food)
            Navigation.findNavController(it).navigate(transition)
        }

        CoroutineScope(Dispatchers.Main).launch {
            val favoriteCount = viewModel.isFavorite(food.yemek_id)
            val isFavorite = if (favoriteCount > 0) {
                true
            } else {
                false
            }
            if (isFavorite) {
                t.imageViewFav.setImageResource(R.drawable.ic_favred)
            } else {
                t.imageViewFav.setImageResource(R.drawable.ic_heart_outlined)
            }
        }

        t.imageViewFav.setOnClickListener {
            fav = !fav
            if (fav == false){
                viewModel.deleteFavorite(food.yemek_id)
                t.imageViewFav.setImageResource(R.drawable.ic_heart_outlined)
                Log.e("tıklandı","${fav}")
            }
            else{
                viewModel.addFavoriteFoods(food.yemek_id,food.yemek_adi,food.yemek_resim_adi,food.yemek_fiyat)
                t.imageViewFav.setImageResource(R.drawable.ic_favred)
                Log.e("tıklandı2","${fav}")
            }
        }

    }

    fun updateList(newList: List<Food>) {
        foodList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return foodList.size
    }
}