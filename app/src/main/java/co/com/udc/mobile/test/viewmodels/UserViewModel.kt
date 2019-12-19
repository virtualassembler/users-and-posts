package co.com.udc.mobile.test.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.com.udc.mobile.test.data.User
import co.com.udc.mobile.test.data.UserRepository

class UserViewModel (application: Application) : AndroidViewModel(application) {
    private var repository: UserRepository = UserRepository(application)
    private var allUsers: LiveData<List<User>> = repository.getAllUsers()
    private var filteredUsers: MutableLiveData<List<User>> = MutableLiveData()

    fun insert(user: User) {
        repository.insert(user)
    }

    fun update(user: User) {
        repository.update(user)
    }

    fun delete(user: User) {
        repository.delete(user)
    }

    fun deleteAllUsers() {
        repository.deleteAllUsers()
    }

    fun getAllUsers(): LiveData<List<User>> {
        return allUsers
    }

    fun filterByName(userName: String) {
        filteredUsers.value = repository.getFilteredUsers(userName)
    }

    fun getFilteredUsers(): LiveData<List<User>> {
        return filteredUsers
    }
}