package co.com.ceiba.mobile.pruebadeingreso.data

import androidx.room.Entity


@Entity(tableName = "post_table")
data class Post(

        var title: String,

        var description: String,

        var priority: Int
) {

    //does it matter if these are private or not?
    @androidx.room.PrimaryKey(autoGenerate = true)
    var id: Int = 0

}