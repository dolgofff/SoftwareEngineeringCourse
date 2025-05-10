package com.example.softwareengineeringtaskone.app.implementation

import com.example.softwareengineeringtaskone.data.dao.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UserDataMediator(private val userDao: UserDao, private val coroutineScope: CoroutineScope) {
    fun startObserving() {
        coroutineScope.launch {
            userDao.getAllUsers().collect() { users ->
                EventAggregator.notifyObservers(source = "UserDao", data = users)
            }
        }
    }
}