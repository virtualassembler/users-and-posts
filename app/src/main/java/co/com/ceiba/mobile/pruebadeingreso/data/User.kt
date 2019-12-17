package co.com.ceiba.mobile.pruebadeingreso.data

import androidx.room.Entity


@Entity(tableName = "post_table")
data class User(

        var name: String,

        var username: String,

        var email: String,

        var priority: Int
) {

    @androidx.room.PrimaryKey(autoGenerate = true)
    var id: Int = 0

}