package jr.brian.mybarber.model.data.appointment

data class AppointmentResponse(
    val appointments: ArrayList<BookedAppointment>,
    val message: String,
    val status: Int
)