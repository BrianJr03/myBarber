package jr.brian.mybarber.model.data.request

data class SignUpRequest(
    val fcmToken: String,
    val mobileNo: String,
    val password: String
)