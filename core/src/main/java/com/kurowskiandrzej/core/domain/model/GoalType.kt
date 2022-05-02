package com.kurowskiandrzej.core.domain.model

import java.security.InvalidKeyException

sealed class GoalType(val name: String) {
    object LoseWeight : GoalType("lose_weight")
    object MaintainWeight : GoalType("maintain_weight")
    object GainWeight : GoalType("gain_weight")

    companion object {
        fun fromString(name: String): GoalType {
            return when (name) {
                "lose_weight" -> LoseWeight
                "maintain_weight" -> MaintainWeight
                "gain_weight" -> GainWeight
                else -> throw InvalidKeyException("GoalType: $name is not implemented")
            }
        }
    }
}
