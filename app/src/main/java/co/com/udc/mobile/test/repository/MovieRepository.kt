package co.com.udc.mobile.test.repository

import android.content.Context
import android.util.Log
import co.com.udc.mobile.test.R
import co.com.udc.mobile.test.api.ApiService
import co.com.udc.mobile.test.api.UserResponse
import co.com.udc.mobile.test.data.User
import co.com.udc.mobile.test.data.UserDao
import co.com.udc.mobile.test.data.UserDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author  david.mazo
 */
class MovieRepository(private val context: Context) {

    private val apiService = ApiService.instance
    private val movieDatabase: UserDao get() = UserDatabase.getInstance(context)!!.userDao()

    fun requestMovieReviewList(): List<User> {
        apiService.getMovieReviewListFromInternet().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                when (response.code()) {
                    200 -> insertUsersIntoDatabase(response)
                    else -> Log.e(context.getString(R.string.error_tag), context.getString(R.string.error_response_code_different_to_200))
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e(context.getString(R.string.error_tag), t.printStackTrace().toString())
            }
        })
        return getUserList()
    }

    private fun insertUsersIntoDatabase(response: Response<List<User>>) {
        if (response.body() != null) {
            for (user: User in response.body() as List<User>) {
                Log.e("user","info: "+user.toString())
                movieDatabase.insert(user)
            }
        }
    }

    fun getUserList(): List<User> {
        //return UserDatabase.getMovieDatabase(context).getMovieDAO().getMovieReviewList()
        return UserDatabase.getInstance(context)!!.userDao().getUsers()
    }
}
