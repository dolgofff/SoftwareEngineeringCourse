package com.example.softwareengineeringtaskone.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softwareengineeringtaskone.app.implementation.EventAggregator
import com.example.softwareengineeringtaskone.app.implementation.EventObserver
import com.example.softwareengineeringtaskone.app.implementation.UserDataMediator
import com.example.softwareengineeringtaskone.data.dao.UserDao
import com.example.softwareengineeringtaskone.data.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userDao: UserDao) : ViewModel(), EventObserver {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    private val eventMediator = UserDataMediator(userDao, viewModelScope)

    init {
        EventAggregator.registerObserver(this)
        eventMediator.startObserving()
    }

    override fun onCleared() {
        super.onCleared()
        EventAggregator.unregisterObserver(this)
    }

    override fun onDataChanged(source: String, data: Any) {
        if (source == "UserDao") {
            val users = data as List<User>
            _users.value = users

            Log.d("EventAggregator", "Получены обновления пользователей: $users")
        }
    }

    fun addRandomUser() {
        viewModelScope.launch {
            val username = "User" + (0..1000).random()
            userDao.insertUser(User(username = username))
        }
    }
}