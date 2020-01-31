package co.com.ceiba.mobile.pruebadeingreso.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [User::class], version = 3)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao


    companion object {
        private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase? {
            if (instance == null) {
                synchronized(UserDatabase::class) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            UserDatabase::class.java, "user_database"
                    )
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .allowMainThreadQueries()
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

    class PopulateDbAsyncTask(db: UserDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val userDao = db?.userDao()

        override fun doInBackground(vararg p0: Unit?) {
            userDao?.insert(User("David", "DavidLab44","david@gmail.com", "3016493756"))
            userDao?.insert(User("Camilo", "Milo","camilo@gmail.com", "3106472883"))
            userDao?.insert(User("Jaiber", "JJYepes","juanjaiber@gmail.com", "31066437857"))
        }
    }

}