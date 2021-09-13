package de.bosch.appointment.model

import java.time.LocalDateTime
import java.util.*

data class Appointment(
    val id: String = UUID.randomUUID().toString(),
    val doctor: Doctor,
    val patient: Patient,
    val scheduledTime: LocalDateTime
)