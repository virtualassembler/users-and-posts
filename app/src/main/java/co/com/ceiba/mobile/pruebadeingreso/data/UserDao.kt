package co.com.ceiba.mobile.pruebadeingreso.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface UserDao {
    @Insert
    fun insert(post: User)

    @Update
    fun update(post: User)

    @Delete
    fun delete(post: User)

    @Query("DELETE FROM post_table")
    fun deleteAllPosts()

    @Query("SELECT * FROM post_table ORDER BY priority DESC")
    fun getAllPosts(): LiveData<List<User>>
}