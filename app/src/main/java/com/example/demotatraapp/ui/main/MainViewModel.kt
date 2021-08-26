package com.example.demotatraapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demotatraapp.MainRepository
import com.example.demotatraapp.data.User

class MainViewModel : ViewModel() {
    private var mainRepository = MainRepository()
    private val users: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>().also {
            loadUsers(1, 5)
        }
    }
    private val user = MutableLiveData<User>()

    init {
        // loadUsers(1)
    }

    fun getUsers(): LiveData<List<User>> {
        return users
    }

    fun loadMore(currentPage: Int, perPage: Int) {
        loadUsers(currentPage, perPage)
    }

    private fun loadUsers(currentPage: Int, perPage: Int) {
        mainRepository.getUsers(currentPage, perPage) { usersResult ->
            users.postValue(usersResult)
        }
    }

    fun loadUserDetail(id: Int): LiveData<User> {
        mainRepository.getUser(id) { userResult ->

            user.postValue(userResult)
        }
        return user
    }

}