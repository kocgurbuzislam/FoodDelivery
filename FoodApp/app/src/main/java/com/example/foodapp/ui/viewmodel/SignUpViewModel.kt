package com.example.foodapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.data.entity.User
import com.example.foodapp.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(val userRepository: UsersRepository): ViewModel() {
    var usersList = MutableLiveData<List<User>>()

    fun userSave(user_name: String, user_surname: String, user_email: String, user_password: String, user_password_again: String){
        CoroutineScope(Dispatchers.Main).launch {
            userRepository.userSave(user_name, user_surname, user_email, user_password, user_password_again)

        }

    }

}