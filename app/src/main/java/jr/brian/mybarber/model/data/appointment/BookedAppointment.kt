package jr.brian.mybarber.model.data.appointment

data class BookedAppointment(
    val aptDate: String,
    val aptNo: Int,
    val aptStatus: String,
    val timeFrom: String,
    val timeTo: String,
    val totalDuration: Double
)