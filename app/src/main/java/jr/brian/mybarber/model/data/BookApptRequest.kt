package jr.brian.mybarber.model.data

data class BookApptRequest(
    val userId: String,
    val barberId: String,
    val services: String,
    val aptDate: String,
    val timeFrom: String,
    val timeTo: String,
    val totalDuration: String,
    val totalCost: String,
    val couponCode: String,
    val sendSms: String
)