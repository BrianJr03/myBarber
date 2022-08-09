package jr.brian.mybarber.model.data

data class BookApptResponse(
    val appointment: Appointment,
    val message: String,
    val status: Int
)