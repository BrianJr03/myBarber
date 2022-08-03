package jr.brian.mybarber.model.data.services

data class BarberService(
    val cost: Double,
    val duration: Double,
    val serviceId: Int,
    val serviceName: String,
    val servicePic: String,
    var isSelected: Boolean
)