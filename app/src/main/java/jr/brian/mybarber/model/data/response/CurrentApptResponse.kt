package jr.brian.mybarber.model.data.response

import jr.brian.mybarber.model.data.appointment.Slot

data class CurrentApptResponse(
    val message: String,
    val slots: ArrayList<Slot>,
    val status: Int
)