package co.com.udc.mobile.test.data

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table")
data class User(
        @SerializedName("name")
        var name: String,
        @SerializedName("username")
        var username: String,
        @SerializedName("email")
        var email: String,
        @SerializedName("phone")
        var phone: String
) {
    @androidx.room.PrimaryKey(autoGenerate = true)
    var id: Int = 0
}