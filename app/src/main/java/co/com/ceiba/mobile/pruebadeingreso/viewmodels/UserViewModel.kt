package co.com.ceiba.mobile.pruebadeingreso.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import co.com.ceiba.mobile.pruebadeingreso.data.User
import co.com.ceiba.mobile.pruebadeingreso.data.UserRepository

class UserViewModel (application: Application) : AndroidViewModel(application) {
    private var repository: UserRepository = UserRepository(application)
    private var allPosts: LiveData<List<User>> = repository.getAllPosts()

    fun insert(post: User) {
        repository.insert(post)
    }

    fun update(post: User) {
        repository.update(post)
    }

    fun delete(post: User) {
        repository.delete(post)
    }

    fun deleteAllPosts() {
        repository.deleteAllPosts()
    }

    fun getAllPosts(): LiveData<List<User>> {
        return allPosts
    }
}