package jr.brian.mybarber.model.data

data class CurrentApptResponse(
    val message: String,
    val slots: ArrayList<Slot>,
    val status: Int
)