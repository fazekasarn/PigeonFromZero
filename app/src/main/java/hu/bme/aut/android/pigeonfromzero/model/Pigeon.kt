package hu.bme.aut.android.pigeonfromzero.model

import java.io.Serializable

data class Pigeon (
    val pigeonId: Int,
    val number :String,
    val name :String,
    val birth :Int,
    val sex :Sex
) : Serializable {
    enum class Sex {
        MALE, FEMALE, UNKNOWN
    }
}