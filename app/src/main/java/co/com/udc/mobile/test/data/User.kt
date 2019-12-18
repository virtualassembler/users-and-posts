package co.com.udc.mobile.test.data

import androidx.room.Entity


@Entity(tableName = "user_table")
data class User(

        var name: String,

        var username: String,

        var email: String,

        var priority: Int
) {

    @androidx.room.PrimaryKey(autoGenerate = true)
    var id: Int = 0

}