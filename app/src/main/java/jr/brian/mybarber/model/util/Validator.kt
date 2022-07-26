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
    if (!validateLength(et)) {
        return false
    }
    val pattern = Regex("^[a-zA-Z\\s]+\$")
    return pattern.containsMatchIn(et.text)
}

fun validatePhone(et: EditText): Boolean {
    if (!validateLength(et)) {
        return false
    }
    val pattern =
        Regex("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}\$")
    return pattern.containsMatchIn(et.text)
}

fun validatePassword(et: EditText): Boolean {
    return validateLength(et)
}