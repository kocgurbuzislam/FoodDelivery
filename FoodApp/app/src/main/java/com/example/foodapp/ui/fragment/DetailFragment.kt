package com.example.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentDetailBinding
import com.example.foodapp.ui.viewmodel.DetailViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    var count = 1
    var fav = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val bundle: DetailFragmentArgs by navArgs()
        val food = bundle.food

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}"

        Glide.with(this).load(url).override(500,650).into(binding.imageViewFoodImage)
        binding.textViewFoodName.text = food.yemek_adi
        binding.textViewPrice.text = "${food.yemek_fiyat}₺"
        binding.textViewTotal.text = "${food.yemek_fiyat}₺"

        binding.textViewIncrease.setOnClickListener {
            count = binding.textViewAmount.text.toString().toInt()
            count = count!! + 1

            binding.textViewAmount.text = count.toString()

            binding.textViewTotal.text = "${food.yemek_fiyat * count!!}₺"
        }
        binding.textViewDecrease.setOnClickListener {
            count = binding.textViewAmount.text.toString().toInt()
            count = count!! - 1

            binding.textViewAmount.text = count.toString()
            binding.textViewTotal.text = "${food.yemek_fiyat * count!!}₺"
        }
        binding.btnAddToCart.setOnClickListener {
            val yemek_adi = food.yemek_adi
            val yemek_resim_adi = food.yemek_resim_adi
            val yemek_fiyat = food.yemek_fiyat
            val yemek_siparis_adet = count
            val kullanici_adi = "ahmet"

            addToCart(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)

        }
        binding.imageViewClose.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.detailToHome)
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.favState.value = viewModel.isFavorite(food.yemek_id).toString()
        }
        binding.imageViewFav.setOnClickListener {
            val food_id = food.yemek_id
            val food_name = food.yemek_adi
            val food_image_name = food.yemek_resim_adi
            val food_price = food.yemek_fiyat
            addFavoriteFoods(food_id,food_name,food_image_name,food_price)


        }


        viewModel.favState.observe(viewLifecycleOwner){
            if(viewModel.favState.value!!.toInt() > 0) {
                binding.imageViewFav.setImageResource(R.drawable.ic_favred)
            }else{
                binding.imageViewFav.setImageResource(R.drawable.ic_heart_outlined)
            }
        }



        return binding.root
    }

    fun addToCart(yemek_adi: String, yemek_resim_adi: String, yemek_fiyat: Int, yemek_siparis_adet: Int, kullanici_adi: String){
        viewModel.addToCart(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
        Navigation.findNavController(binding.btnAddToCart).navigate(R.id.detailToCart)
    }

    fun addFavoriteFoods(food_id : Int, food_name:String,food_image_name:String,food_price:Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val isFavorite = viewModel.isFavorite(food_id)
            if (isFavorite == 1){
                viewModel.deleteFavorite(food_id)
                binding.imageViewFav.setImageResource(R.drawable.ic_heart_outlined)
            }else{
                viewModel.addFavoriteFoods(food_id,food_name,food_image_name,food_price)
                binding.imageViewFav.setImageResource(R.drawable.ic_favred)
                fav = true

            }

        }
    }

    override fun onResume() {
        super.onResume()
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bnv)
        // BottomNavigationView'ı kullanın
        bottomNavigationView?.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DetailViewModel by viewModels()
        viewModel = tempViewModel
    }

}