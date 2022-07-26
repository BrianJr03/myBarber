package jr.brian.mybarber.model.data.request

import com.google.gson.annotations.SerializedName

data class SignInRequest(

    @SerializedName("mobileNo")
    val mobileNo: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("fcmToken")
    val fcmToken: String
)