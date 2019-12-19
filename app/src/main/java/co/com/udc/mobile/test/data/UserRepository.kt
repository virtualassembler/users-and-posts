package co.com.udc.mobile.test.data

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import co.com.udc.mobile.test.R
import co.com.udc.mobile.test.rest.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val application: Application) {


    private var userDao: UserDao
    private var allUsers: LiveData<List<User>>
  //  private var filteredUsers: LiveData<List<User>>

    init {
        val database: UserDatabase = UserDatabase.getInstance(
                application.applicationContext
        )!!
        userDao = database.userDao()
        allUsers = userDao.getAllUsers()
      //  filteredUsers = userDao.getFilteredUsers()
    }

    private val apiService = ApiService.instance
    private val movieDatabase: UserDao get() = UserDatabase.getInstance(application)!!.userDao()

    //To look in the endpoint for userList
    fun requestMovieReviewList(): List<User> {
        apiService.getMovieReviewListFromInternet().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                when (response.code()) {
                    200 -> insertUsersIntoDatabase(response)
                    else -> Log.e(application.applicationContext.getString(R.string.error_tag), application.applicationContext.getString(R.string.error_response_code_different_to_200))
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e(application.applicationContext.getString(R.string.error_tag), t.printStackTrace().toString())
            }
        })
        return getUserList()
    }

    //Save users in local DB
    private fun insertUsersIntoDatabase(response: Response<List<User>>) {
        if (response.body() != null) {
            for (user: User in response.body() as List<User>) {
                Log.e("user", "info: " + user.toString())
                movieDatabase.insert(user)
            }
        }
    }

    fun getUserList(): List<User> {
        return UserDatabase.getInstance(application.applicationContext)!!.userDao().getUsers()
    }

    fun insert(user: User) {
        val insertUserAsyncTask = InsertUserAsyncTask(userDao).execute(user)
    }

    fun update(user: User) {
        val updateUserAsyncTask = UpdateUserAsyncTask(userDao).execute(user)
    }

    fun delete(user: User) {
        val deleteUserAsyncTask = DeleteUserAsyncTask(userDao).execute(user)
    }

    fun deleteAllUsers() {
        val deleteAllUsersAsyncTask = DeleteAllUsersAsyncTask(
                userDao
        ).execute()
    }

    fun getAllUsers(): LiveData<List<User>> {
        return allUsers
    }

    fun getFilteredUsers(userName: String): List<User> {
        return userDao.getFilteredUsers("%$userName%")
    }

    companion object {
        private class InsertUserAsyncTask(userDao: UserDao) : AsyncTask<User, Unit, Unit>() {
            val userDao = userDao

            override fun doInBackground(vararg p0: User?) {
                userDao.insert(p0[0]!!)
            }
        }

        private class UpdateUserAsyncTask(userDao: UserDao) : AsyncTask<User, Unit, Unit>() {
            val userDao = userDao

            override fun doInBackground(vararg p0: User?) {
                userDao.update(p0[0]!!)
            }
        }

        private class DeleteUserAsyncTask(userDao: UserDao) : AsyncTask<User, Unit, Unit>() {
            val userDao = userDao

            override fun doInBackground(vararg p0: User?) {
                userDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllUsersAsyncTask(userDao: UserDao) : AsyncTask<Unit, Unit, Unit>() {
            val userDao = userDao

            override fun doInBackground(vararg p0: Unit?) {
                userDao.deleteAllUsers()
            }
        }
    }

}