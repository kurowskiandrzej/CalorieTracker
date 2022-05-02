package com.kurowskiandrzej.core.domain.model

import java.security.InvalidKeyException

sealed class Gender(val name: String) {
    object Male : Gender("male")
    object Female : Gender("female")

    companion object {
        fun fromString(name: String): Gender {
            return when (name) {
                "male" -> Male
                "female" -> Female
                else -> throw InvalidKeyException("Gender: $name is not implemented")
            }
        }
    }
}
