package co.com.udc.mobile.test.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class UserRepository(application: Application) {
    private var userDao: UserDao

    private var allUsers: LiveData<List<User>>

    init {
        val database: UserDatabase = UserDatabase.getInstance(
                application.applicationContext
        )!!
        userDao = database.userDao()
        allUsers = userDao.getAllUsers()
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