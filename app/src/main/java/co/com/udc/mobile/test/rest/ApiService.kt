package co.com.udc.mobile.test.rest

import co.com.udc.mobile.test.data.User
import co.com.udc.mobile.test.utils.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * @author david.mazo
 */

interface ApiService {

    @GET("/users")
    fun getMovieReviewListFromInternet(): Call<List<User>>

    companion object {
        val instance: ApiService = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiService::class.java)
    }
}
