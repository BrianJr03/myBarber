package jr.brian.mybarber.model.data

data class Slot(
    val date: String,
    val day: String,
    val slots: Map<String, Boolean>
)