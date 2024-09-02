package com.example.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.util.foreignKeyCheck
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentCartBinding
import com.example.foodapp.ui.adapter.CartAdapter
import com.example.foodapp.ui.viewmodel.CartViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var viewModel: CartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        viewModel.getCart("ahmet")

        binding.recyclerViewCart.layoutManager = LinearLayoutManager(requireContext())

        viewModel.cartList.observe(viewLifecycleOwner){
            val adapter = it?.let { it1 -> CartAdapter(requireContext(), it1,viewModel) }
            binding.recyclerViewCart.adapter = adapter
        }
        viewModel.totalPrice.observe(viewLifecycleOwner){
            binding.textViewTotalPrice.text = "₺${it}"
        }



        binding.imageViewClose.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.cartToHome)
        }

        viewModel.totalPrice.observe(viewLifecycleOwner){
            binding.textViewTotalPrice.text = "${it}₺"
        }

        binding.btnCheckOut.setOnClickListener {
            Snackbar.make(it,"Siparişiniz Alınmıştır",Snackbar.LENGTH_INDEFINITE)
                .setAction("Tamam"){
                    for (food in viewModel.cartList.value!!){
                        viewModel.foodDelete(food.sepet_yemek_id, food.kullanici_adi)
                    }
                }
                .show()

        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bnv)
        // BottomNavigationView'ı kullanın
        bottomNavigationView?.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: CartViewModel by viewModels()
        viewModel = tempViewModel
    }
}