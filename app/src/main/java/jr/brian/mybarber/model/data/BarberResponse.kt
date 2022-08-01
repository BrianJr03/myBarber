package jr.brian.mybarber.model.data

data class BarberResponse(
    val barbers: ArrayList<Barber>,
    val message: String,
    val status: Int
)