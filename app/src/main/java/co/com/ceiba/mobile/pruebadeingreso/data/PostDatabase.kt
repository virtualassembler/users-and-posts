package co.com.ceiba.mobile.pruebadeingreso.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Post::class], version = 1)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao


    companion object {
        private var instance: PostDatabase? = null

        fun getInstance(context: Context): PostDatabase? {
            if (instance == null) {
                synchronized(PostDatabase::class) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            PostDatabase::class.java, "post_database"
                    )
                            .fallbackToDestructiveMigration() // when version increments, it migrates (deletes db and creates new) - else it crashes
                            .addCallback(roomCallback)
                            .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                        .execute()
            }
        }
    }

    class PopulateDbAsyncTask(db: PostDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val postDao = db?.postDao()

        override fun doInBackground(vararg p0: Unit?) {
            postDao?.insert(Post("title 1", "description 1", 1))
            postDao?.insert(Post("title 2", "description 2", 2))
            postDao?.insert(Post("title 3", "description 3", 3))
        }
    }

}