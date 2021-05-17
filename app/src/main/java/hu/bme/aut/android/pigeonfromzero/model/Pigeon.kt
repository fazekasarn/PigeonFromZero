package hu.bme.aut.android.pigeonfromzero.model

import java.io.Serializable
import java.text.DecimalFormat
import java.text.NumberFormat

data class Pigeon (
    val pigeonId :String,
    val country :String,
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

    override fun toString() :String {
        val form: NumberFormat = DecimalFormat("00")
        val remainer = form.format(birth%100)
        val short = when (sex) {
            Sex.MALE -> "H"
            Sex.FEMALE -> "T"
            Sex.UNKNOWN -> "?"
        }
        var alias = ""
        if (name!=""){
            alias = name+"\n"
        }
        return "$country $remainer-$pigeonId $short\n$alias$scores"
    }

    fun toIdentifier() :String {
        val form: NumberFormat = DecimalFormat("00")
        val remainder = form.format(birth%100)
        val short = when(sex){
            Sex.MALE -> "H"
            Sex.FEMALE -> "T"
            Sex.UNKNOWN -> "?"
        }
        return "$country-$remainder-$pigeonId-$short"
    }
}