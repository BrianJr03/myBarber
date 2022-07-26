package jr.brian.mybarber.model.util

import android.widget.EditText

private fun validateLength(et: EditText): Boolean {
    if (et.length() == 0) {
        et.error = "This field is required"
        return false
    }
    return true
}

fun validateName(et: EditText): Boolean {
    val pattern = Regex("^[a-zA-Z\\s]+\$")
    if(!pattern.containsMatchIn(et.text)) {
        et.error = "Please enter a valid name"
        return false
    }
    return validateLength(et)
}

fun validatePhone(et: EditText): Boolean {
    val pattern = Regex("^[0-9]{10}\$")
    if(!pattern.containsMatchIn(et.text)) {
        et.error = "Please enter a 10 digit phone number"
        return false
    }
    return validateLength(et)
}

fun validateCountryCode(et: EditText): Boolean {
    val pattern = Regex("^(\\+?\\d{1,3}|\\d{1,4})\$")
    if(!pattern.containsMatchIn(et.text)) {
        et.error = "Please enter a valid country code"
        return false
    }
    return validateLength(et)
}

fun validatePassword(et: EditText): Boolean {
    if (et.text.length < 8) {
        et.error = "Minimum 8 characters"
        return false
    }
    return validateLength(et)
}