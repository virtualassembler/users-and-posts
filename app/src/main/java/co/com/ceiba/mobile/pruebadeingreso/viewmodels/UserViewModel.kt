package co.com.ceiba.mobile.pruebadeingreso.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import co.com.ceiba.mobile.pruebadeingreso.data.User
import co.com.ceiba.mobile.pruebadeingreso.data.UserRepository

class UserViewModel (application: Application) : AndroidViewModel(application) {
    private var repository: UserRepository = UserRepository(application)
    private var allUsers: LiveData<List<User>> = repository.getAllUsers()

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
}