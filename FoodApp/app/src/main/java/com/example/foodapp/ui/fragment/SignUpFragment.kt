package com.example.foodapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentSignUpBinding
import com.example.foodapp.ui.viewmodel.SignUpViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.btnSignUp.setOnClickListener {
            var user_name = binding.editTextName.text.toString()
            var user_surname = binding.editTextSurname.text.toString()
            var user_email = binding.editTextEmail.text.toString()
            var user_password = binding.editTextPasword.text.toString()
            var user_password_again = binding.editTextPaswordAgain.text.toString()

            usersSave(user_name, user_surname, user_email, user_password, user_password_again)
            Snackbar.make(it, "Kayıt Başarılı", Snackbar.LENGTH_INDEFINITE)
                .setAction("Evet"){
                    Navigation.findNavController(binding.btnSignUp).navigate(R.id.signupTologin)
                }
                .show()
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: SignUpViewModel by viewModels()
        viewModel = tempViewModel
    }

    fun usersSave(user_name: String, user_surname: String, user_email: String, user_password: String, user_password_again: String){
        viewModel.userSave(user_name, user_surname, user_email, user_password, user_password_again)
    }

}