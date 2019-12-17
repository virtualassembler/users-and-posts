package co.com.ceiba.mobile.pruebadeingreso.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class UserRepository(application: Application) {
    private var postDao: UserDao

    private var allPosts: LiveData<List<User>>

    init {
        val database: UserDatabase = UserDatabase.getInstance(
                application.applicationContext
        )!!
        postDao = database.postDao()
        allPosts = postDao.getAllPosts()
    }

    fun insert(post: User) {
        val insertPostAsyncTask = InsertPostAsyncTask(postDao).execute(post)
    }

    fun update(post: User) {
        val updatePostAsyncTask = UpdatePostAsyncTask(postDao).execute(post)
    }

    fun delete(post: User) {
        val deletePostAsyncTask = DeletePostAsyncTask(postDao).execute(post)
    }

    fun deleteAllPosts() {
        val deleteAllPostsAsyncTask = DeleteAllPostsAsyncTask(
                postDao
        ).execute()
    }

    fun getAllPosts(): LiveData<List<User>> {
        return allPosts
    }

    companion object {
        private class InsertPostAsyncTask(postDao: UserDao) : AsyncTask<User, Unit, Unit>() {
            val postDao = postDao

            override fun doInBackground(vararg p0: User?) {
                postDao.insert(p0[0]!!)
            }
        }

        private class UpdatePostAsyncTask(postDao: UserDao) : AsyncTask<User, Unit, Unit>() {
            val postDao = postDao

            override fun doInBackground(vararg p0: User?) {
                postDao.update(p0[0]!!)
            }
        }

        private class DeletePostAsyncTask(postDao: UserDao) : AsyncTask<User, Unit, Unit>() {
            val postDao = postDao

            override fun doInBackground(vararg p0: User?) {
                postDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllPostsAsyncTask(postDao: UserDao) : AsyncTask<Unit, Unit, Unit>() {
            val postDao = postDao

            override fun doInBackground(vararg p0: Unit?) {
                postDao.deleteAllPosts()
            }
        }
    }
}