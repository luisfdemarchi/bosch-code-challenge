package de.bosch.appointment

data class AppointmentRequest (
    val doctorId: String,
    val patientId: String,
    val scheduledDateTime: String
)