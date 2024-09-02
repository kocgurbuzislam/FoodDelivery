package com.example.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentFavoriteBinding
import com.example.foodapp.ui.adapter.FavoriteFoodAdapter
import com.example.foodapp.ui.adapter.FoodAdapter
import com.example.foodapp.ui.viewmodel.FavoriteViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var ffoodAdapter: FavoriteFoodAdapter



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        binding.rvfav.layoutManager = StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)

     /*   viewModel.ffoodList.observe(viewLifecycleOwner){
                val adapter = it?.let { it1 -> FavoriteFoodAdapter(requireContext(), it1) }
                binding.rvfav.adapter = adapter
        }
      */


        ffoodAdapter = FavoriteFoodAdapter(requireContext(), listOf(), viewModel)
        binding.rvfav.adapter = ffoodAdapter

        viewModel.ffoodList.observe(viewLifecycleOwner){ newList ->
            newList?.let {
                ffoodAdapter.updateList(it)
            }
        }

        binding.imageViewBack.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.favTohome)
        }


        return binding.root
    }
    override fun onResume() {
        super.onResume()
        viewModel.ffoodGet()
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bnv)
        // BottomNavigationView'ı kullanın
        bottomNavigationView?.visibility = View.VISIBLE

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: FavoriteViewModel by viewModels()
        viewModel = tempViewModel
    }
}