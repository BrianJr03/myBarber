package jr.brian.mybarber.model.data.response

import jr.brian.mybarber.model.data.appointment.Appointment

data class BookApptResponse(
    val appointment: Appointment,
    val message: String,
    val status: Int
)