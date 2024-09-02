package com.example.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentCreateAccountBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountFragment : Fragment() {
    private lateinit var binding: FragmentCreateAccountBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentCreateAccountBinding.inflate(inflater, container, false)

        binding.btnCreateAccount.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.createAccountTosignUp)
        }

        binding.btnLogin.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.createaccountTologin)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bnv)
        // BottomNavigationView'ı kullanın
        bottomNavigationView?.visibility = View.GONE
    }
}