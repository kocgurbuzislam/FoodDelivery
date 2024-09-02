package com.example.foodapp.ui.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.data.entity.Food
import com.example.foodapp.data.entity.User
import com.example.foodapp.data.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(var usersRepository: UsersRepository) : ViewModel() {

    var usersLiveData = MutableLiveData<List<User>?>()


    fun userCheck(user_email: String, user_password: String){
        CoroutineScope(Dispatchers.Main).launch {
            val user =  usersRepository.userCheck(user_email, user_password)
            if (user != null){
                usersLiveData.value = listOf(user)


            }else{
                usersLiveData.value = null
            }

        }
    }
}