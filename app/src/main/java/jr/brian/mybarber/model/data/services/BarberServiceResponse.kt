package jr.brian.mybarber.model.data.services

data class BarberServiceResponse(
    val message: String,
    val services: ArrayList<BarberService>,
    val status: Int
)