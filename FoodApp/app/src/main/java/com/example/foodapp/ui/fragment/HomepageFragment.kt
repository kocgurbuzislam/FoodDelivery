package com.example.foodapp.ui.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.data.entity.Food
import com.example.foodapp.databinding.FragmentHomepageBinding
import com.example.foodapp.ui.adapter.FavoriteFoodAdapter
import com.example.foodapp.ui.adapter.FoodAdapter
import com.example.foodapp.ui.viewmodel.HomepageViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.Locale

@AndroidEntryPoint
class HomepageFragment : Fragment() {
    private lateinit var binding: FragmentHomepageBinding
    private lateinit var viewModel: HomepageViewModel
    private lateinit var foodAdapter: FoodAdapter
    private var izinKontrol = 0
    private lateinit var flpc: FusedLocationProviderClient
    private lateinit var locationTask: Task<Location>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomepageBinding.inflate(inflater, container, false)

        flpc = LocationServices.getFusedLocationProviderClient(requireContext())

        binding.recyclerViewHomepage.layoutManager = StaggeredGridLayoutManager(2,
            StaggeredGridLayoutManager.VERTICAL)

        foodAdapter = FoodAdapter(requireContext(), listOf(), viewModel)
        binding.recyclerViewHomepage.adapter = foodAdapter

        viewModel.foodList.observe(viewLifecycleOwner){ newList ->
            newList?.let {
                foodAdapter.updateList(it)
            }
        }


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.search(query?:"")
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText?:"")
                return true
            }
        })

        binding.searchView.setOnCloseListener {
            binding.searchView.clearFocus() // Arama kutusunun odağını kaldır
            binding.searchView.setQuery("", false) // Arama kutusunu temizle
            viewModel.foodGetAll() // Tüm yemekleri yükle
            true
        }

        binding.imageViewLocation.setOnClickListener {
            izinKontrol = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            if (izinKontrol == PackageManager.PERMISSION_GRANTED){//izin onaylanmışsa
                locationTask = flpc.lastLocation
                konumBilgisiAl()

            }else{
                ActivityCompat.requestPermissions( requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),100)
            }
        }
        return binding.root

    }

    fun konumBilgisiAl() {
        locationTask.addOnSuccessListener { location ->
            if (location != null) {
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                try {
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (addresses!!.isNotEmpty()) {
                        val address = addresses[0]
                        val il = address.adminArea ?: "" // İl ismi, yoksa boş string
                        val ilce = address.subAdminArea ?: "" // İlçe ismi, yoksa boş string
                        binding.textViewLocation.text = "$il, $ilce"
                    } else {
                        binding.textViewLocation.text = "Adres bulunamadı"
                    }
                }catch (e: IOException) {
                    binding.textViewLocation.text = "Adres alınamadı: ${e.message}"
                }
            } else {
                binding.textViewLocation.text = "Konum bilgisi alınamadı"
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 100){
            izinKontrol = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                konumBilgisiAl()
                locationTask = flpc.lastLocation

            }else{
                Toast.makeText(requireContext(),"İzin verilmedi",Toast.LENGTH_SHORT).show()

            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: HomepageViewModel by viewModels()
        viewModel = tempViewModel
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        izinKontrol = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
        locationTask = flpc.lastLocation
    }

    override fun onResume() {
        super.onResume()
        viewModel.foodGetAll()
        konumBilgisiAl()
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bnv)
        // BottomNavigationView'ı kullanın
        bottomNavigationView?.visibility = View.VISIBLE
    }

}