package jr.brian.mybarber.model.data.response

import com.google.gson.annotations.SerializedName
import jr.brian.mybarber.model.data.User

data class SignUpResponse(
    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("otp")
    val otp: String,

    @SerializedName("userId")
    val userId: String
)