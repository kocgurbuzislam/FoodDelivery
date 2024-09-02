package com.example.foodapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.data.entity.FavoriteFoods
import com.example.foodapp.data.entity.Food
import com.example.foodapp.databinding.CardFavoriteBinding
import com.example.foodapp.ui.viewmodel.FavoriteViewModel

class FavoriteFoodAdapter(var mContext: Context, var favoriteFoodList: List<FavoriteFoods>,var viewModel: FavoriteViewModel): RecyclerView.Adapter<FavoriteFoodAdapter.cardFavoriteDesignHolder>() {

    inner class cardFavoriteDesignHolder(var binding: CardFavoriteBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardFavoriteDesignHolder {
        var binding = CardFavoriteBinding.inflate(LayoutInflater.from(mContext),parent,false)

        return cardFavoriteDesignHolder(binding)
    }



    override fun onBindViewHolder(holder: cardFavoriteDesignHolder, position: Int) {
        var ffood = favoriteFoodList.get(position)
        var t = holder.binding
        var fav = false

        t.textViewFoodName.text = ffood.food_name
        t.textViewPrice.text = "${ffood.food_price}₺"
        t.imageViewFav.setImageResource(R.drawable.ic_favred)
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${ffood.food_image_name}"
        Glide.with(mContext).load(url).override(500,650).into(t.imageViewFoodPicture)


        t.imageViewFav.setOnClickListener {
            fav = !fav
            if (fav) {
                t.imageViewFav.setImageResource(R.drawable.ic_favred)

            } else {
                t.imageViewFav.setImageResource(R.drawable.ic_heart_outlined)
                viewModel.deleteFavorite(ffood.food_id)

            }
        }



    }

    fun updateList(newList: List<FavoriteFoods>) {
        favoriteFoodList = newList
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return favoriteFoodList.size
    }

}