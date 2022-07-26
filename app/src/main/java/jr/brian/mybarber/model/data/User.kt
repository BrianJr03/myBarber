package jr.brian.mybarber.model.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_id")
    val user_id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("mobile_no")
    val mobile_no: String
)