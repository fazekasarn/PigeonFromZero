package hu.bme.aut.android.pigeonfromzero.model

import java.io.Serializable

data class Pigeon (
    val pigeonId :String,
    val name :String,
    val birth :Int,
    val sex :Sex,
    val scores :String,
    val dadId :String?,
    val momId :String?
) : Serializable {
    enum class Sex {
        MALE, FEMALE, UNKNOWN
    }
}