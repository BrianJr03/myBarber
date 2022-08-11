package jr.brian.mybarber.model.data.barber

data class BarberResponse(
    val barbers: ArrayList<Barber>,
    val message: String,
    val status: Int
)